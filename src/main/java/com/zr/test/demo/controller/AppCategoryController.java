package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiAppType;
import com.zr.test.demo.model.entity.AppCategory;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.service.IAppCategoryService;
import com.zr.test.demo.service.ITagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/app-category")
@Api(tags = "15-软件分类 api")
@ApiAppType
public class AppCategoryController {
    @Autowired
    private IAppCategoryService service;

    @PostMapping("/add")
    @ApiOperation("15.0.1 新增软件分类 type: 0:类型,1:科目,2:平台,3：标签")
    public Result<Object> add(@RequestBody AppCategory dto) {
        return service.add(dto);
    }

    @PostMapping("/query")
    @ApiOperation("15.0.2 查询软件分类 type:-1:全部 0:类型,1:科目,2:平台,3：标签")
    public Result<List<AppCategory>> query(@RequestBody AppCategory dto) {
        return service.queryByDto(dto);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("15.0.3 删除软件分类")
    public Result<Object> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    @ApiOperation("15.0.4 修改软件分类")
    public Result<Object> update(@RequestBody AppCategory dto) {
        return service.updateByDto(dto);
    }

    @PostMapping("/list")
    @ApiOperation("15.0.5 前端使用 查询软件分类 ")
    public Result<List<AppCategory>> list() {
        return service.queryAll();
    }

}

