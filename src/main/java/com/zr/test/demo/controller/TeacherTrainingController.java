package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiTeacherTraining;
import com.zr.test.demo.config.swagger.annotation.ApiTopic;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.vo.*;
import com.zr.test.demo.service.ITeacherTrainingService;
import com.zr.test.demo.service.ITopicService;
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
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/teacher-training")
@ApiTeacherTraining
@Api(tags = "11-教师研修")
public class TeacherTrainingController {
    @Autowired
    private ITeacherTrainingService service;

    @PostMapping("/add")
    @ApiOperation("11.0.1 新增教师研修")
    public Result<Object> add(@RequestBody OtherCourseDTO dto, HttpServletRequest request) {
        return service.add(dto,request);
    }

    @PostMapping("/query")
    @ApiOperation("11.0.2 查询教师研修")
    public Result<PageInfo<OtherCourseVO>> query(@RequestBody OtherCourseQueryDTO dto, HttpServletRequest request) {
        return service.query(dto,request);
    }
    @PostMapping("/query/{id}")
    @ApiOperation("11.0.3 查询教师研修")
    public Result<OtherCourseOneVO> queryOne(@PathVariable Long id, HttpServletRequest request) {
        return service.queryOne(id,request);
    }

    @PostMapping("/update")
    @ApiOperation("11.0.4 修改教师研修")
    public Result<Object> update(@RequestBody OtherCourseDTO dto, HttpServletRequest request) {
        return service.update(dto,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("11.0.5 删除教师研修")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
        return service.delete(id,request);
    }

    @PostMapping("/updatestatus")
    @ApiOperation("11.0.6 修改上下架状态")
    public Result<Object> updateStatus(@RequestBody StatusDTO dto, HttpServletRequest request) {
        return service.updateStatus(dto,request);
    }
    @PostMapping("/list")
    @ApiOperation("11.0.7 前端用查询师研")
    public Result<List<OtherCourseListVO>> list() {
        return service.findList();
    }
    @PostMapping("/listMore")
    @ApiOperation("11.0.8 前端用更多查询师研")
    public Result<PageInfo<OtherCourseWebVO>> listMore(@RequestBody OtherCourseListDTO dto) {
        return service.listMore(dto);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation("11.0.9 前端用查询师研")
    public Result<WebAfterDetailVO> detail(@PathVariable Long id) {
        return service.detail(id);
    }
}

