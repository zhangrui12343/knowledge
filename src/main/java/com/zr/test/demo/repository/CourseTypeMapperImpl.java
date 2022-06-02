package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.dao.CourseCategoryMapper;
import com.zr.test.demo.dao.CourseTypeMapper;
import com.zr.test.demo.model.entity.CourseCategoryEntity;
import com.zr.test.demo.model.entity.CourseTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseTypeMapperImpl {
    @Autowired
    private CourseTypeMapper dao;
    public int insertOne(CourseTypeEntity entity){
        return dao.insert(entity);
    }

    public List<CourseTypeEntity> selectByEntity(CourseTypeEntity entity){
        if(entity==null){
            return dao.selectList(null);
        }
        QueryWrapper<CourseTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        queryWrapper.orderByAsc("id");
        return dao.selectList(queryWrapper);
    }
    public List<CourseTypeEntity> selectByIds(List<Long> ids){
        return dao.selectBatchIds(ids);
    }
    public CourseTypeEntity selectById(Long id){
        return dao.selectById(id);
    }
    public int updateById(CourseTypeEntity role){
        return dao.updateById(role);
    }

    public int deleteById(Integer id){
        return dao.deleteById(id);
    }

    public int deleteByIds(List<Long> id){
        if(id.isEmpty()){
            return 0;
        }
        return dao.deleteBatchIds(id);
    }
    public List<Long> selectIdsByPIds(List<Long> pids) {
        return dao.selectIdsByPIds(pids);
    }
}
