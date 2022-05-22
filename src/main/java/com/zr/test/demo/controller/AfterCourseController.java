package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.AfterCourseDTO;
import com.zr.test.demo.model.dto.AfterCourseQueryDTO;
import com.zr.test.demo.model.dto.CourseDTO;
import com.zr.test.demo.model.dto.CourseQueryDTO;
import com.zr.test.demo.model.vo.AfterCourseVO;
import com.zr.test.demo.model.vo.CourseOneVO;
import com.zr.test.demo.model.vo.CourseVO;
import com.zr.test.demo.service.IAfterCourseService;
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
 * @since 2022-05-20
 */
@RestController
@RequestMapping("/after-course")
public class AfterCourseController {
    @Autowired
    private IAfterCourseService service;

    @PostMapping("/add")
    @ApiOperation("5.0.1 新增课程")
    public Result<Object> add(@RequestBody AfterCourseDTO dto, HttpServletRequest request) {
        return service.add(dto,request);
    }

    @PostMapping("/query")
    @ApiOperation("5.0.2 查询课程")
    public Result<PageInfo<AfterCourseVO>> query(@RequestBody AfterCourseQueryDTO dto, HttpServletRequest request) {
        return service.query(dto,request);
    }
//    @PostMapping("/query/{id}")
//    @ApiOperation("5.0.2 查询课程")
//    public Result<CourseOneVO> query(@PathVariable Long id, HttpServletRequest request) {
//        return service.queryOne(id,request);
//    }
//
//    @PostMapping("/update")
//    @ApiOperation("5.0.3 修改课程")
//    public Result<Object> update(CourseDTO dto,
//                                 @RequestPart(name = "img",required =false ) MultipartFile img,
//                                 @RequestPart(name = "video",required =false ) MultipartFile video,
//                                 @RequestPart(name = "learningTask",required = false) MultipartFile learningTask,
//                                 @RequestPart(name = "homework",required = false) MultipartFile homework,HttpServletRequest request) {
//        return service.update(dto,img,video,learningTask,homework,request);
//    }
//
//    @PostMapping("/delete/{id}")
//    @ApiOperation("5.0.4 删除课程")
//    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
//        return service.delete(id,request);
//    }
}

