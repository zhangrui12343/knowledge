package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.service.IFileRouterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-21
 */
@RestController
@RequestMapping("/file")
public class FileRouterController {
    @Autowired
    private IFileRouterService service;
    @PostMapping("/upload")
    @ApiOperation("5.0.1 新增课程")
    public Result<Object> upload(@RequestPart(name = "file") MultipartFile[] file,@RequestParam(name = "ids",required = false) List<Long> ids) {
        return service.upload(file,ids);
    }

}

