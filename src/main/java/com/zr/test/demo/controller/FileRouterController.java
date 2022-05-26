package com.zr.test.demo.controller;


import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.config.enums.FileTypeEnums;
import com.zr.test.demo.config.swagger.annotation.ApiCourseTag;
import com.zr.test.demo.config.swagger.annotation.ApiFile;
import com.zr.test.demo.config.swagger.annotation.ApiTag;
import com.zr.test.demo.model.entity.FileRouter;
import com.zr.test.demo.model.vo.FileVO;
import com.zr.test.demo.service.IFileRouterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
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
@Api(tags = "19-文件上传")
@ApiFile
@Slf4j
public class FileRouterController {
    @Autowired
    private IFileRouterService service;
    @Value("${file.save.path}")
    private String path = "./upload/";

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result<FileVO> upload(@RequestPart(name = "file") MultipartFile file) {
        return service.upload(file);
    }

    @GetMapping(value="/view")
    @ApiOperation("查看文件,图片、mp4、pdf")
    public Result<Object> view(@RequestParam("id") Integer id, HttpServletResponse response){
        return service.view(id,response);

    }

}

