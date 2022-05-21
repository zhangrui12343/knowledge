package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.CourseTypeDTO;
import com.zr.test.demo.model.entity.CourseTag;
import com.zr.test.demo.model.vo.CourseTypeVO;
import com.zr.test.demo.service.ICourseTagService;
import com.zr.test.demo.service.ICourseTypeService;
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
 * @since 2022-05-20
 */
@RestController
@RequestMapping("/course-tag")
public class CourseTagController {
    @Autowired
    private ICourseTagService service;

    @PostMapping("/add")
    @ApiOperation("4.0.1 新增课程分类")
    public Result<Object> add(@RequestBody CourseTypeDTO dto) {
        return service.add(dto);
    }

    @PostMapping("/query")
    @ApiOperation("4.0.2 查询课程分类")
    public Result<List<CourseTag>> query() {
        return service.findAll();
    }

    @PostMapping("/update")
    @ApiOperation("4.0.3 修改课程分类")
    public Result<Object> update(@RequestBody CourseTypeDTO dto) {
        return service.update(dto);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("4.0.4 删除课程分类")
    public Result<Object> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}

