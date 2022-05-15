package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.CourseCategoryDTO;
import com.zr.test.demo.model.entity.CourseCategoryEntity;
import com.zr.test.demo.model.vo.CourseCategoryVO;
import com.zr.test.demo.repository.CourseCategoryMapperImpl;
import com.zr.test.demo.service.ICourseCategoryService;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.config.cache.LocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
@Service
@Slf4j
public class CourseCategoryServiceImpl implements ICourseCategoryService {

    private final CourseCategoryMapperImpl service;
    private final LocalUtil localUtil;
    @Autowired
    public CourseCategoryServiceImpl(CourseCategoryMapperImpl service, LocalUtil localUtil) {
        this.service = service;
        this.localUtil = localUtil;
    }

    @Override
    public Result<Object> add(CourseCategoryDTO dto) {
        CourseCategoryEntity entity = new CourseCategoryEntity();
        BeanUtils.copyProperties(dto, entity);
        int i=service.insertOne(entity);
        Map<Long,String> all;
        if(i==0){
            return Result.success(i);
        }
        if(localUtil.get("course_category")==null){
            all=service.selectByEntity(null).stream().collect(Collectors.toMap(CourseCategoryEntity::getId,CourseCategoryEntity::getName));
            localUtil.set("course_category",all);
        }else {
            all= (Map<Long, String>) localUtil.get("course_category");
            all.put(entity.getId(),entity.getName());
        }
        return Result.success(i);
    }

    @Override
    public Result<List<CourseCategoryVO>> query(HttpServletRequest request) {
        List<CourseCategoryEntity> list = service.selectByEntity(null);
        List<CourseCategoryVO> res = new ArrayList<>();
        list.forEach(l -> {
            CourseCategoryVO v = new CourseCategoryVO();
            BeanUtils.copyProperties(l, v);
            res.add(v);
        });
        return Result.success(res);
    }

    @Override
    public Result<Object> update(CourseCategoryDTO dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        CourseCategoryEntity entity = new CourseCategoryEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        int i=service.updateById(entity);
        Map<Long,String> all;
        if(i==0){
            return Result.success(i);
        }
        if(localUtil.get("course_category")==null){
            all=service.selectByEntity(null).stream().collect(Collectors.toMap(CourseCategoryEntity::getId,CourseCategoryEntity::getName));
            localUtil.set("course_category",all);
        }else {
            all= (Map<Long, String>) localUtil.get("course_category");
            all.put(entity.getId(),entity.getName());
        }
        return Result.success(i);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        List<Long> pids = new ArrayList<>();
        pids.add(id);
        List<Long> ids = selectChildren(new ArrayList<>(), pids);
        log.info("一共需要删除 {} 条", ids.size());
        int i=service.deleteByIds(ids);
        Map<Long,String> all;
        if(i==0){
            return Result.success(i);
        }
        if(localUtil.get("course_category")==null){
            all=service.selectByEntity(null).stream().collect(Collectors.toMap(CourseCategoryEntity::getId,CourseCategoryEntity::getName));
            localUtil.set("course_category",all);
        }else {
            all= (Map<Long, String>) localUtil.get("course_category");
            ids.forEach(all::remove);
        }
        return Result.success(i);
    }

    public List<Long> selectChildren(List<Long> ids, List<Long> pids) {
        List<Long> list = service.selectIdsByPIds(pids);
        if (ListUtil.isEmpty(list)) {
            return ids;
        }
        ids.addAll(list);
        return selectChildren(ids, list);
    }

}
