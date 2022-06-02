package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.CourseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

    @Select("update course set count= count +1 where id =#{id}")
    void addCount(Long id);
}
