package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.entity.App;
import com.zr.test.demo.model.entity.AppCategory;
import com.zr.test.demo.dao.AppCategoryMapper;
import com.zr.test.demo.service.IAppCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
@Service
public class AppCategoryServiceImpl extends ServiceImpl<AppCategoryMapper, AppCategory> implements IAppCategoryService {
    private final AppServiceImpl appService;

    public AppCategoryServiceImpl(AppServiceImpl appService) {
        this.appService = appService;
    }

    @Override
    public Result<Object> add(AppCategory dto) {
        if (StringUtil.isEmpty(dto.getName())) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR, "名字不能为空");
        }
        return Result.success(this.baseMapper.insert(dto));
    }

    @Override
    public Result<List<AppCategory>> queryByDto(AppCategory dto) {
        if (dto.getType() == -1) {
            return Result.success(this.baseMapper.selectList(null));

        }
        return Result.success(this.baseMapper.selectList(new QueryWrapper<>(dto)));
    }

    @Override
    public Result<Object> delete(Long id) {
        AppCategory old = this.baseMapper.selectById(id);
        if(old==null){
            return Result.success(0);
        }
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        if (old.getType() == 0) {
            queryWrapper.lambda().eq(App::getType, id);
            int count = appService.getBaseMapper().selectCount(queryWrapper);
            if (count > 0) {
                return Result.fail(ErrorCode.SYS_DATABASE_OPT_ERROR, "该类型存在有关联的软件，请检查");
            }
        }

        List<App> all = appService.getBaseMapper().selectList(null);
        for (App a : all) {
            if (old.getType() == 1) {
                if (!updateAppType(old.getId(), a.getSubject(), a,1)) {
                    return Result.fail(ErrorCode.SYS_DATABASE_OPT_ERROR, "该科目存在有关联的软件，请检查");
                }
            } else if (old.getType() == 2) {
                if (!updateAppType(old.getId(), a.getPlatform(), a,2)) {
                    return Result.fail(ErrorCode.SYS_DATABASE_OPT_ERROR, "该平台存在有关联的软件，请检查");
                }
            }else if(old.getType() == 3) {
                if (!updateAppType(old.getId(), a.getTags(), a,3)) {
                    return Result.fail(ErrorCode.SYS_DATABASE_OPT_ERROR, "该标签存在有关联的软件，请检查");
                }
            }
        }
        return Result.success(this.baseMapper.deleteById(id));
    }

    private boolean updateAppType(Long id, String subject, App a,int type) {
        String[] arr = subject.split(",");
        int idx = check(arr, id);
        if (idx > -1) {
            if (arr.length == 1) {
                return false;
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < arr.length; i++) {
                    if (i != idx) {
                        sb.append(arr[i]).append(",");
                    }
                }
                if(sb.length()>0){
                    if(type==1){
                        a.setSubject(sb.substring(0, sb.length() - 1));
                    }else if(type==2){
                        a.setPlatform(sb.substring(0, sb.length() - 1));
                    }else if(type==3){
                        a.setTags(sb.substring(0, sb.length() - 1));
                    }
                    appService.getBaseMapper().updateById(a);
                }
            }
        }
        return true;
    }

    private int check(String[] arr, Long id) {
        for (int i = 0; i < arr.length; i++) {
            if (id.toString().equals(arr[i].trim())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Result<Object> updateByDto(AppCategory dto) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR, "id不能为空");
        }
        dto.setType(null);
        return Result.success(this.baseMapper.updateById(dto));
    }

    @Override
    public Result<List<AppCategory>> queryAll() {
        QueryWrapper<AppCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("type");
        return Result.success(this.baseMapper.selectList(queryWrapper));
    }
}
