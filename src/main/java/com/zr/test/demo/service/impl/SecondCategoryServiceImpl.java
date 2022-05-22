package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.cache.LocalUtil;
import com.zr.test.demo.model.entity.SecondCategory;
import com.zr.test.demo.dao.SecondCategoryMapper;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.service.ISecondCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-15
 */
@Service
public class SecondCategoryServiceImpl extends ServiceImpl<SecondCategoryMapper, SecondCategory> implements ISecondCategoryService {
    @Override
    public Result<Object> add(SecondCategory dto) {
        return Result.success(this.getBaseMapper().insert(dto));
    }

    @Override
    public Result<Object> update(SecondCategory dto) {
        return Result.success(this.getBaseMapper().updateById(dto));
    }

    @Override
    public Result<List<SecondCategory>> queryByDto(SecondCategory dto) {
        return Result.success(this.getBaseMapper().selectList(null));
    }

    @Override
    public Result<Object> delete(Long id) {
        return Result.success(this.baseMapper.deleteById(id));
    }
}
