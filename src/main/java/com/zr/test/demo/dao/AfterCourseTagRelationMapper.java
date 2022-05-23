package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.AfterCourseTagRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.test.demo.model.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zr
 * @since 2022-05-23
 */
public interface AfterCourseTagRelationMapper extends BaseMapper<AfterCourseTagRelation> {


    @Select("select a.id,a.name from tag as a left join after_course_tag_relation b where a.id=b.tag_id and b.after_course_id =#{courseId}")
    List<Tag> selectTagByCourseId(@Param("courseId") Long courseId);
}
