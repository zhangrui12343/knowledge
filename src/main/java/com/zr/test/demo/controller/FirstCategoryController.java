package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiFirstCategory;
import com.zr.test.demo.model.dto.FirstCategoryDTO;
import com.zr.test.demo.model.entity.FirstCategory;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.model.vo.FirstCategoryListVO;
import com.zr.test.demo.model.vo.FirstCategoryOneVO;
import com.zr.test.demo.model.vo.FirstCategoryVO;
import com.zr.test.demo.service.IFirstCategoryService;
import com.zr.test.demo.service.ITagService;
import io.swagger.annotations.Api;
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
@ApiFirstCategory
@Api(tags = "12-教育类型管理")
public class FirstCategoryController {
    @Autowired
    private IFirstCategoryService service;

    @PostMapping("/add")
    @ApiOperation("12.0.1 新增教育类型")
    public Result<Object> add(@RequestBody FirstCategoryDTO dto) {
        return service.add(dto);
    }

    @PostMapping("/query")
    @ApiOperation("12.0.2 查询教育类型")
    public Result<List<FirstCategoryVO>> query(@RequestParam(name = "type") @NotNull(message = "类型不能为空") Integer type) {
        return service.queryByType(type);
    }

    @PostMapping("/query/{id}")
    @ApiOperation("12.0.3 查询教育类型详情")
    public Result<FirstCategoryOneVO> queryOne(@PathVariable(name = "id") Long id) {
        return service.queryOne(id);
    }
    @PostMapping("/update")
    @ApiOperation("12.0.4 修改教育类型")
    public Result<Object> update(@RequestBody FirstCategoryDTO dto) {
        return service.update(dto);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("12.0.5 删除教育类型")
    public Result<Object> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/list")
    @ApiOperation("12.0.6 查询教育类型和所属分类、标签")
    public Result<List<FirstCategoryListVO>> list(@RequestParam(name = "type") @NotNull(message = "类型不能为空") Integer type) {
        return service.listByType(type);
    }
}

