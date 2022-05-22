package com.zr.test.demo.controller;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.ApiUser;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/findPassword")
    @ApiOperation("1.0.4 findPassword")
    public Result<Object> findPassword(HttpServletRequest request,FindPasswordDTO dto) {
        return userService.findPassword(request,dto);
    }
    @PostMapping("/getCode")
    @ApiOperation("1.0.5 获取验证码")
    public Result<Object> getCode(HttpServletRequest request,@RequestParam(name = "phone") String phone) {
        return userService.getCode(request,phone);
    }
}
