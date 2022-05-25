package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiApp;
import com.zr.test.demo.config.swagger.annotation.ApiAppCase;
import com.zr.test.demo.model.dto.AppCaseDTO;
import com.zr.test.demo.model.dto.AppDTO;
import com.zr.test.demo.model.dto.AppQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.vo.AppCaseOneVO;
import com.zr.test.demo.model.vo.AppCaseVO;
import com.zr.test.demo.model.vo.AppOneVO;
import com.zr.test.demo.model.vo.AppVO;
import com.zr.test.demo.service.IAppCaseService;
import com.zr.test.demo.service.IAppService;
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
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/app-case")
@Api(tags = "17-信课融合案例 api")
@ApiAppCase
public class AppCaseController {

    @Autowired
    private IAppCaseService service;
    @PostMapping("/add")
    @ApiOperation("17.0.1 新增信课融合案例")
    public Result<Object> add(@RequestBody AppCaseDTO dto, HttpServletRequest request) {
        return service.add(dto,request);
    }

    @PostMapping("/query")
    @ApiOperation("17.0.2 查询信课融合案例")
    public Result<PageInfo<AppCaseVO>> query(@RequestBody AppQueryDTO dto, HttpServletRequest request) {
        return service.queryByDto(dto,request);
    }
    @PostMapping("/query/{id}")
    @ApiOperation("17.0.3 查询信课融合案例")
    public Result<AppCaseOneVO> queryOne(@PathVariable Long id, HttpServletRequest request) {
        return service.queryOne(id,request);
    }

    @PostMapping("/update")
    @ApiOperation("17.0.4 修改信课融合案例")
    public Result<Object> update(AppCaseDTO dto, HttpServletRequest request) {
        return service.updateByDto(dto,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("17.0.5 删除信课融合案例")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Long id) {
        return service.delete(id,request);
    }
    @PostMapping("/updatestatus")
    @ApiOperation("17.0.6 修改上下架状态")
    public Result<Object> updateStatus(@RequestBody StatusDTO dto, HttpServletRequest request) {
        return service.updateStatus(dto,request);
    }
}

