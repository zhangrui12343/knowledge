package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zr.test.demo.dao.CourseCategoryMapper;
import com.zr.test.demo.dao.CourseMapper;
import com.zr.test.demo.model.dto.CourseQueryDTO;
import com.zr.test.demo.model.entity.CourseCategoryEntity;
import com.zr.test.demo.model.entity.CourseEntity;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import com.zr.test.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Component
public class CourseMapperImpl {
    @Autowired
    private CourseMapper dao;
    public int insertOne(CourseEntity entity){
        return dao.insert(entity);
    }

    public List<CourseEntity> selectByEntity(CourseEntity entity){
        if(entity==null){
            return dao.selectList(null);
        }
        QueryWrapper<CourseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        queryWrapper.orderByDesc("time");
        return dao.selectList(queryWrapper);
    }
    public List<CourseEntity> selectByIds(List<Long> ids){
        return dao.selectBatchIds(ids);
    }

    public int updateById(CourseEntity role){
        return dao.updateById(role);
    }

    public int deleteById(Long id){
        return dao.deleteById(id);
    }

    public int deleteByIds(List<Long> id){
        return dao.deleteBatchIds(id);
    }

    public IPage<CourseEntity> selectPageByTime(CourseEntity entity, String start, String end, Integer page, Integer pageSize) {
        QueryWrapper<CourseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        if(!StringUtil.isEmpty(start)&&!StringUtil.isEmpty(end)){
            queryWrapper.gt("time", TimeUtil.getDate(start)).lt("time", TimeUtil.getDate(end));
        }
        queryWrapper.orderByDesc("time");
        return dao.selectPage(new Page<>(page,pageSize),queryWrapper);
    }
}
