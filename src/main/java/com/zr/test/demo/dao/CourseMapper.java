package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.CourseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
@Repository
@Mapper
public interface CourseMapper extends BaseMapper<CourseEntity> {

}
