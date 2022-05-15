package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.CourseCategoryDTO;
import com.zr.test.demo.model.dto.RoleDTO;
import com.zr.test.demo.model.vo.CourseCategoryVO;
import com.zr.test.demo.model.vo.RoleVO;
import com.zr.test.demo.service.ICourseCategoryService;
import com.zr.test.demo.service.IRoleService;
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
@RequestMapping("/course-category")
public class CourseCategoryController {
    @Autowired
    private ICourseCategoryService service;

    @PostMapping("/add")
    @ApiOperation("4.0.1 新增课程分类")
    public Result<Object> add(@RequestBody CourseCategoryDTO dto, HttpServletRequest request) {
        return service.add(dto);
    }

    @PostMapping("/query")
    @ApiOperation("4.0.2 查询课程分类")
    public Result<List<CourseCategoryVO>> query(HttpServletRequest request) {
        return service.query(request);
    }

    @PostMapping("/update")
    @ApiOperation("4.0.3 修改课程分类")
    public Result<Object> update(HttpServletRequest request, @RequestBody CourseCategoryDTO dto) {
        return service.update(dto,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("4.0.4 删除课程分类")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
        return service.delete(id,request);
    }

}

