package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.PageTDO;
import com.zr.test.demo.model.entity.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.CarouselVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-30
 */
public interface ICarouselService extends IService<Carousel> {

    Result<Object> add(Carousel dto);

    Result<PageInfo<CarouselVO>> queryByDto(PageTDO page);

    Result<Object> delete(Long id);

    Result<Object> updateByDto(Carousel dto);
}
