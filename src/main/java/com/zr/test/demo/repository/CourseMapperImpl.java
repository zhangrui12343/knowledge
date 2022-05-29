package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zr.test.demo.dao.CourseMapper;
import com.zr.test.demo.model.entity.CourseEntity;
import com.zr.test.demo.util.StringUtil;
import com.zr.test.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapperImpl {
    @Autowired
    private CourseMapper dao;
    public int insertOne(CourseEntity entity){
        return dao.insert(entity);
    }
    public CourseEntity selectById(Long id){
        return dao.selectById(id);
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
    public int addCount(Long id){
        return dao.addCount(id);
    }
    public int deleteById(Long id){
        return dao.deleteById(id);
    }

    public int deleteByIds(List<Long> id){
        if(id.isEmpty()){
            return 0;
        }
        return dao.deleteBatchIds(id);
    }

    public Page<CourseEntity> selectPageByTime(CourseEntity entity, String start, String end,
                                               String nameOrTeacher, Integer page, Integer pageSize) {
        QueryWrapper<CourseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        if(!StringUtil.isEmpty(start)&&!StringUtil.isEmpty(end)){
            queryWrapper.between("time", TimeUtil.getDate(start), TimeUtil.getDate(end));
        }
        if(!StringUtil.isEmpty(nameOrTeacher)){
            queryWrapper.like("name",nameOrTeacher).or().like("teacher",nameOrTeacher);
        }
        queryWrapper.orderByDesc("time");
        return PageHelper.startPage(page,pageSize).doSelectPage(()->dao.selectList(queryWrapper));
    }
}
