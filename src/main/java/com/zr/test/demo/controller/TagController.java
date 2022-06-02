package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiCourse;
import com.zr.test.demo.config.swagger.annotation.ApiTag;
import com.zr.test.demo.model.dto.CourseCategoryDTO;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.model.vo.CourseCategoryVO;
import com.zr.test.demo.service.ICourseCategoryService;
import com.zr.test.demo.service.ITagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/tag")
@ApiTag
@Api(tags = "13-标签管理")
public class TagController {
    @Autowired
    private ITagService service;

    @PostMapping("/add")
    @ApiOperation("13.0.1 新增tag")
    public Result<Object> add(@RequestBody Tag dto) {
        return service.add(dto);
    }

    @PostMapping("/query/{id}")
    @ApiOperation("13.0.2 根据教育类型id查询tag,id为-3 查询所有标签,id=0,-1,-2分别按课后、专题、师研查询")
    public Result<List<Tag>> query(@PathVariable Long id) {
        return service.queryByDto(id);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("13.0.3 删除tag")
    public Result<Object> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    @ApiOperation("13.0.4 修改tag")
    public Result<Object> update(@RequestBody Tag dto) {
        return service.update(dto);
    }
}

