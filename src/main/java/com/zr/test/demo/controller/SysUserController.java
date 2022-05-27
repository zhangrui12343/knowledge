package com.zr.test.demo.controller;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiSysUser;
import com.zr.test.demo.config.swagger.annotation.ApiUser;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.vo.*;
import com.zr.test.demo.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/sys-user")
@Api(tags = "2-系统用户管理")
@ApiSysUser
@CrossOrigin(origins = "*", maxAge = 3600)
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @PostMapping("/login")
    @ApiOperation("2.0.1 系统用户")
    public Result<SysLoginVO> login(@RequestBody SysLoginDTO username, HttpServletRequest request) {
        return this.userService.login(username,request);
    }
    @PostMapping("/update/password")
    @ApiOperation("2.0.2 修改系统用户密码")
    public Result<Object> updateSystemPassword(HttpServletRequest request, @RequestBody SystemPasswordDTO user) {
        return userService.updateSystemPassword(user,request);
    }
    @PostMapping("/query/student")
    @ApiOperation("2.0.3 查询学生")
    public Result<PageInfo<StudentVO>> queryStudent(HttpServletRequest request, @RequestBody StudentDTO user) {
        return userService.queryStudent(user, request);
    }
    @PostMapping("/query/general")
    @ApiOperation("2.0.4 查询普通用户")
    public Result<PageInfo<GeneralUserVO>> queryGeneral(HttpServletRequest request, @RequestBody GeneralUserDTO user) {
        return userService.queryGeneral(user, request);
    }
    @PostMapping("/update/general")
    @ApiOperation("2.0.5 修改学生用户")
    public Result<Object> updateStudent(HttpServletRequest request, @RequestBody UpdateStudentDTO user) {
        return userService.updateStudent(user,request);
    }

    @PostMapping("/generalDelete")
    @ApiOperation("2.0.6 删除普通用户")
    public Result<Object> deleteGeneral(HttpServletRequest request, @RequestBody UpdateStudentDTO user) {
        return userService.deleteGeneral(user,request);
    }

    @PostMapping("/add")
    @ApiOperation("2.0.7 新增系统用户")
    public Result<Object> addSystem(HttpServletRequest request, @RequestBody SystemUserDTO user) {
        return userService.addSystem(user,request);
    }

    @PostMapping("/systemQuery")
    @ApiOperation("2.0.8 查询系统用户")
    public Result<PageInfo<SystemUserVO>> querySystem(HttpServletRequest request, @RequestBody GeneralUserDTO user) {
        return userService.querySystem(user,request);
    }

    @PostMapping("/systemUpdate")
    @ApiOperation("2.0.9 修改系统用户")
    public Result<Object> updateSystem(HttpServletRequest request, @RequestBody SystemUserDTO user) {
        return userService.updateSystem(user,request);
    }

    @PostMapping("/systemDelete")
    @ApiOperation("2.1.0 删除系统用户")
    public Result<Object> deleteSystem(HttpServletRequest request, @RequestBody UserIdDTO user) {
        return userService.deleteSystem(user,request);
    }
    @PostMapping("/student/import")
    @ApiOperation("2.1.1 导入学生用户")
    public Result<ExcelImportVO> importStudent(HttpServletRequest request, @RequestParam(name = "file") MultipartFile file) {
        return userService.importStudent(file,request);
    }
}
