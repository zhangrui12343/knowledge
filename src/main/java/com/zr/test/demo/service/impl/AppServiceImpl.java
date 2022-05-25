package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.AppDTO;
import com.zr.test.demo.model.dto.AppQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.entity.AfterCourse;
import com.zr.test.demo.model.entity.App;
import com.zr.test.demo.dao.AppMapper;
import com.zr.test.demo.model.entity.AppCategory;
import com.zr.test.demo.model.entity.FileRouter;
import com.zr.test.demo.model.vo.AppOneVO;
import com.zr.test.demo.model.vo.AppVO;
import com.zr.test.demo.service.IAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import java.util.stream.Collector;
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
    private final FileRouterServiceImpl fileRouterService;
    private final AppCategoryServiceImpl appCategoryService;

    @Autowired
    public AppServiceImpl(FileRouterServiceImpl fileRouterService, AppCategoryServiceImpl appCategoryService) {
        this.fileRouterService = fileRouterService;
        this.appCategoryService = appCategoryService;
    }

    @Override
    public Result<Object> add(AppDTO dto, HttpServletRequest request) {
        App app = new App();
        BeanUtils.copyProperties(dto, app);
        app.setTime(new Date());
        app.setTags(ListUtil.listToString(dto.getTags()));
        return Result.success(this.getBaseMapper().insert(app));
    }

    @Override
    public Result<PageInfo<AppVO>> queryByDto(AppQueryDTO dto, HttpServletRequest request) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        if (dto.getType() != null) {
            queryWrapper.eq("type", dto.getType());
        }
        if (dto.getSubject() != null) {
            queryWrapper.eq("subject", dto.getSubject());
        }
        if (dto.getPlatform() != null) {
            queryWrapper.eq("platform", dto.getPlatform());
        }
        if (!StringUtil.isEmpty(dto.getStartTime()) && !StringUtil.isEmpty(dto.getEndTime())) {
            queryWrapper.between("time", TimeUtil.getDate(dto.getStartTime()), TimeUtil.getDate(dto.getEndTime()));
        }
        if (!StringUtil.isEmpty(dto.getName())) {
            queryWrapper.like("name", dto.getName());
        }
        queryWrapper.orderByDesc("time");
        IPage<App> page = this.getBaseMapper().selectPage(new Page<>(dto.getPage(), dto.getPageSize()), queryWrapper);
        if (page.getTotal() == 0) {
            PageInfo<AppVO> pageInfo = new PageInfo<>();
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setTotal(0);
            return Result.success(pageInfo);
        }
        List<AppVO> res = new ArrayList<>();
        List<AppCategory> categories = appCategoryService.getBaseMapper().selectList(null);
        Map<String, String> map = categories.stream().collect(Collectors.toMap(e -> e.getId().toString(), AppCategory::getName));
        page.getRecords().forEach(app -> {
            AppVO vo = new AppVO();
            BeanUtils.copyProperties(app, vo);
            FileRouter file = fileRouterService.getBaseMapper().selectById(app.getLogo());
            if (file != null) {
                vo.setLogo(Optional.ofNullable(file.getFilePath()).orElse(""));
            }
            vo.setType(Optional.ofNullable(map.get(app.getType().toString())).orElse(""));
            vo.setSubject(Optional.ofNullable(map.get(app.getSubject().toString())).orElse(""));
            vo.setPlatform(Optional.ofNullable(map.get(app.getPlatform().toString())).orElse(""));
            String[] arr = app.getTags().split(",");
            StringBuilder sb = new StringBuilder();
            for (String idStr : arr) {
                String temp = map.get(idStr);
                if (StringUtil.isEmpty(temp)) {
                    continue;
                }
                sb.append(temp).append(",");
            }
            vo.setTags(sb.substring(0, sb.length() - 1));
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

    @Override
    public Result<AppOneVO> queryOne(Long id, HttpServletRequest request) {
        App app = this.getBaseMapper().selectById(id);
        AppOneVO vo = new AppOneVO();
        BeanUtils.copyProperties(app, vo);
        vo.setImg(FileUtil.getBase64FilePath(fileRouterService.selectPath(app.getImg())));
        vo.setImgId(app.getImg());
        vo.setLogo(FileUtil.getBase64FilePath(fileRouterService.selectPath(app.getLogo())));
        vo.setLogoId(app.getLogo());
        vo.setTags(ListUtil.stringToList(app.getTags()));
        return Result.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateByDto(AppDTO dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR);
        }
        App oldApp = this.getBaseMapper().selectById(dto.getId() );
        if(!oldApp.getLogo().equals(dto.getId())){
            fileRouterService.deleteOldFile(oldApp.getLogo());
        }
        if(!oldApp.getImg().equals(dto.getImg())){
            fileRouterService.deleteOldFile(oldApp.getImg());
        }
        App app = new App();
        BeanUtils.copyProperties(dto, app);
        app.setTags(ListUtil.listToString(dto.getTags()));
        return Result.success(this.getBaseMapper().updateById(app));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        App oldApp = this.getBaseMapper().selectById(id );
        if (oldApp == null) {
            return Result.success(0);
        }
        fileRouterService.deleteOldFile(oldApp.getLogo());
        fileRouterService.deleteOldFile(oldApp.getImg());
        return Result.success(this.getBaseMapper().deleteById(id));
    }

    @Override
    public Result<Object> updateStatus(StatusDTO dto, HttpServletRequest request) {
        App course = new App();
        course.setId(dto.getId());
        course.setStatus(dto.getStatus());
        return Result.success(this.getBaseMapper().updateById(course));
    }

    @Override
    public Result<List<AppOneVO>> queryAppName(String name, HttpServletRequest request) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<App> list=this.getBaseMapper().selectList(queryWrapper);
        if(ListUtil.isEmpty(list)){
            return Result.success(new ArrayList<>());
        }
        List<AppOneVO> voList=new ArrayList<>();
        list.forEach(l->{
            AppOneVO vo=new AppOneVO();
            vo.setId(l.getId());
            vo.setName(l.getName());
            vo.setLogo(FileUtil.getBase64FilePath(fileRouterService.selectPath(l.getLogo())));
            voList.add(vo);
        });
        return Result.success(voList);
    }


}
