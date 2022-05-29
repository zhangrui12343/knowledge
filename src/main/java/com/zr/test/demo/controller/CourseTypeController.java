package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiCourseTag;
import com.zr.test.demo.model.dto.CourseCategoryDTO;
import com.zr.test.demo.model.dto.CourseTypeDTO;
import com.zr.test.demo.model.vo.CourseCategoryVO;
import com.zr.test.demo.model.vo.CourseTypeVO;
import com.zr.test.demo.service.ICourseCategoryService;
import com.zr.test.demo.service.ICourseTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  课程分类控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
@RestController
@RequestMapping("/course-type")
@ApiCourseTag
@Api(tags = "8-课程类型")
public class CourseTypeController {
    @Autowired
    private ICourseTypeService service;

    @PostMapping("/add")
    @ApiOperation("8.0.1 新增课程类型")
    public Result<Object> add(@RequestBody CourseTypeDTO dto, HttpServletRequest request) {
        return service.add(dto);
    }

    @PostMapping("/query/id")
    @ApiOperation("8.0.2 根据课程分类查询课程类型树")
    public Result<List<CourseTypeVO>> query(@PathVariable Long id) {
        return service.query(id);
    }

    @PostMapping("/update")
    @ApiOperation("8.0.3 修改课程类型")
    public Result<Object> update(HttpServletRequest request, @RequestBody CourseTypeDTO dto) {
        return service.update(dto,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("8.0.4 删除课程类型")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
        return service.delete(id,request);
    }

}

