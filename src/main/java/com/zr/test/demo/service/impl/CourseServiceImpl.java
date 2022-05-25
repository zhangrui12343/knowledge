package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.dao.CourseTagRelationMapper;
import com.zr.test.demo.dao.CourseTypeRelationMapper;
import com.zr.test.demo.model.dto.CourseDTO;
import com.zr.test.demo.model.dto.CourseQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.entity.*;
import com.zr.test.demo.model.vo.CourseOneVO;
import com.zr.test.demo.model.vo.CourseVO;
import com.zr.test.demo.repository.*;
import com.zr.test.demo.service.ICourseService;
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
    private final FileRouterMapperImpl fileRouterMapper;
    private final CourseTagRelationMapperImpl tagRelationMapper;
    private final CourseTypeRelationMapperImpl typeRelationMapper;


    @Autowired
    public CourseServiceImpl(CourseMapperImpl service, CourseCategoryMapperImpl categoryMapper,
                             FileRouterMapperImpl fileRouterMapper, CourseTagRelationMapperImpl tagRelationMapper,
                             CourseTypeRelationMapperImpl typeRelationMapper) {
        this.service = service;
        this.categoryMapper = categoryMapper;
        this.fileRouterMapper = fileRouterMapper;
        this.tagRelationMapper = tagRelationMapper;
        this.typeRelationMapper = typeRelationMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> add(CourseDTO dto, HttpServletRequest request) {
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setApp(ListUtil.listToString(dto.getApps()));
        entity.setTime(new Date());
        int i = service.insertOne(entity);
        if (i == 1) {
            tagRelationMapper.insert(entity.getId(), dto.getCourseTagIds());
            typeRelationMapper.insert(entity.getId(), dto.getCourseTypeIds());
        }
        return Result.success(i);
    }


    @Override
    public Result<PageInfo<CourseVO>> query(CourseQueryDTO dto, HttpServletRequest request) {
        PageInfo<CourseVO> pageInfo = new PageInfo<>();
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        IPage<CourseEntity> es = service.selectPageByTime(entity, dto.getStartTime(), dto.getEndTime(), dto.getNameOrTeacher(),
                dto.getPage(), dto.getPageSize());
        if (es.getTotal() == 0) {
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setTotal(es.getTotal());
            return Result.success(pageInfo);
        }
        Map<Long, String> all = categoryMapper.selectByEntity(null).stream().
                collect(Collectors.toMap(CourseCategoryEntity::getId, CourseCategoryEntity::getName));
        List<CourseVO> vos = new ArrayList<>();
        es.getRecords().forEach(e -> {
            CourseVO v = new CourseVO();
            v.setTime(TimeUtil.getTime(e.getTime()));
            v.setSubject(Optional.ofNullable(all.get(e.getSubject())).orElse(""));
            v.setBooks(Optional.ofNullable(all.get(e.getBooks())).orElse(""));
            v.setStatus(e.getStatus());
            v.setGrade(Optional.ofNullable(all.get(e.getGrade())).orElse(""));
            v.setXueduan(Optional.ofNullable(all.get(e.getXueduan())).orElse(""));
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> update(CourseDTO dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        CourseEntity old=service.selectById(dto.getId());
        if(!old.getImg().equals(dto.getImg())){
            //删除图片
            fileRouterMapper.deleteById(old.getImg());
        }
        if(!old.getHomework().equals(dto.getHomework())){
            fileRouterMapper.deleteById(old.getHomework());
        }
        if(!old.getLearningTask().equals(dto.getLearningTask())){
            fileRouterMapper.deleteById(old.getLearningTask());
        }
        if(!old.getVideo().equals(dto.getVideo())){
            fileRouterMapper.deleteById(old.getVideo());
        }
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        typeRelationMapper.deleteByCourseId(dto.getId());
        typeRelationMapper.insert(dto.getId(), dto.getCourseTypeIds());
        tagRelationMapper.deleteByCourseId(dto.getId());
        tagRelationMapper.insert(dto.getId(), dto.getCourseTagIds());
        entity.setApp(dto.getApps() == null ? "" : ListUtil.listToString(dto.getApps()));
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
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        CourseEntity e = service.selectById(id);
        if (e == null) {
            return Result.success(0);
        }
        typeRelationMapper.deleteByCourseId(e.getId());
        tagRelationMapper.deleteByCourseId(e.getId());
        deleteOldFile(e.getImg());
        deleteOldFile(e.getVideo());
        deleteOldFile(e.getLearningTask());
        deleteOldFile(e.getHomework());
        return Result.success(service.deleteById(id));
    }

    private void deleteOldFile(Long id) {
        if (id == null) {
            return;
        }
        String path = fileRouterMapper.getPathById(id);
        if (!StringUtil.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                if (file.delete()) {
                    log.info("文件删除成功,删除数据库记录,path={},id={}", path, id);
                    this.fileRouterMapper.deleteById(id);
                } else {
                    log.error("文件删除失败,path={},id={}", path, id);
                }
            }
        }
    }

    @Override
    public Result<CourseOneVO> queryOne(Long id, HttpServletRequest request) {
        CourseEntity entity = service.selectById(id);
        if (entity == null) {
            return Result.success(null);
        }
        CourseOneVO vo = new CourseOneVO();
        BeanUtils.copyProperties(entity, vo);
        vo.setImg(FileUtil.getBase64FilePath(fileRouterMapper.getPathById(entity.getImg())));
        vo.setImgId(entity.getImg());
        vo.setVideo(FileUtil.getBase64FilePath(fileRouterMapper.getPathById(entity.getVideo())));
        vo.setVideoId(entity.getVideo());
        vo.setLearningTask(FileUtil.getBase64FilePath(fileRouterMapper.getPathById(entity.getLearningTask())));
        vo.setLearningTaskId(entity.getLearningTask());
        vo.setHomework(FileUtil.getBase64FilePath(fileRouterMapper.getPathById(entity.getHomework())));
        vo.setHomeworkId(entity.getHomework());
        vo.setApp(ListUtil.stringToList(entity.getApp()));
        vo.setCourseTag(typeRelationMapper.selectByCourseId(entity.getId()));
        vo.setCourseType(tagRelationMapper.selectByCourseId(entity.getId()));
        return Result.success(vo);
    }

}
