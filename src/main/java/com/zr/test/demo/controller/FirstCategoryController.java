package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.FirstCategoryDTO;
import com.zr.test.demo.model.entity.FirstCategory;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.model.vo.FirstCategoryVO;
import com.zr.test.demo.service.IFirstCategoryService;
import com.zr.test.demo.service.ITagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-16
 */
@RestController
@RequestMapping("/first-category")
public class FirstCategoryController {
    @Autowired
    private IFirstCategoryService service;

    @PostMapping("/add")
    @ApiOperation("6.0.1 新增教育分类")
    public Result<Object> add(@RequestBody FirstCategoryDTO dto) {
        return service.add(dto);
    }

    @PostMapping("/query")
    @ApiOperation("6.0.2 查询教育分类")
    public Result<List<FirstCategoryVO>> query(@RequestParam(name = "type") @NotNull(message = "类型不能为空") Integer type) {
        return service.queryByType(type);
    }

    @PostMapping("/update")
    @ApiOperation("6.0.1 新增教育分类")
    public Result<Object> update(@RequestBody FirstCategoryDTO dto) {
        return service.update(dto);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("6.0.3 删除教育分类")
    public Result<Object> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}

