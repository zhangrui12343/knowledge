package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.dao.TagMapper;
import com.zr.test.demo.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Override
    public Result<Object> add(Tag dto) {
        return Result.success(this.baseMapper.insert(dto));
    }
    @Override
    public Result<List<Tag>> queryByDto(Tag dto) {
        return Result.success(this.getBaseMapper().selectList(null));
    }

    @Override
    public Result<Object> delete(Long id) {
        return Result.success(this.baseMapper.deleteById(id));
    }

    @Override
    public Result<Object> update(Tag dto) {
        return Result.success(this.baseMapper.updateById(dto));
    }
}
