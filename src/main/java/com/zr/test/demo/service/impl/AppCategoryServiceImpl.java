package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.entity.AppCategory;
import com.zr.test.demo.dao.AppCategoryMapper;
import com.zr.test.demo.service.IAppCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
@Service
public class AppCategoryServiceImpl extends ServiceImpl<AppCategoryMapper, AppCategory> implements IAppCategoryService {

    @Override
    public Result<Object> add(AppCategory dto) {
        return Result.success(this.baseMapper.insert(dto));
    }

    @Override
    public Result<List<AppCategory>> queryByDto(AppCategory dto) {
        return Result.success(this.baseMapper.selectList(new QueryWrapper<>(dto)));
    }

    @Override
    public Result<Object> delete(Long id) {
        return Result.success(this.baseMapper.deleteById(id));
    }

    @Override
    public Result<Object> updateByDto(AppCategory dto) {
        return Result.success(this.baseMapper.updateById(dto));
    }
}
