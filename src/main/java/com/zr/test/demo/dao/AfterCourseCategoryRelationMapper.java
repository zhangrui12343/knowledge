package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.AfterCourseCategoryRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.test.demo.model.entity.SecondCategory;
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
 * @since 2022-05-23
 */
@Repository
@Mapper
public interface AfterCourseCategoryRelationMapper extends BaseMapper<AfterCourseCategoryRelation> {
    @Select("select a.id,a.name from second_category as a left join after_course_category_relation b where a.id=b.category_id and b.after_course_id =#{courseId}")
    List<SecondCategory> selectTagByCourseId(@Param("courseId") Long courseId);
}
