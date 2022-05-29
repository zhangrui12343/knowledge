package com.zr.test.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.CourseDTO;
import com.zr.test.demo.model.dto.CourseQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.entity.*;
import com.zr.test.demo.model.vo.CourseOneVO;
import com.zr.test.demo.model.vo.CourseVO;
import com.zr.test.demo.repository.*;
import com.zr.test.demo.service.ICourseService;
import com.zr.test.demo.support.FileRouterBiz;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import com.zr.test.demo.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
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
public class CourseServiceImpl implements ICourseService {

    private final CourseMapperImpl service;
    private final CourseCategoryMapperImpl categoryMapper;
    private final CourseTypeMapperImpl typeMapper;
    private final FileRouterBiz fileRouterMapper;
    private final CourseTagRelationMapperImpl tagRelationMapper;


    @Autowired
    public CourseServiceImpl(CourseMapperImpl service, CourseCategoryMapperImpl categoryMapper,
                             CourseTypeMapperImpl typeMapper, FileRouterBiz fileRouterMapper, CourseTagRelationMapperImpl tagRelationMapper) {
        this.service = service;
        this.categoryMapper = categoryMapper;
        this.typeMapper = typeMapper;
        this.fileRouterMapper = fileRouterMapper;
        this.tagRelationMapper = tagRelationMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> add(CourseDTO dto, HttpServletRequest request) {
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setCount(0);
        entity.setCategory(ListUtil.listToString(dto.getCategory()));
        entity.setCourseTypeId(ListUtil.listToString(dto.getCourseTypeId()));
        entity.setApp(ListUtil.listToString(dto.getApps()));
        entity.setTime(new Date());
        int i = service.insertOne(entity);
        if (i == 1) {
            tagRelationMapper.insert(entity.getId(), dto.getCourseTagIds());
        }
        return Result.success(i);
    }


    @Override
    public Result<PageInfo<CourseVO>> query(CourseQueryDTO dto, HttpServletRequest request) {
        PageInfo<CourseVO> pageInfo = new PageInfo<>();
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        Page<CourseEntity> es = service.selectPageByTime(entity, dto.getStartTime(), dto.getEndTime(), dto.getNameOrTeacher(),
                dto.getPage(), dto.getPageSize());
        if (es.getTotal() == 0) {
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setTotal(es.getTotal());
            return Result.success(pageInfo);
        }
        //查询类型所有
        Map<String, String> all = categoryMapper.selectByEntity(null).stream().
                collect(Collectors.toMap(e->e.getId().toString(), CourseCategoryEntity::getName));
        //查询类型所有
        Map<String, String> allType = typeMapper.selectByEntity(null).stream().
                collect(Collectors.toMap(e->e.getId().toString(), CourseTypeEntity::getName));
        List<CourseVO> vos = new ArrayList<>();

        es.getResult().forEach(e -> {
            CourseVO v = new CourseVO();
            v.setCategory(getNames(all,e.getCategory()));
            v.setTags(tagRelationMapper.selectTagNameByCourseId(e.getId()));
            v.setType(getNames(allType,e.getCourseTypeId()));
            v.setTime(TimeUtil.getTime(e.getTime()));
            v.setStatus(e.getStatus());
            v.setName(e.getName());
            v.setTeacher(e.getTeacher());
            v.setId(e.getId());
            vos.add(v);
        });
        pageInfo.setPage(dto.getPage());
        pageInfo.setPageSize(dto.getPageSize());
        pageInfo.setTotal(es.getTotal());
        pageInfo.setList(vos);
        return Result.success(pageInfo);
    }

    private List<String> getNames(Map<String, String> all, String category) {
        String[] arr=category.split(",");
        List<String> categoryNames=new ArrayList<>();
        for(String str:arr){
            String temp=all.get(str);
            if(StringUtil.isEmpty(temp)){
                break;
            }
            categoryNames.add(temp);
        }
        return  categoryNames;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> update(CourseDTO dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        CourseEntity old=service.selectById(dto.getId());
        if(!old.getImg().equals(dto.getImg())){
            //删除图片
            fileRouterMapper.deleteOldFile(old.getImg());
        }
        if(!old.getHomework().equals(dto.getHomework())){
            fileRouterMapper.deleteOldFile(old.getHomework());
        }
        if(!old.getLearningTask().equals(dto.getLearningTask())){
            fileRouterMapper.deleteOldFile(old.getLearningTask());
        }
        if(!old.getVideo().equals(dto.getVideo())){
            fileRouterMapper.deleteOldFile(old.getVideo());
        }
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setCategory(ListUtil.listToString(dto.getCategory()));
        entity.setCourseTypeId(ListUtil.listToString(dto.getCourseTypeId()));
        tagRelationMapper.deleteByCourseId(dto.getId());
        tagRelationMapper.insert(dto.getId(), dto.getCourseTagIds());
        entity.setApp(ListUtil.listToString(dto.getApps()));
        entity.setTime(new Date());
        return Result.success(service.updateById(entity));
    }

    @Override
    public Result<Object> updateStatus(StatusDTO dto, HttpServletRequest request) {
        CourseEntity course=new CourseEntity();
        course.setId(dto.getId());
        course.setStatus(dto.getStatus());
        return Result.success(this.service.updateById(course));
    }
    @Override
    public Result<Object> addCount(Long id) {
        return Result.success(this.service.addCount(id));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        CourseEntity e = service.selectById(id);
        if (e == null) {
            return Result.success(0);
        }
        tagRelationMapper.deleteByCourseId(e.getId());
        fileRouterMapper.deleteOldFile(e.getImg());
        fileRouterMapper.deleteOldFile(e.getVideo());
        fileRouterMapper.deleteOldFile(e.getLearningTask());
        fileRouterMapper.deleteOldFile(e.getHomework());
        return Result.success(service.deleteById(id));
    }
    @Override
    public Result<CourseOneVO> queryOne(Long id, HttpServletRequest request) {
        CourseEntity entity = service.selectById(id);
        if (entity == null) {
            return Result.success(null);
        }
        CourseOneVO vo = new CourseOneVO();
        BeanUtils.copyProperties(entity, vo);
        vo.setCategory(Long.parseLong(entity.getCategory().substring(entity.getCategory().lastIndexOf(",")+1)));
        vo.setCourseTypeId(Long.parseLong(entity.getCourseTypeId().substring(entity.getCourseTypeId().lastIndexOf(",")+1)));
        vo.setImg(FileUtil.getBase64FilePath(fileRouterMapper.selectPath(entity.getImg())));
        vo.setImgId(entity.getImg());
        vo.setVideo(FileUtil.getBase64FilePath(fileRouterMapper.selectPath(entity.getVideo())));
        vo.setVideoId(entity.getVideo());
        vo.setLearningTask(FileUtil.getBase64FilePath(fileRouterMapper.selectPath(entity.getLearningTask())));
        vo.setLearningTaskId(entity.getLearningTask());
        vo.setHomework(FileUtil.getBase64FilePath(fileRouterMapper.selectPath(entity.getHomework())));
        vo.setHomeworkId(entity.getHomework());
        vo.setApp(ListUtil.stringToList(entity.getApp()));
        vo.setCourseTag(tagRelationMapper.selectByCourseId(entity.getId()));
        return Result.success(vo);
    }

}
