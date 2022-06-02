package com.zr.test.demo.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.AfterCourseTagRelationMapper;
import com.zr.test.demo.model.entity.AfterCourseTagRelation;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AfterCourseTagRelationBiz {
    @Autowired
    private AfterCourseTagRelationMapper mapper;
    public String selectTagNamesByCourseId(Long courseId){
        List<Tag> tags=this.mapper.selectTagByCourseId(courseId);
        if(ListUtil.isEmpty(tags)){
            return "";
        }
        StringBuilder sb=new StringBuilder();
        tags.forEach(tag-> sb.append(tag.getName()).append(","));
        return sb.substring(0,sb.length()-1);
    }
    public List<String> selectNamesByCourseId(Long courseId){
        List<Tag> tags=this.mapper.selectTagByCourseId(courseId);
        if(ListUtil.isEmpty(tags)){
            return new ArrayList<>();
        }
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }
    public List<Long> selectTagIdByCourseId(Long courseId){
        QueryWrapper<AfterCourseTagRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("after_course_id",courseId);
        List<AfterCourseTagRelation> tags=this.mapper.selectList(queryWrapper);
        if(ListUtil.isEmpty(tags)){
            return Collections.emptyList();
        }
        return tags.stream().map(AfterCourseTagRelation::getTagId).collect(Collectors.toList());
    }
}
