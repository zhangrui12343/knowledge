package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.config.cache.LocalUtil;
import com.zr.test.demo.model.entity.SecondCategory;
import com.zr.test.demo.dao.SecondCategoryMapper;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.service.ISecondCategoryService;
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
public class SecondCategoryServiceImpl extends ServiceImpl<SecondCategoryMapper, SecondCategory> implements ISecondCategoryService {
    @Autowired
    private LocalUtil localUtil;
    @Override
    public Result<Object> add(SecondCategory dto) {
        if(this.save(dto)){
            Map<Long,String> map;
            if(localUtil.get("tagAll")==null){
                localUtil.set("tagAll",this.getBaseMapper().selectList(null).stream().collect(Collectors.toMap(SecondCategory::getId,SecondCategory::getName)));
            }else {
                map= (Map<Long,String>)localUtil.get("tagAll");
                map.put(dto.getId(),dto.getName());
            }
        }
        return Result.success(null);
    }

    @Override
    public Result<List<SecondCategory>> queryByDto(SecondCategory dto) {
        return Result.success(this.getBaseMapper().selectList(null));
    }

    @Override
    public Result<Object> delete(Long id) {
        if(this.baseMapper.deleteById(id)==1){
            Map<Long,String> map;
            if(localUtil.get("tagAll")==null){
                localUtil.set("tagAll",this.getBaseMapper().selectList(null).stream().collect(Collectors.toMap(SecondCategory::getId,SecondCategory::getName)));
            }else {
                map= (Map<Long,String>)localUtil.get("tagAll");
                map.remove(id);
            }
        }
        return Result.success(null);
    }
}
