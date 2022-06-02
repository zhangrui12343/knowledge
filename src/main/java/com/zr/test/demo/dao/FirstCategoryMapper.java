package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.FirstCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zr
 * @since 2022-05-16
 */
@Repository
@Mapper
public interface FirstCategoryMapper extends BaseMapper<FirstCategory> {
    @Select("select a.name from second_category as a,first_second as b where a.id=b.second_id and b.first_id=#{id}")
    List<String> selectCategoryNamesByFirstId(@Param("id") Long id);

}
