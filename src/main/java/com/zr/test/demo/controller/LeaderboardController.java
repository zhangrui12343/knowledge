package com.zr.test.demo.controller;


import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.swagger.annotation.ApiLeaderboard;
import com.zr.test.demo.model.dto.PageTDO;
import com.zr.test.demo.model.entity.Leaderboard;
import com.zr.test.demo.service.ILeaderboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zr
 * @since 2022-05-30
 */
@RestController
@RequestMapping("/leaderboard")
@Api(tags = "21-排行榜管理")
@ApiLeaderboard
public class LeaderboardController {
    @Autowired
    private ILeaderboardService service;

    @PostMapping("/add")
    @ApiOperation("21.0.1 新增排行榜")
    public Result<Object> add(@RequestBody Leaderboard dto) {
        return service.add(dto);
    }

    @PostMapping("/query/{type}")
    @ApiOperation("21.0.2 排行榜查询")
    public Result<PageInfo<Leaderboard>> query(@RequestBody PageTDO page,@PathVariable Integer type) {
        return service.queryByDto(page,type);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("21.0.3 删除排行榜")
    public Result<Object> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/update")
    @ApiOperation("21.0.4 修改排行榜")
    public Result<Object> update(@RequestBody Leaderboard dto) {
        return service.updateByDto(dto);
    }
}

