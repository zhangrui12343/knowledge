package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.test.demo.model.vo.OtherCourseWebVO;
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
 * @since 2022-05-24
 */
@Repository
@Mapper
public interface TopicMapper extends BaseMapper<Topic> {
    @Select("select id,name,img,description from topic where type=#{type} and status=1  order by time desc limit 3")
    List<OtherCourseWebVO> selectLimit3(@Param("type") Long type);

    @Select("<script>" +
            " select a.id,a.name,a.img,a.count from topic as a " +
            " left join topic_tag_relation as b on a.id=b.after_course_id " +
            " left join topic_category_relation as c on a.id=c.after_course_id " +
            " where a.status=1   " +
            " <if test=\"type != null \"> and a.type=#{type} </if>" +
            " <if test=\"category != null \"> and c.category_id=#{category} </if>" +
            " <if test=\"tag != null \"> and b.tag_id=#{tag} </if>" +
            " order by time desc" +
            "</script>")
    List<OtherCourseWebVO> selectMore(@Param("type") Long type,@Param("category") Long category,@Param("tag") Long tag);
    @Select("update topic set count= count +1 where id =#{id}")
    void addCount(Long id);
}
