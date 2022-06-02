package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiApp;
import com.zr.test.demo.config.swagger.annotation.ApiCarousel;
import com.zr.test.demo.model.dto.PageTDO;
import com.zr.test.demo.model.entity.Carousel;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.model.vo.CarouselVO;
import com.zr.test.demo.service.ICarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-30
 */
@RestController
@RequestMapping("/carousel")
@Api(tags = "20-轮播图管理")
@ApiCarousel
public class CarouselController {
    @Autowired
    private ICarouselService service;

    @PostMapping("/add")
    @ApiOperation("20.0.1 新增轮播图")
    public Result<Object> add(@RequestBody Carousel dto) {
        return service.add(dto);
    }

    @PostMapping("/query")
    @ApiOperation("20.0.2 轮播图查询")
    public Result<PageInfo<CarouselVO>> query(@RequestBody PageTDO page) {
        return service.queryByDto(page);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("20.0.3 删除轮播图")
    public Result<Object> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    @ApiOperation("20.0.4 修改轮播图")
    public Result<Object> update(@RequestBody Carousel dto) {
        return service.updateByDto(dto);
    }
}

