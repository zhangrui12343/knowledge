package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiAfterCourse;
import com.zr.test.demo.model.dto.OtherCourseDTO;
import com.zr.test.demo.model.dto.OtherCourseQueryDTO;
import com.zr.test.demo.model.vo.OtherCourseOneVO;
import com.zr.test.demo.model.vo.OtherCourseVO;
import com.zr.test.demo.service.IAfterCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-20
 */
@RestController
@RequestMapping("/after-course")
@ApiAfterCourse
@Api(tags = "9-课后教育")
public class AfterCourseController {
    @Autowired
    private IAfterCourseService service;

    @PostMapping("/add")
    @ApiOperation("9.0.1 新增课后教育")
    public Result<Object> add(@RequestBody OtherCourseDTO dto, HttpServletRequest request) {
        return service.add(dto,request);
    }

    @PostMapping("/query")
    @ApiOperation("9.0.2 查询课后教育")
    public Result<PageInfo<OtherCourseVO>> query(@RequestBody OtherCourseQueryDTO dto, HttpServletRequest request) {
        return service.query(dto,request);
    }
    @PostMapping("/query/{id}")
    @ApiOperation("9.0.2 查询课后教育")
    public Result<OtherCourseOneVO> queryOne(@PathVariable Long id, HttpServletRequest request) {
        return service.queryOne(id,request);
    }

    @PostMapping("/update")
    @ApiOperation("9.0.3 修改课后教育")
    public Result<Object> update(OtherCourseDTO dto, HttpServletRequest request) {
        return service.update(dto,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("9.0.4 删除课后教育")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
        return service.delete(id,request);
    }
}

