package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.PageTDO;
import com.zr.test.demo.model.entity.Carousel;
import com.zr.test.demo.model.entity.Leaderboard;
import com.zr.test.demo.dao.LeaderboardMapper;
import com.zr.test.demo.service.ILeaderboardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.StringUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-30
 */
@Service
public class LeaderboardServiceImpl extends ServiceImpl<LeaderboardMapper, Leaderboard> implements ILeaderboardService {

    @Override
    public Result<Object> add(Leaderboard dto) {
        if (dto.getType() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        return Result.success(this.baseMapper.insert(dto));
    }

    @Override
    public Result<PageInfo<Leaderboard>> queryByDto(PageTDO page, Integer type) {
        Leaderboard leaderboard=new Leaderboard();
        leaderboard.setType(type);
        QueryWrapper<Leaderboard> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("`orderr`");
        queryWrapper.setEntity(leaderboard);

        Page<Leaderboard> p = PageHelper.startPage(page.getPage(), page.getPageSize()).doSelectPage(() ->
                this.baseMapper.selectList(queryWrapper));
        PageInfo<Leaderboard> pageInfo = new PageInfo<>();
        pageInfo.setList(p.getResult());
        pageInfo.setPage(page.getPage());
        pageInfo.setPageSize(page.getPageSize());
        pageInfo.setTotal(p.getTotal());
        return Result.success(pageInfo);
    }

    @Override
    public Result<Object> delete(Long id) {
        return Result.success(this.baseMapper.deleteById(id));
    }

    @Override
    public Result<Object> updateByDto(Leaderboard dto) {
        if(dto.getId()==null){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        return Result.success(this.baseMapper.updateById(dto));
    }
}
