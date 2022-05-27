package com.zr.test.demo.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.dao.CourseCategoryMapper;
import com.zr.test.demo.dao.IMenuDao;
import com.zr.test.demo.model.entity.CourseCategoryEntity;
import com.zr.test.demo.model.entity.MenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseCategoryMapperImpl {
    @Autowired
    private  CourseCategoryMapper dao;
    public int insertOne(CourseCategoryEntity entity){
        return dao.insert(entity);
    }

    public List<CourseCategoryEntity> selectByEntity(CourseCategoryEntity entity){
        if(entity==null){
            return dao.selectList(null);
        }
        QueryWrapper<CourseCategoryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(entity);
        queryWrapper.orderByAsc("id");
        return dao.selectList(queryWrapper);
    }
    public List<CourseCategoryEntity> selectByIds(List<Long> ids){
        return dao.selectBatchIds(ids);
    }

    public int updateById(CourseCategoryEntity role){
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
