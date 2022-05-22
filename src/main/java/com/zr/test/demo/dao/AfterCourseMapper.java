package com.zr.test.demo.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zr.test.demo.model.entity.AfterCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zr
 * @since 2022-05-20
 */
@Repository
@Mapper
public interface AfterCourseMapper extends BaseMapper<AfterCourse> {
    @Select("<script> select a.name as aname,c.name,a.time,a.status, from after_course as a " +
            "left join after_course_first_relation as b on b.after_course_id =a.id " +
            "left join first_category as c on b.first_id =c.id where 1=1 " +
            " <if test=\"type != null and type != ''\"> and b.first_id = #{type} </if> " +
            " <if test=\"category != null and category != ''\"> and c.category= #{category} </if> " +
            " <if test=\"tag != null and tag != ''\"> and c.tag=#{tag} </if>" +
            " <if test=\"name != null and name != ''\"> and a.name like CONCAT('%', #{name}, '%') </if> " +
            "</script>" )
    List<Map<String,Object>> selectByCondition(@Param(value = "type")Long typeId, @Param(value = "category")Long category, @Param(value = "tag")Long tag,@Param(value = "name")String name);
}
