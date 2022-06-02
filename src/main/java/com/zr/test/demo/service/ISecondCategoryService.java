package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.entity.SecondCategory;
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
public interface ISecondCategoryService extends IService<SecondCategory> {

    Result<Object> add(SecondCategory dto);
    Result<Object> update(SecondCategory dto);

    Result<List<SecondCategory>> queryByDto(Long id);

    Result<Object> delete(Long id);
}
