package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.entity.SecondCategory;
import com.zr.test.demo.dao.SecondCategoryMapper;
import com.zr.test.demo.service.ISecondCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(StringUtil.isEmpty(dto.getName())){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR,"名字不能为空");
        }
        return Result.success(this.getBaseMapper().insert(dto));
    }

    @Override
    public Result<Object> update(SecondCategory dto) {
        if(dto.getId()!=null){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR,"id不能为空");
        }
        return Result.success(this.getBaseMapper().updateById(dto));
    }

    @Override
    public Result<List<SecondCategory>> queryByDto(SecondCategory dto) {
        return Result.success(this.getBaseMapper().selectList(new QueryWrapper<>(dto)));
    }

    @Override
    public Result<Object> delete(Long id) {
        return Result.success(this.baseMapper.deleteById(id));
    }
}
