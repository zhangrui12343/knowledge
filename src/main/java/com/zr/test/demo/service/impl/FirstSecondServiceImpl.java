package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.model.entity.FirstSecond;
import com.zr.test.demo.dao.FirstSecondMapper;
import com.zr.test.demo.service.IFirstSecondService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-22
 */
@Service
public class FirstSecondServiceImpl extends ServiceImpl<FirstSecondMapper, FirstSecond> implements IFirstSecondService {

    public int deleteByFirstId(Long id){
        FirstSecond f=new FirstSecond();
        f.setFirstId(id);
        return this.getBaseMapper().delete(new QueryWrapper<>(f));
    }
}
