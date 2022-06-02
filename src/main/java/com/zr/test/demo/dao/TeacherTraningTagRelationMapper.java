package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.model.entity.TeacherTraningTagRelation;
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
 * @since 2022-05-30
 */
@Repository
@Mapper
public interface TeacherTraningTagRelationMapper extends BaseMapper<TeacherTraningTagRelation> {
    @Select("select a.id,a.name from tag as a left join teacher_traning_tag_relation b on a.id=b.tag_id where b.after_course_id =#{courseId}")
    List<Tag> selectTagByCourseId(@Param("courseId") Long courseId);
}
