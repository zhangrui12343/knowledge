package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiApp;
import com.zr.test.demo.config.swagger.annotation.ApiAppType;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.vo.AppOneVO;
import com.zr.test.demo.model.vo.AppVO;
import com.zr.test.demo.model.vo.OtherCourseOneVO;
import com.zr.test.demo.model.vo.OtherCourseVO;
import com.zr.test.demo.service.IAppService;
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
@RequestMapping("/app")
@Api(tags = "16-信课融合软件管理 api")
@ApiApp
public class AppController {

    @Autowired
    private IAppService service;
    @PostMapping("/add")
    @ApiOperation("16.0.1 新增信课融合软件")
    public Result<Object> add(@RequestBody AppDTO dto, HttpServletRequest request) {
        return service.add(dto,request);
    }

    @PostMapping("/query")
    @ApiOperation("16.0.2 查询信课融合软件")
    public Result<PageInfo<AppVO>> query(@RequestBody AppQueryDTO dto, HttpServletRequest request) {
        return service.queryByDto(dto,request);
    }
    @PostMapping("/query/{id}")
    @ApiOperation("16.0.3 查询信课融合软件")
    public Result<AppOneVO> queryOne(@PathVariable Long id, HttpServletRequest request) {
        return service.queryOne(id,request);
    }

    @PostMapping("/update")
    @ApiOperation("16.0.4 修改信课融合软件")
    public Result<Object> update(AppDTO dto, HttpServletRequest request) {
        return service.updateByDto(dto,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("16.0.5 删除信课融合软件")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
        return service.delete(id,request);
    }
    @PostMapping("/updatestatus")
    @ApiOperation("16.0.6 修改上下架状态")
    public Result<Object> updateStatus(@RequestBody StatusDTO dto, HttpServletRequest request) {
        return service.updateStatus(dto,request);
    }
    @PostMapping("/querybyname")
    @ApiOperation("16.0.7 查询信课融合软件")
    public Result<List<AppOneVO>> queryByName(@RequestParam(name = "name") String name,HttpServletRequest request) {
        return service.queryAppName(name,request);
    }
}

