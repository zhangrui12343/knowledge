package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.CourseDTO;
import com.zr.test.demo.model.dto.CourseQueryDTO;
import com.zr.test.demo.model.vo.CourseOneVO;
import com.zr.test.demo.model.vo.CourseVO;
import com.zr.test.demo.service.ICourseService;
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
public class CourseController {
    @Autowired
    private ICourseService service;

    @PostMapping("/add")
    @ApiOperation("5.0.1 新增课程")
    public Result<Object> add(CourseDTO dto,
                              @RequestPart(name = "img") MultipartFile img,
                              @RequestPart(name = "video",required =false ) MultipartFile video,
                              @RequestPart(name = "learningTask",required = false) MultipartFile learningTask,
                              @RequestPart(name = "homework",required = false) MultipartFile homework,
                              HttpServletRequest request) {
        return service.add(dto,img,learningTask,homework,video,request);
    }

    @PostMapping("/query")
    @ApiOperation("5.0.2 查询课程")
    public Result<PageInfo<CourseVO>> query(@RequestBody CourseQueryDTO dto, HttpServletRequest request) {
        return service.query(dto,request);
    }
    @PostMapping("/query/{id}")
    @ApiOperation("5.0.2 查询课程")
    public Result<CourseOneVO> query(@PathVariable Long id, HttpServletRequest request) {
        return service.queryOne(id,request);
    }

    @PostMapping("/update")
    @ApiOperation("5.0.3 修改课程")
    public Result<Object> update(CourseDTO dto, @RequestPart(name = "img",required = false) MultipartFile img,@RequestPart(name = "pdf",required =false ) MultipartFile[] pdf,
                                 @RequestPart(name = "video",required = false) MultipartFile video,HttpServletRequest request) {
        return service.update(dto,img,pdf,video,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("5.0.4 删除课程")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
        return service.delete(id,request);
    }
}

