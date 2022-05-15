package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.cache.LocalUtil;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.dao.TagMapper;
import com.zr.test.demo.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-15
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Autowired
    private LocalUtil localUtil;
    @Override
    public Result<Object> add(Tag dto) {
        if(this.save(dto)){
            Map<Long,String> map;
            if(localUtil.get("categoryAll")==null){
                localUtil.set("categoryAll",this.getBaseMapper().selectList(null).stream().collect(Collectors.toMap(Tag::getId,Tag::getName)));
            }else {
                map= (Map<Long,String>)localUtil.get("categoryAll");
                map.put(dto.getId(),dto.getName());
            }
        }
        return Result.success(null);
    }

    @Override
    public Result<List<Tag>> queryByDto(Tag dto) {
        return Result.success(this.getBaseMapper().selectList(null));
    }

    @Override
    public Result<Object> delete(Long id) {
        if(this.baseMapper.deleteById(id)==1){
            Map<Long,String> map;
            if(localUtil.get("categoryAll")==null){
                localUtil.set("categoryAll",this.getBaseMapper().selectList(null).stream().collect(Collectors.toMap(Tag::getId,Tag::getName)));
            }else {
                map= (Map<Long,String>)localUtil.get("categoryAll");
                map.remove(id);
            }
        }
        return Result.success(null);
    }
}
