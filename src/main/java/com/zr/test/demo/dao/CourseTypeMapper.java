package com.zr.test.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.test.demo.model.entity.CourseCategoryEntity;
import com.zr.test.demo.model.entity.CourseTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
@Repository
@Mapper
public interface CourseTypeMapper extends BaseMapper<CourseTypeEntity> {

    @Select("<script>" +
            "select id from course_type where 1=1  " +
            " <if test=\"pids != null and pids.size > 0\"> " +
            " and pid in (" +
            " <foreach collection=\"pids\" item=\"item\" index=\"index\" separator=\",\">" +
            " #{item}" +
            "  </foreach> " +
            ")" +
            " </if>" +
            "</script>"
    )
    List<Long> selectIdsByPIds(@Param(value = "pids") List<Long> pids);

}
