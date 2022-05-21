package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.CourseTagRelationMapper;
import com.zr.test.demo.dao.CourseTypeRelationMapper;
import com.zr.test.demo.model.entity.CourseTagRelation;
import com.zr.test.demo.model.entity.CourseTypeRelation;
import com.zr.test.demo.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Component
public class CourseTypeRelationMapperImpl {
    @Autowired
    private CourseTypeRelationMapper dao;

    public int deleteByCourseId(Long id){
        QueryWrapper<CourseTypeRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        return dao.delete(queryWrapper);
    }
    public int insert(Long courseId, List<Long> typeId){
        typeId.forEach(e -> dao.insert(new CourseTypeRelation(courseId, e)));
        return typeId.size();
    }

    public List<Long> selectByCourseId(Long id){
        QueryWrapper<CourseTypeRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        List<CourseTypeRelation> list=dao.selectList(queryWrapper);
        if(ListUtil.isEmpty(list)){
            return new ArrayList<>();
        }
        return list.stream().map(CourseTypeRelation::getTypeId).collect(Collectors.toList());
    }
}
