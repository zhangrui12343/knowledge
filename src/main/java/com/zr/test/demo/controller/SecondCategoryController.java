package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiCategory;
import com.zr.test.demo.config.swagger.annotation.ApiTag;
import com.zr.test.demo.model.entity.SecondCategory;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.service.ISecondCategoryService;
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
 * @since 2022-05-15
 */
@RestController
@RequestMapping("/second-category")
@ApiCategory
@Api(tags = "14-分类管理")
public class SecondCategoryController {
    @Autowired
    private ISecondCategoryService service;

    @PostMapping("/add}")
    @ApiOperation("14.0.1 新增分类")
    public Result<Object> add(@RequestBody SecondCategory dto) {
        return service.add(dto);
    }

    @PostMapping("/query")
    @ApiOperation("14.0.2 查询分类")
    public Result<List<SecondCategory>> query(@RequestBody SecondCategory dto) {
        return service.queryByDto(dto);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("14.0.3 删除分类")
    public Result<Object> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    @ApiOperation("14.0.4 修改分类")
    public Result<Object> update(@RequestBody SecondCategory dto) {
        return service.update(dto);
    }
}

