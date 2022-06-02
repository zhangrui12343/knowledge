package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.FirstTag;
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
 * @since 2022-05-22
 */
@Repository
@Mapper
public interface FirstTagMapper extends BaseMapper<FirstTag> {
    @Select("select tag_id from first_tag where first_id = #{first}")
    List<Long> getTagIdsByFirstId(@Param("first") Long firstId);
}
