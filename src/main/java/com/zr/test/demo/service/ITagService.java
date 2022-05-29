package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-15
 */
public interface ITagService extends IService<Tag> {

    Result<Object> add(Tag dto);

    Result<List<Tag>> queryByDto(Long id);

    Result<Object> delete(Long id);

    Result<Object> update(Tag dto);
}
