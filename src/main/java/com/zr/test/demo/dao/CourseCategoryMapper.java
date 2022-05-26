package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.CourseCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CourseCategoryMapper extends BaseMapper<CourseCategoryEntity> {

    @Select("<script>" +
            "select id from course_category where 1=1  " +
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
