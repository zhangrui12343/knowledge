package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.*;
import com.zr.test.demo.model.entity.*;
import com.zr.test.demo.dao.AfterCourseMapper;
import com.zr.test.demo.model.vo.*;
import com.zr.test.demo.service.IAfterCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.support.AfterCourseCategoryRelationBiz;
import com.zr.test.demo.support.AfterCourseTagRelationBiz;
import com.zr.test.demo.support.FileRouterBiz;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import com.zr.test.demo.util.TimeUtil;
import org.springframework.beans.BeanUtils;
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
 * @since 2022-05-20
 */
@Service
public class AfterCourseServiceImpl extends ServiceImpl<AfterCourseMapper, AfterCourse> implements IAfterCourseService {

    private final AfterCourseTagRelationServiceImpl afterCourseTagRelationService;
    private final AfterCourseCategoryRelationServiceImpl afterCourseCategoryRelationService;
    private final AfterCourseTagRelationBiz afterCourseTagRelationBiz;
    private final AfterCourseCategoryRelationBiz afterCourseCategoryRelationBiz;
    private final FirstCategoryServiceImpl firstCategoryService;
    private final FileRouterBiz fileRouterBiz;

    public AfterCourseServiceImpl(AfterCourseTagRelationServiceImpl afterCourseTagRelationService,
                                  AfterCourseCategoryRelationServiceImpl afterCourseCategoryRelationService,
                                  AfterCourseTagRelationBiz afterCourseTagRelationBiz,
                                  AfterCourseCategoryRelationBiz afterCourseCategoryRelationBiz,
                                  FirstCategoryServiceImpl firstCategoryService,
                                  FileRouterBiz fileRouterBiz) {
        this.afterCourseTagRelationService = afterCourseTagRelationService;
        this.afterCourseCategoryRelationService = afterCourseCategoryRelationService;
        this.afterCourseTagRelationBiz = afterCourseTagRelationBiz;
        this.afterCourseCategoryRelationBiz = afterCourseCategoryRelationBiz;
        this.firstCategoryService = firstCategoryService;
        this.fileRouterBiz = fileRouterBiz;
    }

    @Override
    public Result<Object> add(OtherCourseDTO dto, HttpServletRequest request) {
        AfterCourse vo = new AfterCourse();
        BeanUtils.copyProperties(dto, vo);
        vo.setCount(0L);
        vo.setTime(new Date());
        vo.setVideos(ListUtil.listToString(dto.getVideos()));
        vo.setDocs(ListUtil.listToString(dto.getDocs()));
        int i = this.baseMapper.insert(vo);
        if (i == 1) {
            dto.getTags().forEach(id -> afterCourseTagRelationService.getBaseMapper().insert(new AfterCourseTagRelation(vo.getId(), id)));
            dto.getCategories().forEach(id -> afterCourseCategoryRelationService.getBaseMapper().insert(new AfterCourseCategoryRelation(vo.getId(), id)));
        }
        return Result.success(i);
    }

    @Override
    public Result<PageInfo<OtherCourseVO>> query(OtherCourseQueryDTO dto, HttpServletRequest request) {
        QueryWrapper<AfterCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtil.isEmpty(dto.getName())) {
            queryWrapper.like("name", dto.getName());
        }
        if (!StringUtil.isEmpty(dto.getEndTime()) && !StringUtil.isEmpty(dto.getStartTime())) {
            queryWrapper.between("time", TimeUtil.getDate(dto.getStartTime()), TimeUtil.getDate(dto.getEndTime()));
        }
        if(dto.getStatus()!=null){
            queryWrapper.eq("status",dto.getStatus());
        }
        queryWrapper.orderByDesc("time");
        Page<AfterCourse> pages = PageHelper.startPage(dto.getPage(), dto.getPageSize()).
                doSelectPage(() -> this.baseMapper.selectList(queryWrapper));
        List<AfterCourse> list = pages.getResult();
        if (ListUtil.isEmpty(list)) {
            PageInfo<OtherCourseVO> pageInfo = new PageInfo<>();
            pageInfo.setTotal(0);
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            return Result.success(pageInfo);
        }
        long total = pages.getTotal();
        List<OtherCourseVO> res = new ArrayList<>();
        Map<String, String> types = firstCategoryService.getBaseMapper().selectList(null).
                stream().collect(Collectors.toMap(e -> e.getId().toString(), FirstCategory::getName));
        list.forEach(e -> {
            OtherCourseVO vo = new OtherCourseVO();
            vo.setId(e.getId());
            vo.setName(e.getName());
            vo.setStatus(e.getStatus());
            vo.setTime(TimeUtil.getTime(e.getTime()));
            vo.setType(Optional.ofNullable(types.get(e.getType().toString())).orElse(""));
            vo.setTag(afterCourseTagRelationBiz.selectTagNamesByCourseId(e.getId()));
            vo.setCategory(afterCourseCategoryRelationBiz.selectCategoryNamesByCourseId(e.getId()));
            res.add(vo);
        });
        PageInfo<OtherCourseVO> pageInfo = new PageInfo<>();
        pageInfo.setList(res);
        pageInfo.setTotal(total);
        pageInfo.setPage(dto.getPage());
        pageInfo.setPageSize(dto.getPageSize());
        return Result.success(pageInfo);
    }

    @Override
    public Result<OtherCourseOneVO> queryOne(Long id, HttpServletRequest request) {
        AfterCourse course = this.getBaseMapper().selectById(id);
        if (course == null) {
            return Result.success(null);
        }
        OtherCourseOneVO vo = new OtherCourseOneVO();
        BeanUtils.copyProperties(course, vo);
        vo.setImg(fileRouterBiz.selectFile(course.getImg()));
        vo.setTags(afterCourseTagRelationBiz.selectTagIdByCourseId(course.getId()));
        vo.setCategories(afterCourseCategoryRelationBiz.selectCategoryIdByCourseId(course.getId()));
        if(!StringUtil.isEmpty(course.getVideos())){
            String[] videoStr = course.getVideos().split(",");
            List<FileVO> videos = new ArrayList<>();
            for (String str : videoStr) {
                Long idd = Long.parseLong(str);
                FileVO file = fileRouterBiz.selectFile(idd);
                videos.add(file);
            }
            vo.setVideos(videos);
        }
        if(!StringUtil.isEmpty(course.getDocs())){
            String[] docStr = course.getDocs().split(",");
            List<FileVO> docs = new ArrayList<>();
            for (String str : docStr) {
                Long idd = Long.parseLong(str);
                FileVO file = fileRouterBiz.selectFile(idd);
                docs.add(file);
            }
            vo.setDocs(docs);
        }
        return Result.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> update(OtherCourseDTO dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        AfterCourse old = this.baseMapper.selectById(dto.getId());
        if (!old.getImg().equals(dto.getImg())) {
            //需要删除图片文件
            fileRouterBiz.deleteOldFile(old.getImg());
        }
        String docs = ListUtil.listToString(dto.getDocs());
        if (!old.getDocs().equals(docs)) {
            //需要删除文档文件
            fileRouterBiz.delete(old.getDocs());
        }
        String videos = ListUtil.listToString(dto.getVideos());
        if (!old.getDocs().equals(videos)) {
            //需要删除视频文件
            fileRouterBiz.delete(old.getVideos());
        }
        AfterCourse entity = new AfterCourse();
        BeanUtils.copyProperties(dto, entity);
        entity.setDocs(docs);
        entity.setVideos(videos);
        QueryWrapper<AfterCourseTagRelation> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("after_course_id", dto.getId());
        afterCourseTagRelationService.getBaseMapper().delete(queryWrapper1);
        dto.getTags().forEach(c -> afterCourseTagRelationService.getBaseMapper().insert(new AfterCourseTagRelation(dto.getId(), c)));
        QueryWrapper<AfterCourseCategoryRelation> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("after_course_id", dto.getId());
        afterCourseCategoryRelationService.getBaseMapper().delete(queryWrapper2);
        dto.getCategories().forEach(c -> afterCourseCategoryRelationService.getBaseMapper().insert(new AfterCourseCategoryRelation(dto.getId(), c)));
        return Result.success(this.getBaseMapper().updateById(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        AfterCourse e = this.getBaseMapper().selectById(id);
        if (e == null) {
            return Result.success(0);
        }
        QueryWrapper<AfterCourseTagRelation> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("after_course_id", id);
        afterCourseTagRelationService.getBaseMapper().delete(queryWrapper1);
        QueryWrapper<AfterCourseCategoryRelation> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("after_course_id", id);
        afterCourseCategoryRelationService.getBaseMapper().delete(queryWrapper2);
        fileRouterBiz.deleteOldFile(e.getImg());
        if (e.getVideos() != null && e.getVideos().length() > 0) {
            String[] strs = e.getVideos().split(",");
            for (String idStr : strs) {
                fileRouterBiz.deleteOldFile(Long.parseLong(idStr));
            }
        }
        if (e.getDocs() != null && e.getDocs().length() > 0) {
            String[] strs = e.getDocs().split(",");
            for (String idStr : strs) {
                fileRouterBiz.deleteOldFile(Long.parseLong(idStr));
            }
        }
        return Result.success(this.getBaseMapper().deleteById(id));
    }

    @Override
    public Result<Object> updateStatus(StatusDTO dto, HttpServletRequest request) {
        AfterCourse course = new AfterCourse();
        course.setId(dto.getId());
        course.setStatus(dto.getStatus());
        return Result.success(this.getBaseMapper().updateById(course));
    }

    @Override
    public Result<List<OtherCourseListVO>> findList() {
        QueryWrapper<FirstCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 0);
        queryWrapper.orderByDesc("`order`");
        List<FirstCategory> list =this.firstCategoryService.getBaseMapper().selectList(queryWrapper);
        if (ListUtil.isEmpty(list)) {
            return Result.success(new ArrayList<>());
        }
        List<OtherCourseListVO> res = new ArrayList<>();
        list.forEach(firstCategory -> {
            OtherCourseListVO vo = new OtherCourseListVO();
            vo.setCategoryId(firstCategory.getId());
            vo.setCategoryImg(firstCategory.getImg());
            vo.setCategoryName(firstCategory.getName());
            vo.setDos(this.getBaseMapper().selectLimit3(firstCategory.getId()));
            res.add(vo);
        });
        return Result.success(res);
    }

    @Override
    public Result<PageInfo<OtherCourseWebVO>> listMore(OtherCourseListDTO dto) {
        List<OtherCourseWebVO> list=this.getBaseMapper().selectMore(dto.getType(),dto.getCategory(),dto.getTag());
        list=list.stream().distinct().collect(Collectors.toList());
        int total=list.size();
        if(total==0){
            PageInfo<OtherCourseWebVO> pageInfo = new PageInfo<>();
            pageInfo.setTotal(0);
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setPage(dto.getPage());
            return Result.success(pageInfo);
        }
        List<OtherCourseWebVO> list2=ListUtil.page(list,dto.getPage(),dto.getPageSize());
        PageInfo<OtherCourseWebVO> pageInfo = new PageInfo<>();
        pageInfo.setTotal(total);
        pageInfo.setPageSize(dto.getPageSize());
        pageInfo.setPage(dto.getPage());
        pageInfo.setList(list2);
        return Result.success(pageInfo);
    }

    @Override
    public Result<WebAfterDetailVO> detail(Long id) {
        AfterCourse entity = this.baseMapper.selectById(id);
        if (entity == null) {
            return Result.success(null);
        }
        WebAfterDetailVO vo = new WebAfterDetailVO();
        BeanUtils.copyProperties(entity, vo);
        vo.setCategories(afterCourseCategoryRelationBiz.selectNamesByCourseId(entity.getId()));
        FirstCategory f=firstCategoryService.getBaseMapper().selectById(entity.getType());
        vo.setType(f==null?"":f.getName());
        vo.setTags(afterCourseTagRelationBiz.selectNamesByCourseId(entity.getId()));
        if(!StringUtil.isEmpty(entity.getVideos())){
           List<Long> videoIds= ListUtil.stringToList(entity.getVideos());
           List<FileVO> videoList=new ArrayList<>();
            videoIds.forEach(videoId-> videoList.add(fileRouterBiz.selectFile(id)));
            vo.setVideoList(videoList);
        }
        if(!StringUtil.isEmpty(entity.getDocs())){
            List<Long> videoIds= ListUtil.stringToList(entity.getDocs());
            List<FileVO> videoList=new ArrayList<>();
            videoIds.forEach(videoId-> videoList.add(fileRouterBiz.selectFile(id)));
            vo.setDocList(videoList);
        }
        vo.setCount(entity.getCount()+1);
        this.baseMapper.addCount(id);
        return Result.success(vo);
      }
}
