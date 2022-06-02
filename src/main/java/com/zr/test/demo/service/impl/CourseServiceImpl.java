package com.zr.test.demo.service.impl;

import com.github.pagehelper.Page;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.dao.AppMapper;
import com.zr.test.demo.model.dto.CourseDTO;
import com.zr.test.demo.model.dto.CourseQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.entity.*;
import com.zr.test.demo.model.vo.*;
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
    private final AppMapper appMapper;


    @Autowired
    public CourseServiceImpl(CourseMapperImpl service, CourseCategoryMapperImpl categoryMapper,
                             CourseTypeMapperImpl typeMapper, FileRouterBiz fileRouterMapper, CourseTagRelationMapperImpl tagRelationMapper, AppMapper appMapper) {
        this.service = service;
        this.categoryMapper = categoryMapper;
        this.typeMapper = typeMapper;
        this.fileRouterMapper = fileRouterMapper;
        this.tagRelationMapper = tagRelationMapper;
        this.appMapper = appMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> add(CourseDTO dto, HttpServletRequest request) {
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setCount(0L);
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
        Page<CourseEntity> es = service.selectPageByTime(dto.getStartTime(), dto.getEndTime(), dto.getNameOrTeacher(),
                dto.getPage(), dto.getPageSize());
        if (es.getTotal() == 0) {
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setTotal(es.getTotal());
            return Result.success(pageInfo);
        }
        //查询类型所有
        Map<String, String> all = categoryMapper.selectByEntity(null).stream().
                collect(Collectors.toMap(e -> e.getId().toString(), CourseCategoryEntity::getName));
        //查询类型所有
        Map<String, String> allType = typeMapper.selectByEntity(null).stream().
                collect(Collectors.toMap(e -> e.getId().toString(), CourseTypeEntity::getName));
        List<CourseVO> vos = new ArrayList<>();

        es.getResult().forEach(e -> {
            CourseVO v = new CourseVO();
            v.setCategory(getNames(all, e.getCategory()));
            v.setTags(tagRelationMapper.selectTagNameByCourseId(e.getId()));
            v.setType(getNames(allType, e.getCourseTypeId()));
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
        String[] arr = category.split(",");
        List<String> categoryNames = new ArrayList<>();
        for (String str : arr) {
            String temp = all.get(str);
            if (StringUtil.isEmpty(temp)) {
                break;
            }
            categoryNames.add(temp);
        }
        return categoryNames;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> update(CourseDTO dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        CourseEntity old = service.selectById(dto.getId());
        if (!old.getImg().equals(dto.getImg())) {
            //删除图片
            fileRouterMapper.deleteOldFile(old.getImg());
        }
        if (!old.getHomework().equals(dto.getHomework())) {
            fileRouterMapper.deleteOldFile(old.getHomework());
        }
        if (!old.getLearningTask().equals(dto.getLearningTask())) {
            fileRouterMapper.deleteOldFile(old.getLearningTask());
        }
        if (!old.getVideo().equals(dto.getVideo())) {
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
        CourseEntity course = new CourseEntity();
        course.setId(dto.getId());
        course.setStatus(dto.getStatus());
        return Result.success(this.service.updateById(course));
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
        vo.setCategory(ListUtil.stringToList(entity.getCategory()));
        vo.setCourseTypeId(ListUtil.stringToList(entity.getCourseTypeId()));
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

    @Override
    public Result<WebCourseDetailVO> detail(Long id) {
        CourseEntity entity = service.selectById(id);
        if (entity == null) {
            return Result.success(null);
        }
        WebCourseDetailVO vo = new WebCourseDetailVO();
        BeanUtils.copyProperties(entity, vo);
        String[] arr = entity.getCategory().split(",");
        CourseCategoryEntity categoryEntity = categoryMapper.selectById(Long.parseLong(arr[arr.length - 1]));
        vo.setCategory(categoryEntity == null ? "" : categoryEntity.getName());
        vo.setCourseTag(tagRelationMapper.selectTagNameByCourseId(entity.getId()));
        String[] arr1 = entity.getCourseTypeId().split(",");
        CourseTypeEntity typeEntity = typeMapper.selectById(Long.parseLong(arr1[arr1.length - 1]));
        vo.setCourseType(typeEntity == null ? "" : typeEntity.getName());
        if (!StringUtil.isEmpty(entity.getApp())) {
            vo.setApp(appMapper.selectBatchIds(ListUtil.stringToList(entity.getApp())));
        }
        vo.setCount(entity.getCount() + 1);
        this.service.addCount(id);
        return Result.success(vo);


    }

    @Override
    public Result<PageInfo<CourseListVO>> list(CourseQueryDTO dto) {
        PageInfo<CourseListVO> pageInfo = new PageInfo<>();

        Page<CourseEntity> es = service.selectPageByType(ListUtil.listToString(dto.getCategories()), ListUtil.listToString(dto.getTypes()),
                dto.getPage(), dto.getPageSize());
        if (es.getTotal() == 0) {
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setTotal(es.getTotal());
            return Result.success(pageInfo);
        }
        //查询类型所有
        Map<String, String> all = categoryMapper.selectByEntity(null).stream().
                collect(Collectors.toMap(e -> e.getId().toString(), CourseCategoryEntity::getName));
        //查询类型所有
        Map<String, String> allType = typeMapper.selectByEntity(null).stream().
                collect(Collectors.toMap(e -> e.getId().toString(), CourseTypeEntity::getName));
        List<CourseListVO> vos = new ArrayList<>();

        es.getResult().forEach(e -> {
            CourseListVO v = new CourseListVO();
            List<String> temp = getNames(all, e.getCategory());
            if (!ListUtil.isEmpty(temp)) {
                v.setCategory(temp.get(temp.size() - 1));
            }
            v.setTags(tagRelationMapper.selectTagNameByCourseId(e.getId()));
            List<String> temp1 = getNames(allType, e.getCourseTypeId());
            if (!ListUtil.isEmpty(temp1)) {
                v.setType(temp1.get(temp1.size() - 1));
            }
            v.setTime(TimeUtil.getTime(e.getTime()));
            v.setExcellent(e.getExcellent());
            v.setName(e.getName());
            v.setTeacher(e.getTeacher());
            v.setId(e.getId());
            v.setCount(e.getCount());
            v.setImg(e.getImg());
            vos.add(v);
        });
        pageInfo.setPage(dto.getPage());
        pageInfo.setPageSize(dto.getPageSize());
        pageInfo.setTotal(es.getTotal());
        pageInfo.setList(vos);
        return Result.success(pageInfo);

    }

}
