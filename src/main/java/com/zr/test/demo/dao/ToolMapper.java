package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.Tool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.test.demo.model.pojo.TeachingTools;
import org.apache.ibatis.annotations.Mapper;
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
 * @since 2022-05-25
 */
@Repository
@Mapper
public interface ToolMapper extends BaseMapper<Tool> {
    @Select("select a.id,a.name as name,d.filePath as path from tool as a " +
            "left join tool_app_relation as b on a.id=b.tool_id " +
            "left join app as c on b.app_id=c.id " +
            "left join file_router as d on c.logo=d.id order by a.name")
    List<TeachingTools> selectAll();
}
