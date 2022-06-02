package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiAfterCourse;
import com.zr.test.demo.config.swagger.annotation.ApiTopic;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.vo.*;
import com.zr.test.demo.service.IAfterCourseService;
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
@RequestMapping("/topic")
@ApiTopic
@Api(tags = "10-专题教育管理")
public class TopicController {
    @Autowired
    private ITopicService service;

    @PostMapping("/add")
    @ApiOperation("10.0.1 新增专题")
    public Result<Object> add(@RequestBody OtherCourseDTO dto, HttpServletRequest request) {
        return service.add(dto,request);
    }

    @PostMapping("/query")
    @ApiOperation("10.0.2 查询专题")
    public Result<PageInfo<OtherCourseVO>> query(@RequestBody OtherCourseQueryDTO dto, HttpServletRequest request) {
        return service.query(dto,request);
    }
    @PostMapping("/query/{id}")
    @ApiOperation("10.0.3 查询专题")
    public Result<OtherCourseOneVO> queryOne(@PathVariable Long id, HttpServletRequest request) {
        return service.queryOne(id,request);
    }

    @PostMapping("/update")
    @ApiOperation("10.0.4 修改专题")
    public Result<Object> update(@RequestBody OtherCourseDTO dto, HttpServletRequest request) {
        return service.update(dto,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("10.0.5 删除专题")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
        return service.delete(id,request);
    }

    @PostMapping("/updatestatus")
    @ApiOperation("10.0.6 修改上下架状态")
    public Result<Object> updateStatus(@RequestBody StatusDTO dto, HttpServletRequest request) {
        return service.updateStatus(dto,request);
    }
    @PostMapping("/list")
    @ApiOperation("10.0.7 前端用查询专题")
    public Result<List<OtherCourseListVO>> list() {
        return service.findList();
    }
    @PostMapping("/listMore")
    @ApiOperation("10.0.8 前端用更多查询专题")
    public Result<PageInfo<OtherCourseWebVO>> listMore(@RequestBody OtherCourseListDTO dto) {
        return service.listMore(dto);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation("10.0.9 前端用查询专题")
    public Result<WebAfterDetailVO> detail(@PathVariable Long id) {
        return service.detail(id);
    }

}

