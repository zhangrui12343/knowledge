package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.AppCaseDTO;
import com.zr.test.demo.model.dto.AppQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.entity.App;
import com.zr.test.demo.model.entity.AppCase;
import com.zr.test.demo.dao.AppCaseMapper;
import com.zr.test.demo.model.entity.AppCategory;
import com.zr.test.demo.model.vo.AppCaseOneVO;
import com.zr.test.demo.model.vo.AppCaseVO;
import com.zr.test.demo.service.IAppCaseService;
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
public class AppCaseServiceImpl extends ServiceImpl<AppCaseMapper, AppCase> implements IAppCaseService {
    private final AppServiceImpl appService;
    private final FileRouterServiceImpl fileRouterService;
    private final AppCategoryServiceImpl appCategoryService;

    @Autowired
    public AppCaseServiceImpl(AppServiceImpl appService, FileRouterServiceImpl fileRouterService, AppCategoryServiceImpl appCategoryService) {
        this.appService = appService;
        this.fileRouterService = fileRouterService;
        this.appCategoryService = appCategoryService;
    }

    @Override
    public Result<Object> add(AppCaseDTO dto, HttpServletRequest request) {
        AppCase appCase = new AppCase();
        BeanUtils.copyProperties(dto, appCase);
        return Result.success(this.getBaseMapper().insert(appCase));
    }

    @Override
    public Result<PageInfo<AppCaseVO>> queryByDto(AppQueryDTO dto, HttpServletRequest request) {
        QueryWrapper<AppCase> queryWrapper1 = new QueryWrapper<>();
        if (!StringUtil.isEmpty(dto.getStartTime()) && !StringUtil.isEmpty(dto.getEndTime())) {
            queryWrapper1.between("time", TimeUtil.getDate(dto.getStartTime()), TimeUtil.getDate(dto.getEndTime()));
        }
        queryWrapper1.orderByDesc("time");
        List<AppCase> appCases = this.getBaseMapper().selectList(queryWrapper1);
        if(ListUtil.isEmpty(appCases)){
            PageInfo<AppCaseVO> pageInfo = new PageInfo<>();
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setTotal(0);
            return Result.success(pageInfo);
        }
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

        if (!StringUtil.isEmpty(dto.getName())) {
            queryWrapper.like("name", dto.getName());
        }

        List<App> apps = appService.getBaseMapper().selectList(queryWrapper);
        Map<String,App> map=apps.stream().collect(Collectors.toMap(app->app.getId().toString(), e->e));
        List<AppCategory> categories = appCategoryService.getBaseMapper().selectList(null);
        Map<String, String> map2 = categories.stream().collect(Collectors.toMap(e ->  e.getId().toString(), AppCategory::getName));
        List<AppCaseVO> res=new ArrayList<>();
        appCases.forEach(appCase -> {
            if(!map.containsKey(appCase.getAppId().toString())){
                return;
            }
            App app=map.get(appCase.getAppId().toString());
            AppCaseVO vo=new AppCaseVO();
            vo.setId(appCase.getId());
            vo.setAppName(app.getName());
            vo.setCaseName(appCase.getCaseName());
            vo.setLogo(FileUtil.getBase64FilePath(fileRouterService.selectPath(app.getLogo())));
            vo.setType(Optional.ofNullable(map2.get(app.getType().toString())).orElse(""));
            vo.setSubject(Optional.ofNullable(map2.get(app.getSubject().toString())).orElse(""));
            vo.setPlatform(Optional.ofNullable(map2.get(app.getPlatform().toString())).orElse(""));
            String[] arr = app.getTags().split(",");
            StringBuilder sb = new StringBuilder();
            for (String idStr : arr) {
                String temp = map2.get(idStr);
                if (StringUtil.isEmpty(temp)) {
                    continue;
                }
                sb.append(temp).append(",");
            }
            vo.setTags(sb.substring(0, sb.length() - 1));
            vo.setTime(TimeUtil.getTime(appCase.getTime()));
            vo.setStatus(appCase.getStatus());
            vo.setOrder(appCase.getOrder());
            res.add(vo);
        });
        int total=res.size();
        if(total==0){
            PageInfo<AppCaseVO> pageInfo = new PageInfo<>();
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setTotal(0);
            return Result.success(pageInfo);
        }
        if(!StringUtil.isEmpty(dto.getName())){
            res.sort(Comparator.comparing(AppCaseVO::getOrder).reversed());
        }
        List<AppCaseVO> list= ListUtil.page(res,dto.getPage(),dto.getPageSize());
        PageInfo<AppCaseVO> pageInfo = new PageInfo<>();
        pageInfo.setPage(dto.getPage());
        pageInfo.setPageSize(dto.getPageSize());
        pageInfo.setTotal(total);
        pageInfo.setList(list);
        return Result.success(pageInfo);
    }

    @Override
    public Result<AppCaseOneVO> queryOne(Long id, HttpServletRequest request) {
       AppCase appCase= this.baseMapper.selectById(id);
        AppCaseOneVO vo=new AppCaseOneVO();
        BeanUtils.copyProperties(appCase,vo);
        vo.setVideoId(appCase.getId());
        vo.setVideo(FileUtil.getBase64FilePath(fileRouterService.selectPath(appCase.getVideo())));
        return Result.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateByDto(AppCaseDTO dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        AppCase appCase= this.baseMapper.selectById(dto.getId());
        if(!appCase.getVideo().equals(dto.getVideo())){
            fileRouterService.deleteOldFile(appCase.getVideo());
        }
        AppCase newApp = new AppCase();
        BeanUtils.copyProperties(dto,newApp);
        return Result.success(this.baseMapper.updateById(newApp));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        AppCase appCase= this.baseMapper.selectById(id);
        if (appCase == null) {
            return Result.success(0);
        }
        fileRouterService.deleteOldFile(appCase.getVideo());
        return Result.success(this.baseMapper.deleteById(id));
    }

    @Override
    public Result<Object> updateStatus(StatusDTO dto, HttpServletRequest request) {
        AppCase course = new AppCase();
        course.setId(dto.getId());
        course.setStatus(dto.getStatus());
        return Result.success(this.baseMapper.updateById(course));
    }
}
