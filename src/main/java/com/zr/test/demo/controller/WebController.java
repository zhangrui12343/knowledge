package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiTool;
import com.zr.test.demo.model.dto.KeywordDTO;

import com.zr.test.demo.model.vo.IndexVO;

import com.zr.test.demo.service.IWebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/web")
@Api(tags = "19-前端页面所需接口 api")
@ApiTool
public class WebController {
    @Autowired
    private IWebService service;

    @PostMapping("/search")
    @ApiOperation("19.0.1 根据关键字查询")
    public Result<Object> query(@RequestBody KeywordDTO dto) {
        return service.query(dto);
    }

    @PostMapping("/index")
    @ApiOperation("19.0.2 首页相关数据")
    public Result<IndexVO> index() {
        return service.index();
    }

}

