package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.PageTDO;
import com.zr.test.demo.model.entity.Leaderboard;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-30
 */
public interface ILeaderboardService extends IService<Leaderboard> {

    Result<Object> add(Leaderboard dto);

    Result<PageInfo<Leaderboard>> queryByDto(PageTDO page, Integer type);

    Result<Object> delete(Long id);

    Result<Object> updateByDto(Leaderboard dto);
}
