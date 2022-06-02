package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.FirstSecond;
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
public interface FirstSecondMapper extends BaseMapper<FirstSecond> {
    @Select("select second_id from first_second where first_id = #{first}")
    List<Long> getSecondIdsByFirstId(@Param("first") Long firstId);
}
