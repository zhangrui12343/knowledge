package com.zr.test.demo.controller;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.ApiUser;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.pojo.AuthKey;
import com.zr.test.demo.model.vo.GeneralUserVO;
import com.zr.test.demo.model.vo.SystemUserVO;
import com.zr.test.demo.service.IUserService;
import com.zr.test.demo.util.ParseToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.net.www.ParseUtil;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/user")
@Api(tags = "1-user")
@ApiUser
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    @ApiOperation("1.0.1 user login")
    public Result<Object> login(@RequestBody LoginDTO username,HttpServletRequest request) {
        return this.userService.login(username,request);
    }

    @PostMapping("/register")
    @ApiOperation("1.0.2 user register")
    public Result<Object> register(@RequestBody UserDTO user){
        return userService.register(user);
    }

    @PostMapping("/logout")
    @ApiOperation("1.0.3 user logout")
    public Result<Object> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    @PostMapping("/generalQuery")
    @ApiOperation("1.0.4 查询普通用户")
    public Result<PageInfo<GeneralUserVO>> queryGeneral(HttpServletRequest request, @RequestBody GeneralUserDTO user) {
        return userService.queryGeneral(user, request);
    }

    @PostMapping("/generalUpdate")
    @ApiOperation("1.0.5 修改用户")
    public Result<Object> updateGeneral(HttpServletRequest request, @RequestBody UpdateUserDTO user) {
        return userService.updateGeneral(user,request);
    }

    @PostMapping("/generalDelete")
    @ApiOperation("1.0.6 删除用户")
    public Result<Object> deleteGeneral(HttpServletRequest request, @RequestBody UpdateUserDTO user) {
        return userService.deleteGeneral(user,request);
    }

    @PostMapping("/systemAdd")
    @ApiOperation("1.0.7 新增系统用户")
    public Result<Object> addSystem(HttpServletRequest request, @RequestBody SystemUserDTO user) {
        return userService.addSystem(user,request);
    }

    @PostMapping("/systemQuery")
    @ApiOperation("1.0.8 查询系统用户")
    public Result<PageInfo<SystemUserVO>> querySystem(HttpServletRequest request, @RequestBody GeneralUserDTO user) {
        return userService.querySystem(user,request);
    }

    @PostMapping("/systemUpdate")
    @ApiOperation("1.0.9 修改系统用户")
    public Result<Object> updateSystem(HttpServletRequest request, @RequestBody SystemUserDTO user) {
        return userService.updateSystem(user,request);
    }

    @PostMapping("/systemDelete")
    @ApiOperation("1.1.0 删除系统用户")
    public Result<Object> deleteSystem(HttpServletRequest request, @RequestBody UserIdDTO user) {
        return userService.deleteSystem(user,request);
    }

}
