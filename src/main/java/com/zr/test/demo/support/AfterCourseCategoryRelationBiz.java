package com.zr.test.demo.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.AfterCourseCategoryRelationMapper;
import com.zr.test.demo.dao.AfterCourseTagRelationMapper;
import com.zr.test.demo.model.entity.AfterCourseCategoryRelation;
import com.zr.test.demo.model.entity.AfterCourseTagRelation;
import com.zr.test.demo.model.entity.SecondCategory;
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
public class AfterCourseCategoryRelationBiz {
    @Autowired
    private AfterCourseCategoryRelationMapper mapper;
    public String selectCategoryNamesByCourseId(Long courseId){
        List<SecondCategory> secondCategories=this.mapper.selectCategoryByCourseId(courseId);
        if(ListUtil.isEmpty(secondCategories)){
            return "";
        }
        StringBuilder sb=new StringBuilder();
        secondCategories.forEach(secondCategory-> sb.append(secondCategory.getName()).append(","));
        return sb.substring(0,sb.length()-1);
    }
    public List<String> selectNamesByCourseId(Long courseId){
        List<SecondCategory> tags=this.mapper.selectCategoryByCourseId(courseId);
        if(ListUtil.isEmpty(tags)){
            return new ArrayList<>();
        }
        return tags.stream().map(SecondCategory::getName).collect(Collectors.toList());
    }
    public List<Long> selectCategoryIdByCourseId(Long courseId){
        QueryWrapper<AfterCourseCategoryRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("after_course_id",courseId);
        List<AfterCourseCategoryRelation> tags=this.mapper.selectList(queryWrapper);
        if(ListUtil.isEmpty(tags)){
            return Collections.emptyList();
        }
        return tags.stream().map(AfterCourseCategoryRelation::getCategoryId).collect(Collectors.toList());
    }
}
