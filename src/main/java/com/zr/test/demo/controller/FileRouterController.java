package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiCourseTag;
import com.zr.test.demo.config.swagger.annotation.ApiFile;
import com.zr.test.demo.config.swagger.annotation.ApiTag;
import com.zr.test.demo.model.vo.FileVO;
import com.zr.test.demo.service.IFileRouterService;
import io.swagger.annotations.Api;
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
@Api(tags = "15-文件上传")
@ApiFile
public class FileRouterController {
    @Autowired
    private IFileRouterService service;
    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result<FileVO> upload(@RequestPart(name = "file") MultipartFile file) {
        return service.upload(file);
    }

}

