package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.model.entity.FirstSecond;
import com.zr.test.demo.model.entity.FirstTag;
import com.zr.test.demo.dao.FirstTagMapper;
import com.zr.test.demo.service.IFirstTagService;
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
public class FirstTagServiceImpl extends ServiceImpl<FirstTagMapper, FirstTag> implements IFirstTagService {

    public int deleteByFirstId(Long id){
        FirstTag f=new FirstTag();
        f.setFirstId(id);
        return this.getBaseMapper().delete(new QueryWrapper<>(f));
    }
}
