package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.dao.FirstTagMapper;
import com.zr.test.demo.model.entity.FirstTag;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.dao.TagMapper;
import com.zr.test.demo.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-15
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    private final FirstTagMapper firstTagMapper;

    public TagServiceImpl(FirstTagMapper firstTagMapper) {
        this.firstTagMapper = firstTagMapper;
    }

    @Override
    public Result<Object> add(Tag dto) {
        if (StringUtil.isEmpty(dto.getName())) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR, "名字不能为空");
        }
        return Result.success(this.baseMapper.insert(dto));
    }

    @Override
    public Result<List<Tag>> queryByDto(Long id) {
        if (id == -3) {
            return Result.success(this.baseMapper.selectList(null));
        }
        if (id < 1) {
            Tag tag=new Tag();
            tag.setType(Math.abs(id.intValue()));
            return Result.success(this.baseMapper.selectList(new QueryWrapper<>(tag)));
        }
        FirstTag firstTag = new FirstTag();
        firstTag.setFirstId(id);
        List<FirstTag> list = firstTagMapper.selectList(new QueryWrapper<>(firstTag));
        if (ListUtil.isEmpty(list)) {
            return Result.success(null);
        }
        return Result.success(this.baseMapper.selectBatchIds(list.stream().map(FirstTag::getTagId).collect(Collectors.toList())));
    }

    @Override
    public Result<Object> delete(Long id) {
        return Result.success(this.baseMapper.deleteById(id));
    }

    @Override
    public Result<Object> update(Tag dto) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR, "id不能为空");
        }
        return Result.success(this.baseMapper.updateById(dto));
    }
}
