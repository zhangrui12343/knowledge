package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiCourse;
import com.zr.test.demo.model.dto.CourseDTO;
import com.zr.test.demo.model.dto.CourseQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.vo.CourseListVO;
import com.zr.test.demo.model.vo.CourseOneVO;
import com.zr.test.demo.model.vo.CourseVO;
import com.zr.test.demo.model.vo.WebCourseDetailVO;
import com.zr.test.demo.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
@RestController
@RequestMapping("/course")
@ApiCourse
@Api(tags = "5-课程管理")
public class CourseController {
    @Autowired
    private ICourseService service;

    @PostMapping("/add")
    @ApiOperation("5.0.1 新增课程")
    public Result<Object> add(@RequestBody CourseDTO dto,
                              HttpServletRequest request) {
        return service.add(dto,request);
    }

    @PostMapping("/query")
    @ApiOperation("5.0.2 查询课程")
    public Result<PageInfo<CourseVO>> query(@RequestBody CourseQueryDTO dto, HttpServletRequest request) {
        return service.query(dto,request);
    }

    @PostMapping("/update")
    @ApiOperation("5.0.4 修改课程")
    public Result<Object> update(@RequestBody CourseDTO dto,HttpServletRequest request) {
        return service.update(dto,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("5.0.5 删除课程")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
        return service.delete(id,request);
    }

    @PostMapping("/updatestatus")
    @ApiOperation("5.0.6 修改上下架状态")
    public Result<Object> updateStatus(@RequestBody StatusDTO dto, HttpServletRequest request) {
        return service.updateStatus(dto,request);
    }

    @PostMapping("/query/{id}")
    @ApiOperation("5.0.3 查询课程")
    public Result<CourseOneVO> queryOne(@PathVariable Long id, HttpServletRequest request) {
        return service.queryOne(id,request);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation("5.0.7 前端用查询课程详情")
    public Result<WebCourseDetailVO> detail(@PathVariable Long id) {
        return service.detail(id);
    }

    @PostMapping("/list")
    @ApiOperation("5.0.8 前端用课程列表")
    public Result<PageInfo<CourseListVO>> list(@RequestBody CourseQueryDTO dto) {
        return service.list(dto);
    }
}

