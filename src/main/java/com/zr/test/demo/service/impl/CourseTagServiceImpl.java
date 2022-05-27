package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.CourseTypeDTO;
import com.zr.test.demo.model.entity.CourseTag;
import com.zr.test.demo.dao.CourseTagMapper;
import com.zr.test.demo.model.vo.CourseTypeVO;
import com.zr.test.demo.service.ICourseTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-20
 */
@Service
@Slf4j
public class CourseTagServiceImpl extends ServiceImpl<CourseTagMapper, CourseTag> implements ICourseTagService {

    @Override
    public Result<Object> add(CourseTypeDTO dto) {
        if(StringUtil.isEmpty(dto.getName())||dto.getPid()==null){
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        CourseTag entity = new CourseTag();
        BeanUtils.copyProperties(dto, entity);
        return Result.success(this.baseMapper.insert(entity));
    }

    @Override
    public Result<List<CourseTag>> findAll() {
        List<CourseTag> list = this.baseMapper.selectList(null);
        return Result.success(list);
    }

    @Override
    public Result<Object> update(CourseTypeDTO dto) {
        if (dto.getId() == null||StringUtil.isEmpty(dto.getName())) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        CourseTag entity = new CourseTag();
        BeanUtils.copyProperties(dto, entity);
        return Result.success(this.baseMapper.updateById(entity));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Object> delete(Long id) {
        List<Long> pids = new ArrayList<>();
        pids.add(id);
        List<Long> ids = selectChildren(new ArrayList<>(), pids);
        ids.add(id);
        log.info("一共需要删除 {} 条", ids.size());
        return Result.success(this.baseMapper.deleteBatchIds(ids));
    }

    public List<Long> selectChildren(List<Long> ids, List<Long> pids) {
        List<Long> list = this.baseMapper.selectIdsByPIds(pids);
        if (ListUtil.isEmpty(list)) {
            return ids;
        }
        ids.addAll(list);
        return selectChildren(ids, list);
    }
}
