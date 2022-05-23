package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.model.entity.AfterCourseTagRelation;
import com.zr.test.demo.dao.AfterCourseTagRelationMapper;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.service.IAfterCourseTagRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-23
 */
@Service
public class AfterCourseTagRelationServiceImpl extends ServiceImpl<AfterCourseTagRelationMapper, AfterCourseTagRelation> implements IAfterCourseTagRelationService {
    public String selectTagNamesByCourseId(Long courseId){
        List<Tag> tags=this.getBaseMapper().selectTagByCourseId(courseId);
        if(ListUtil.isEmpty(tags)){
            return "";
        }
        StringBuilder sb=new StringBuilder();
        tags.forEach(tag-> sb.append(tag.getName()).append(","));
        return sb.substring(0,sb.length()-1);
    }
    public List<Long> selectTagIdByCourseId(Long courseId){
        QueryWrapper<AfterCourseTagRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<AfterCourseTagRelation> tags=this.getBaseMapper().selectList(queryWrapper);
        if(ListUtil.isEmpty(tags)){
            return null;
        }
        return tags.stream().map(AfterCourseTagRelation::getTagId).collect(Collectors.toList());
    }
}
