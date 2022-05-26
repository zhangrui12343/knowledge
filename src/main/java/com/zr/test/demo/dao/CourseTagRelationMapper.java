package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.CourseTagRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zr
 * @since 2022-05-21
 */
@Repository
@Mapper
public interface CourseTagRelationMapper extends BaseMapper<CourseTagRelation> {
    @Select("select a.name from course_tag as a left join course_tag_relation as b on a.id=b.type_id where b.course_id=#{id} ")
    List<String> selectTagNameByCourseId(@Param("id") Long id);
}
