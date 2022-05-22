package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.AfterCourseDTO;
import com.zr.test.demo.model.dto.AfterCourseQueryDTO;
import com.zr.test.demo.model.entity.AfterCourse;
import com.zr.test.demo.dao.AfterCourseMapper;
import com.zr.test.demo.model.entity.AfterCourseFirstRelation;
import com.zr.test.demo.model.entity.FirstCategory;
import com.zr.test.demo.model.vo.AfterCourseVO;
import com.zr.test.demo.model.vo.CourseVO;
import com.zr.test.demo.service.IAfterCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    private final AfterCourseFirstRelationServiceImpl typeService;

    public AfterCourseServiceImpl(AfterCourseFirstRelationServiceImpl typeService) {
        this.typeService = typeService;
    }

    @Override
    public Result<Object> add(AfterCourseDTO dto, HttpServletRequest request) {
        AfterCourse vo=new AfterCourse();
        BeanUtils.copyProperties(vo,dto);
        vo.setTime(new Date());
        vo.setVideos(ListUtil.listToString(dto.getVideos()));
        vo.setDocs(ListUtil.listToString(dto.getDocs()));
        int i=this.baseMapper.insert(vo);
        if(i==1){
            dto.getTypes().forEach(id-> typeService.getBaseMapper().insert(new AfterCourseFirstRelation(vo.getId(),id)));
        }
        return Result.success(i);
    }

    @Override
    public Result<PageInfo<AfterCourseVO>> query(AfterCourseQueryDTO dto, HttpServletRequest request) {

        List<Long> firstId=new ArrayList<>();
        if(dto.getType()!=null){
            QueryWrapper<FirstCategory> queryWrapper=new QueryWrapper<>();
            QueryWrapper<AfterCourseFirstRelation> queryWrapper2=new QueryWrapper<>();
            queryWrapper2.eq("first_id",dto.getType());
            List<AfterCourseFirstRelation> list= typeService.getBaseMapper().selectList(queryWrapper2);
            if(ListUtil.isEmpty(list)){
        //        return ;
            }
      //      list.
        }
    //    this.getBaseMapper().selectByCondition()
        return null;
    }

}
