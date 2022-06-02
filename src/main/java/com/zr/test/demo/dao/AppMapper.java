package com.zr.test.demo.dao;

import com.zr.test.demo.model.entity.App;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.test.demo.model.entity.AppCategory;
import com.zr.test.demo.model.vo.AppAndCaseVO;
import com.zr.test.demo.model.vo.CaseAppVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
@Repository
@Mapper
public interface AppMapper extends BaseMapper<App> {
    @Select("<script> " +
            "select a.id,a.name,a.logo,a.img,a.introduction,a.type,a.subject,a.platform,a.tags,a.status,a.time,a.universal," +
            " b.id as caseid,b.order as caseorder from app as a,app_case as b where b.app_id= a.id and a.status =1 and b.status =1  " +
            " <if test=\"type != null \"> and a.type=#{type} </if>" +

            " order by a.time desc" +
            "</script>")
    List<AppAndCaseVO> selectByType(@Param("type") Long type);

    @Select(
            "select a.id,a.name,a.logo,a.img,a.introduction,a.type,a.subject,a.platform,a.tags,a.status,a.time,a.universal," +
                    " b.id as caseid,b.order as caseorder from app as a,app_case as b where b.app_id= a.id" +
                    " and a.status =1 and b.status =1 and a.id !=#{id} " +
                    " order by a.time desc"
    )
    List<AppAndCaseVO> selectAllNotMe(Long id);

    @Select("delete from app_case where app_id=#{id}")
    void deleteAppCaseByAppId(Long id);

    @Select("select video from app_case where app_id=#{id}")
    List<Long> selectAppCaseVideoByAppId(Long id);

    @Select("delete from tool_app_relation where app_id=#{id}")
    void deleteToolRelationByAppId(Long id);

    @Select("select * from app_category")
    List<AppCategory> selectAppCategory();
}
