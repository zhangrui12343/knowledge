package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.CourseTypeDTO;
import com.zr.test.demo.model.entity.CourseTypeEntity;
import com.zr.test.demo.model.vo.CourseTypeVO;
import com.zr.test.demo.repository.CourseTypeMapperImpl;
import com.zr.test.demo.service.ICourseTypeService;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
public class CourseTypeServiceImpl implements ICourseTypeService {

    private final CourseTypeMapperImpl service;

    @Autowired
    public CourseTypeServiceImpl(CourseTypeMapperImpl service) {
        this.service = service;
    }

    @Override
    public Result<Object> add(CourseTypeDTO dto) {
        if(StringUtil.isEmpty(dto.getName())||dto.getPid()==null){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        CourseTypeEntity entity = new CourseTypeEntity();
        BeanUtils.copyProperties(dto, entity);
        return Result.success(service.insertOne(entity));
    }

    @Override
    public Result<List<CourseTypeVO>> query(HttpServletRequest request) {
        List<CourseTypeEntity> list = service.selectByEntity(null);
        List<CourseTypeVO> res = new ArrayList<>();
        list.forEach(l -> {
            CourseTypeVO v = new CourseTypeVO();
            BeanUtils.copyProperties(l, v);
            res.add(v);
        });
        return Result.success(res);
    }

    @Override
    public Result<Object> update(CourseTypeDTO dto, HttpServletRequest request) {
        if (dto.getId() == null||StringUtil.isEmpty(dto.getName())) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        CourseTypeEntity entity = new CourseTypeEntity();
        BeanUtils.copyProperties(dto, entity);
        return Result.success(service.updateById(entity));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        List<Long> pids = new ArrayList<>();
        pids.add(id);
        List<Long> ids = selectChildren(new ArrayList<>(), pids);
        ids.add(id);
         log.info("一共需要删除 {} 条", ids.size());
        return Result.success(service.deleteByIds(ids));
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
