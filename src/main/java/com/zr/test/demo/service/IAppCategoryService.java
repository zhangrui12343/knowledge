package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.entity.AppCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
public interface IAppCategoryService extends IService<AppCategory> {

    Result<Object> add(AppCategory dto);

    Result<List<AppCategory>> queryByDto(AppCategory dto);

    Result<Object> delete(Long id);

    Result<Object> updateByDto(AppCategory dto);

    Result<List<AppCategory>> queryAll();
}
