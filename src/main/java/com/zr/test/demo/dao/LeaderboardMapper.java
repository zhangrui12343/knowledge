package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.Leaderboard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zr
 * @since 2022-05-30
 */
@Repository
@Mapper
public interface LeaderboardMapper extends BaseMapper<Leaderboard> {

}
