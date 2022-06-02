package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.SecondCategory;
import com.zr.test.demo.model.entity.TopicCategoryRelation;
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
public interface TopicCategoryRelationMapper extends BaseMapper<TopicCategoryRelation> {
    @Select("select a.id,a.name from second_category as a left join topic_category_relation b on a.id=b.category_id where b.after_course_id =#{courseId}")
    List<SecondCategory> selectCategoryByCourseId(@Param("courseId") Long courseId);
}
