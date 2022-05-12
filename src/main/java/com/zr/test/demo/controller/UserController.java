package com.zr.test.demo.controller;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.entity.UserEntity;
import com.zr.test.demo.service.IUserService;
import com.zr.test.demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result<Object> login(@RequestParam String username, @RequestParam String password) throws UnsupportedEncodingException {
        UserEntity user=userService.getUserByPass(username, password);
        if(user == null){
            return Result.fail(ErrorCode.SYS_USER_OR_PWD_ERROR_ERR,"用户名或密码错误");
        }
        //登录成功则调用JWTUtil类的创建Token方法返回客户端
        String token= JWTUtil.createToken(user);
        return Result.success(token);
    }

    @GetMapping("/test")
    public Result<Object> test(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        if(token == null){
            return Result.fail(ErrorCode.SYS_KEY_ERROR,"尚未登录");
        }
        //获取到token中的用户信息
        System.out.println(JWTUtil.getUsername(token));
        //可自行编写获取用户信息后的操作

        return Result.success("test");
    }


}
