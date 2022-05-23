package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.model.entity.AfterCourseCategoryRelation;
import com.zr.test.demo.dao.AfterCourseCategoryRelationMapper;
import com.zr.test.demo.model.entity.AfterCourseTagRelation;
import com.zr.test.demo.model.entity.SecondCategory;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.service.IAfterCourseCategoryRelationService;
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
public class AfterCourseCategoryRelationServiceImpl extends ServiceImpl<AfterCourseCategoryRelationMapper, AfterCourseCategoryRelation> implements IAfterCourseCategoryRelationService {

    public String selectCategoryNamesByCourseId(Long courseId) {
        List<SecondCategory> tags=this.getBaseMapper().selectTagByCourseId(courseId);
        if(ListUtil.isEmpty(tags)){
            return "";
        }
        StringBuilder sb=new StringBuilder();
        tags.forEach(tag-> sb.append(tag.getName()).append(","));
        return sb.substring(0,sb.length()-1);
    }

    public List<Long> selectCategoryIdByCourseId(Long id) {
        QueryWrapper<AfterCourseCategoryRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        List<AfterCourseCategoryRelation> tags=this.getBaseMapper().selectList(queryWrapper);
        if(ListUtil.isEmpty(tags)){
            return null;
        }
        return tags.stream().map(AfterCourseCategoryRelation::getCategoryId).collect(Collectors.toList());
    }
}
