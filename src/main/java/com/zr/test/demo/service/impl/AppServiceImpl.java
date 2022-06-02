package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.dao.FileRouterMapper;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.entity.App;
import com.zr.test.demo.dao.AppMapper;
import com.zr.test.demo.model.entity.AppCase;
import com.zr.test.demo.model.entity.AppCategory;
import com.zr.test.demo.model.entity.FileRouter;
import com.zr.test.demo.model.vo.CaseAppVO;
import com.zr.test.demo.model.vo.AppAndCaseVO;
import com.zr.test.demo.model.vo.AppOneVO;
import com.zr.test.demo.model.vo.AppVO;
import com.zr.test.demo.service.IAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.support.FileRouterBiz;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import com.zr.test.demo.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {
    private final FileRouterBiz fileRouterService;
    private final FileRouterMapper fileRouter;


    @Autowired
    public AppServiceImpl(FileRouterBiz fileRouterService, FileRouterMapper fileRouter) {
        this.fileRouterService = fileRouterService;
        this.fileRouter = fileRouter;


    }

    @Override
    public Result<Object> add(AppDTO dto, HttpServletRequest request) {
        App app = new App();
        BeanUtils.copyProperties(dto, app);
        app.setTime(new Date());
        app.setSubject(ListUtil.listToString(dto.getSubject()));
        app.setPlatform(ListUtil.listToString(dto.getPlatform()));
        app.setTags(ListUtil.listToString(dto.getTags()));
        return Result.success(this.getBaseMapper().insert(app));
    }

    @Override
    public Result<PageInfo<AppVO>> queryByDto(AppQueryDTO dto, HttpServletRequest request) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
//        if (dto.getType() != null) {
//            queryWrapper.eq("type", dto.getType());
//        }
//        if (dto.getSubject() != null) {
//            queryWrapper.eq("subject", dto.getSubject());
//        }
//        if (dto.getPlatform() != null) {
//            queryWrapper.eq("platform", dto.getPlatform());
//        }
        if (!StringUtil.isEmpty(dto.getStartTime()) && !StringUtil.isEmpty(dto.getEndTime())) {
            queryWrapper.between("time", TimeUtil.getDate(dto.getStartTime()), TimeUtil.getDate(dto.getEndTime()));
        }
        if (!StringUtil.isEmpty(dto.getName())) {
            queryWrapper.like("name", dto.getName());
        }
        queryWrapper.orderByDesc("time");

        Page<App> page = PageHelper.startPage(dto.getPage(), dto.getPageSize())
                .doSelectPage(() -> this.getBaseMapper().selectList(queryWrapper));
        if (page.getTotal() == 0) {
            PageInfo<AppVO> pageInfo = new PageInfo<>();
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setTotal(0);
            return Result.success(pageInfo);
        }
        List<AppVO> res = new ArrayList<>();
        List<AppCategory> categories = this.baseMapper.selectAppCategory();
        Map<String, String> map = categories.stream().collect(Collectors.toMap(e -> e.getId().toString(), AppCategory::getName));
        page.getResult().forEach(app -> {
            AppVO vo = new AppVO();
            BeanUtils.copyProperties(app, vo);
            FileRouter file = fileRouter.selectById(app.getLogo());
            if (file != null) {
                vo.setLogo(FileUtil.getBase64FilePath(file.getFilePath()));
                vo.setLogoId(file.getId());
            }
            vo.setType(Optional.ofNullable(map.get(app.getType().toString())).orElse(""));

            vo.setSubject(getString(app.getSubject(), map));
            vo.setPlatform(getString(app.getPlatform(), map));
            vo.setTags(getString(app.getTags(), map));
            vo.setTime(TimeUtil.getTime(app.getTime()));
            res.add(vo);
        });
        PageInfo<AppVO> pageInfo = new PageInfo<>();
        pageInfo.setPage(dto.getPage());
        pageInfo.setPageSize(dto.getPageSize());
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(res);
        return Result.success(pageInfo);
    }

    private String getString(String tags, Map<String, String> map) {
        String[] arr = tags.split(",");
        StringBuilder sb = new StringBuilder();
        for (String idStr : arr) {
            String temp = map.get(idStr);
            if (StringUtil.isEmpty(temp)) {
                continue;
            }
            sb.append(temp).append(",");
        }
        if(sb.length()==0){
            return sb.toString();
        }
        return sb.substring(0, sb.length() - 1);
    }

    @Override
    public Result<AppOneVO> queryOne(Long id, HttpServletRequest request) {
        App app = this.getBaseMapper().selectById(id);
        if (app == null) {
            return Result.success(null);
        }
        AppOneVO vo = new AppOneVO();
        BeanUtils.copyProperties(app, vo);
        vo.setImg(FileUtil.getBase64FilePath(fileRouterService.selectPath(app.getImg())));
        vo.setImgId(app.getImg());
        vo.setLogo(FileUtil.getBase64FilePath(fileRouterService.selectPath(app.getLogo())));
        vo.setLogoId(app.getLogo());
        vo.setSubject(ListUtil.stringToList(app.getSubject()));
        vo.setPlatform(ListUtil.stringToList(app.getPlatform()));
        vo.setTags(ListUtil.stringToList(app.getTags()));
        return Result.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateByDto(AppDTO dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR);
        }
        App oldApp = this.getBaseMapper().selectById(dto.getId());
        if (!oldApp.getLogo().equals(dto.getId())) {
            fileRouterService.deleteOldFile(oldApp.getLogo());
        }
        if (!oldApp.getImg().equals(dto.getImg())) {
            fileRouterService.deleteOldFile(oldApp.getImg());
        }
        App app = new App();
        BeanUtils.copyProperties(dto, app);
        app.setSubject(ListUtil.listToString(dto.getSubject()));
        app.setPlatform(ListUtil.listToString(dto.getPlatform()));
        app.setTags(ListUtil.listToString(dto.getTags()));
        app.setTime(new Date());
        return Result.success(this.getBaseMapper().updateById(app));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        App oldApp = this.getBaseMapper().selectById(id);
        if (oldApp == null) {
            return Result.success(0);
        }
        fileRouterService.deleteOldFile(oldApp.getLogo());
        fileRouterService.deleteOldFile(oldApp.getImg());
        //删除 关联的案例
        List<Long> fileIds = this.getBaseMapper().selectAppCaseVideoByAppId(oldApp.getId());
        fileRouterService.delete(fileIds);
        this.getBaseMapper().deleteAppCaseByAppId(oldApp.getId());
        //删除 关联的矩阵的关联关系
        this.getBaseMapper().deleteToolRelationByAppId(oldApp.getId());
        return Result.success(this.getBaseMapper().deleteById(id));
    }

    @Override
    public Result<Object> updateStatus(AppStatusDTO dto) {
        if (dto.getUniversal() == null && dto.getStatus() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        App course = new App();
        course.setId(dto.getId());
        if (dto.getStatus() != null) {
            course.setStatus(dto.getStatus());
        }
        if (dto.getUniversal() != null) {
            course.setUniversal(dto.getUniversal());
        }
        return Result.success(this.getBaseMapper().updateById(course));
    }

    @Override
    public Result<List<AppOneVO>> queryAppName(String name, HttpServletRequest request) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<App> list = this.getBaseMapper().selectList(queryWrapper);
        if (ListUtil.isEmpty(list)) {
            return Result.success(new ArrayList<>());
        }
        List<AppOneVO> voList = new ArrayList<>();
        list.forEach(l -> {
            AppOneVO vo = new AppOneVO();
            vo.setId(l.getId());
            vo.setName(l.getName());
            vo.setLogo(FileUtil.getBase64FilePath(fileRouterService.selectPath(l.getLogo())));
            voList.add(vo);
        });
        return Result.success(voList);
    }

    private boolean exist(Long l, String str) {
        String[] arr = str.split(",");
        for (String s : arr) {
            if (s.equals(l.toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Result<PageInfo<AppAndCaseVO>> listByDto(AppQueryListDTO dto) {
        List<AppAndCaseVO> caseApps = this.getBaseMapper().selectByType(dto.getType());
        if(dto.getSubject()!=null){
            caseApps=caseApps.stream().filter(e->
                    exist(dto.getSubject(),e.getSubject().toString())).collect(Collectors.toList());
        }
        if(dto.getPlatform()!=null){
            caseApps=caseApps.stream().filter(e->
                    exist(dto.getPlatform(),e.getPlatform().toString())).collect(Collectors.toList());
        }
        if (ListUtil.isEmpty(caseApps)) {
            PageInfo<AppAndCaseVO> pageInfo = new PageInfo<>();
            pageInfo.setTotal(0);
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            return Result.success(pageInfo);
        }
        LinkedHashMap<String, AppAndCaseVO> map = new LinkedHashMap<>();
        caseApps.forEach(caseApp -> {
            if (caseApp.getTags() != null) {
                caseApp.setTags(ListUtil.stringToList((String) caseApp.getTags()));
            }
            if (caseApp.getSubject() != null) {
                caseApp.setSubject(ListUtil.stringToList((String) caseApp.getSubject()));
            }
            if (caseApp.getPlatform() != null) {
                caseApp.setPlatform(ListUtil.stringToList((String) caseApp.getPlatform()));
            }
            String key = caseApp.getId().toString();
            if (map.containsKey(key)) {
                if (map.get(key).getCaseorder() < caseApp.getCaseorder()) {
                    map.put(key, caseApp);
                }
            } else {
                map.put(key, caseApp);
            }
        });
        List<AppAndCaseVO> list = new ArrayList<>(map.values());
        int total = list.size();
        List<AppAndCaseVO> res = ListUtil.page(list, dto.getPage(), dto.getPageSize());
        PageInfo<AppAndCaseVO> pageInfo = new PageInfo<>();
        pageInfo.setTotal(total);
        pageInfo.setPage(dto.getPage());
        pageInfo.setPageSize(dto.getPageSize());
        pageInfo.setList(res);
        return Result.success(pageInfo);

    }

    @Override
    public Result<List<App>> listU() {
        App app = new App();
        app.setUniversal(1);
        app.setStatus(1);
        return Result.success(this.getBaseMapper().selectList(new QueryWrapper<>(app)));
    }


}
