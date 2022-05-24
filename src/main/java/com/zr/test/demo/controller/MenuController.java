package com.zr.test.demo.controller;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiMenu;
import com.zr.test.demo.config.swagger.annotation.ApiUser;
import com.zr.test.demo.model.dto.MenuDTO;
import com.zr.test.demo.model.vo.MenuVO;
import com.zr.test.demo.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/menu")
@Api(tags = "4-menu")
@ApiMenu
@CrossOrigin(origins = "*", maxAge = 3600)
public class MenuController {

    @Autowired
    private IMenuService service;

    @PostMapping("/add")
    @ApiOperation("4.0.1 新增页面模块")
    public Result<Object> add(HttpServletRequest request, @RequestBody MenuDTO dto) {
        return service.add(dto,request);
    }
    @PostMapping("/query")
    @ApiOperation("4.0.2 查询页面模块")
    public Result<List<MenuVO>> query(HttpServletRequest request) {
        return service.query(request);
    }

    @PostMapping("/update")
    @ApiOperation("4.0.3 修改页面模块")
    public Result<Object> update(HttpServletRequest request, @RequestBody MenuDTO dto) {
        return service.update(dto,request);
    }
    @PostMapping("/delete/{id}")
    @ApiOperation("4.0.4 删除权限")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Integer id) {
        return service.delete(id,request);
    }

}
