package com.zr.test.demo.controller;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.ApiUser;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.vo.RoleVO;
import com.zr.test.demo.model.vo.SystemUserVO;
import com.zr.test.demo.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/role")
@Api(tags = "2-role")
@ApiUser
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/add")
    @ApiOperation("2.0.1 新增权限")
    public Result<Object> add(HttpServletRequest request, @RequestBody RoleDTO role) {
        return roleService.add(role,request);
    }

    @PostMapping("/query")
    @ApiOperation("2.0.2 查询权限")
    public Result<List<RoleVO>> query(HttpServletRequest request) {
        return roleService.query(request);
    }

    @PostMapping("/update")
    @ApiOperation("2.0.3 修改权限")
    public Result<Object> update(HttpServletRequest request, @RequestBody RoleDTO role) {
        return roleService.update(role,request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("2.0.4 删除权限")
    public Result<Object> delete(HttpServletRequest request, @PathVariable Integer id) {
        return roleService.delete(id,request);
    }

}
