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
import com.zr.test.demo.dao.CarouselMapper;
import com.zr.test.demo.model.vo.CarouselVO;
import com.zr.test.demo.service.ICarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.support.FileRouterBiz;
import com.zr.test.demo.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-30
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {
    private final FileRouterBiz fileRouterBiz;

    @Autowired
    public CarouselServiceImpl(FileRouterBiz fileRouterBiz) {
        this.fileRouterBiz = fileRouterBiz;
    }

    @Override
    public Result<Object> add(Carousel dto) {
        if (dto.getImg() == null || StringUtil.isEmpty(dto.getUrl())) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        if (dto.getOrderr() == null) {
            dto.setOrderr(0);
        }
        if (dto.getStatus() == null) {
            dto.setStatus(0);
        }
        return Result.success(this.baseMapper.insert(dto));
    }

    @Override
    public Result<PageInfo<CarouselVO>> queryByDto(PageTDO page) {
        QueryWrapper<Carousel> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("status","`orderr`");
        Page<Carousel> p = PageHelper.startPage(page.getPage(), page.getPageSize()).doSelectPage(() ->
                this.baseMapper.selectList(queryWrapper));
        List<CarouselVO> res=new ArrayList<>();

        p.getResult().forEach(e->{
            CarouselVO vo=new CarouselVO();
            BeanUtils.copyProperties(e,vo);
            vo.setImg(fileRouterBiz.selectFile(e.getImg()));
            res.add(vo);
        });
        PageInfo<CarouselVO> pageInfo = new PageInfo<>();
        pageInfo.setList(res);
        pageInfo.setPage(page.getPage());
        pageInfo.setPageSize(page.getPageSize());
        pageInfo.setTotal(p.getTotal());
        return Result.success(pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id) {
        Carousel carousel = this.baseMapper.selectById(id);
        if (carousel == null) {
            return Result.success(0);
        }
        fileRouterBiz.deleteOldFile(carousel.getImg());
        return Result.success(this.baseMapper.deleteById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateByDto(Carousel dto) {
        if(dto.getId()==null||dto.getImg()==null||StringUtil.isEmpty(dto.getUrl())){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        Carousel carousel = this.baseMapper.selectById(dto.getId());
        if (carousel == null) {
            return Result.success(0);
        }
        if(!dto.getImg().equals(carousel.getImg())){
            //删除旧文件
            fileRouterBiz.deleteOldFile(carousel.getImg());
        }

        return Result.success(this.baseMapper.updateById(dto));
    }
}
