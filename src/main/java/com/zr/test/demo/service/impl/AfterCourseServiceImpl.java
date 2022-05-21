package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.AfterCourseDTO;
import com.zr.test.demo.model.dto.AfterCourseQueryDTO;
import com.zr.test.demo.model.entity.AfterCourse;
import com.zr.test.demo.dao.AfterCourseMapper;
import com.zr.test.demo.model.vo.CourseVO;
import com.zr.test.demo.service.IAfterCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
public class AfterCourseServiceImpl extends ServiceImpl<AfterCourseMapper, AfterCourse> implements IAfterCourseService {

    @Override
    public Result<Object> add(AfterCourseDTO dto, HttpServletRequest request) {
        AfterCourse vo=new AfterCourse();
        BeanUtils.copyProperties(vo,dto);
        vo.setTime(new Date());

        vo.setTypes(ListUtil.listToString(dto.getTypes()));
        vo.setCategories(ListUtil.listToString(dto.getCategories()));
        vo.setTags(ListUtil.listToString(dto.getTags()));

        vo.setVideos(ListUtil.listToString(dto.getVideos()));
        vo.setDocs(ListUtil.listToString(dto.getDocs()));
        return Result.success(this.baseMapper.insert(vo));
    }

    @Override
    public Result<PageInfo<CourseVO>> query(AfterCourseQueryDTO dto, HttpServletRequest request) {
        QueryWrapper<AfterCourse> queryWrapper =new QueryWrapper<>();
        AfterCourse vo=new AfterCourse();
        if(!StringUtil.isEmpty(dto.getName())){
            queryWrapper.like("name",dto.getName());
        }
        queryWrapper.orderByDesc("time");



        return null;
    }

}
