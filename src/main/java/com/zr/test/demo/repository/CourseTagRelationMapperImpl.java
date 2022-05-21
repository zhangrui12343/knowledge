package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.CourseTagRelationMapper;
import com.zr.test.demo.dao.FileRouterMapper;
import com.zr.test.demo.model.entity.CourseTagRelation;
import com.zr.test.demo.model.entity.CourseTypeRelation;
import com.zr.test.demo.model.entity.FileRouter;
import com.zr.test.demo.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CourseTagRelationMapperImpl {
    @Autowired
    private CourseTagRelationMapper dao;

    public int deleteByCourseId(Long id){
        QueryWrapper<CourseTagRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        return dao.delete(queryWrapper);
    }
    public int insert(Long courseId, List<Long> tagId){
        tagId.forEach(e -> dao.insert(new CourseTagRelation(courseId, e)));
        return tagId.size();
    }

    public List<Long> selectByCourseId(Long id){
        QueryWrapper<CourseTagRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        List<CourseTagRelation> list=dao.selectList(queryWrapper);
        if(ListUtil.isEmpty(list)){
            return new ArrayList<>();
        }
        return list.stream().map(CourseTagRelation::getTypeId).collect(Collectors.toList());
    }
}
