package com.zr.test.demo.controller;



import com.zr.test.demo.common.Result;

import com.zr.test.demo.config.swagger.annotation.ApiTool;

import com.zr.test.demo.model.entity.Tool;
import com.zr.test.demo.model.entity.ToolAppRelation;

import com.zr.test.demo.model.vo.ToolAppVO;
import com.zr.test.demo.model.vo.ToolVO;

import com.zr.test.demo.service.IToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/tool")
@Api(tags = "18-矩阵管理 api")
@ApiTool
public class ToolController {
    @Autowired
    private IToolService service;

    @PostMapping("/add")
    @ApiOperation("18.0.1 新增矩阵名称")
    public Result<Object> add(@RequestBody Tool dto, HttpServletRequest request) {
        return service.add(dto, request);
    }
    @PostMapping("/query")
    @ApiOperation("18.0.2 查询所有矩阵")
    public Result<List<ToolVO>> query() {
        return service.queryAll();
    }
    @PostMapping("/addapp")
    @ApiOperation("18.0.3 新增矩阵下的app")
    public Result<Object> addApp(@RequestBody ToolAppRelation dto, HttpServletRequest request) {
        return service.addRelation(dto, request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("18.0.4 删除矩阵名称")
    public Result<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        return service.delete(id, request);
    }
    @PostMapping("/deleterelation")
    @ApiOperation("18.0.5 删除矩阵下面的软件 关联关系")
    public Result<Object> deleteRelation(@RequestBody ToolAppRelation id, HttpServletRequest request) {
        return service.deleteRelation(id, request);
    }
    @PostMapping("/update")
    @ApiOperation("18.0.6 修改矩阵名称")
    public Result<Object> update(@RequestBody Tool dto, HttpServletRequest request) {
        return service.updateByDto(dto, request);
    }

    @PostMapping("/list")
    @ApiOperation("18.0.7 web使用，查询所有矩阵和关联的app")
    public Result<List<ToolAppVO>> list() {
        return service.findAll();
    }


}

