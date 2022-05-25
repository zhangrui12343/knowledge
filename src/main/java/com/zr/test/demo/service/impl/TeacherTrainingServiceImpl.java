package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.OtherCourseDTO;
import com.zr.test.demo.model.dto.OtherCourseQueryDTO;
import com.zr.test.demo.model.dto.StatusDTO;
import com.zr.test.demo.model.entity.*;
import com.zr.test.demo.dao.TeacherTrainingMapper;
import com.zr.test.demo.model.vo.OtherCourseOneVO;
import com.zr.test.demo.model.vo.OtherCourseVO;
import com.zr.test.demo.service.ITeacherTrainingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.FileUtil;
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
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-24
 */
@Service
public class TeacherTrainingServiceImpl extends ServiceImpl<TeacherTrainingMapper, TeacherTraining> implements ITeacherTrainingService {

    private final AfterCourseTagRelationServiceImpl afterCourseTagRelationService;
    private final AfterCourseCategoryRelationServiceImpl afterCourseCategoryRelationService;
    private final FirstCategoryServiceImpl firstCategoryService;
    private final FileRouterServiceImpl fileRouterService;

    public TeacherTrainingServiceImpl(AfterCourseTagRelationServiceImpl afterCourseTagRelationService,
                            AfterCourseCategoryRelationServiceImpl afterCourseCategoryRelationService,
                            FirstCategoryServiceImpl firstCategoryService, FileRouterServiceImpl fileRouterService) {
        this.afterCourseTagRelationService = afterCourseTagRelationService;
        this.afterCourseCategoryRelationService = afterCourseCategoryRelationService;
        this.firstCategoryService = firstCategoryService;
        this.fileRouterService = fileRouterService;
    }

    @Override
    public Result<Object> add(OtherCourseDTO dto, HttpServletRequest request) {
        TeacherTraining vo = new TeacherTraining();
        BeanUtils.copyProperties(vo, dto);
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
        QueryWrapper<TeacherTraining> queryWrapper = new QueryWrapper<>();
        if (!StringUtil.isEmpty(dto.getName())) {
            queryWrapper.like("name", dto.getName());
        }
        if (dto.getType() != null) {
            queryWrapper.eq("type", dto.getType());
        }
        queryWrapper.orderByDesc("time");
        List<TeacherTraining> list = this.baseMapper.selectList(queryWrapper);
        if(ListUtil.isEmpty(list)){
            PageInfo<OtherCourseVO> pageInfo=new PageInfo<>();
            pageInfo.setTotal(0);
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            return Result.success(pageInfo);
        }
        if (dto.getCategory() != null) {
            QueryWrapper<AfterCourseCategoryRelation> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("category_id", dto.getCategory());
            List<AfterCourseCategoryRelation> categoryRelations = afterCourseCategoryRelationService.getBaseMapper().selectList(queryWrapper1);
            if (!ListUtil.isEmpty(categoryRelations)) {
                List<Long> courseIds = categoryRelations.stream().map(AfterCourseCategoryRelation::getAfterCourseId).collect(Collectors.toList());
                list = list.stream().filter(e -> courseIds.contains(e.getId())).collect(Collectors.toList());
            }
        }
        if (dto.getTag() != null) {
            QueryWrapper<AfterCourseTagRelation> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("tag_id", dto.getTag());
            List<AfterCourseTagRelation> tagRelations = afterCourseTagRelationService.getBaseMapper().selectList(queryWrapper1);
            if (!ListUtil.isEmpty(tagRelations)) {
                List<Long> courseIds = tagRelations.stream().map(AfterCourseTagRelation::getAfterCourseId).collect(Collectors.toList());
                list = list.stream().filter(e -> courseIds.contains(e.getId())).collect(Collectors.toList());
            }
        }
        int total=list.size();
        if(total==0){
            PageInfo<OtherCourseVO> pageInfo=new PageInfo<>();
            pageInfo.setTotal(0);
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            return Result.success(pageInfo);
        }
        list=ListUtil.page(list,dto.getPage(),dto.getPageSize());
        List<OtherCourseVO> res=new ArrayList<>();
        Map<String,String> types=firstCategoryService.getBaseMapper().selectList(null).stream().collect(Collectors.toMap(e->""+e.getId(),FirstCategory::getName));
        list.forEach(e->{
            OtherCourseVO vo=new OtherCourseVO();
            vo.setId(e.getId());
            vo.setName(e.getName());
            vo.setStatus(e.getStatus());
            vo.setTime(TimeUtil.getTime(e.getTime()));
            vo.setType(Optional.ofNullable(types.get(""+e.getType())).orElse(""));
            vo.setTag(afterCourseTagRelationService.selectTagNamesByCourseId(e.getId()));
            vo.setCategory(afterCourseCategoryRelationService.selectCategoryNamesByCourseId(e.getId()));
            res.add(vo);
        });
        PageInfo<OtherCourseVO> pageInfo=new PageInfo<>();
        pageInfo.setList(res);
        pageInfo.setTotal(total);
        pageInfo.setPage(dto.getPage());
        pageInfo.setPageSize(dto.getPageSize());
        return Result.success(pageInfo);
    }

    @Override
    public Result<OtherCourseOneVO> queryOne(Long id, HttpServletRequest request) {
        TeacherTraining course=this.getBaseMapper().selectById(id);
        OtherCourseOneVO vo=new OtherCourseOneVO();
        BeanUtils.copyProperties(course,vo);

        vo.setCategories(afterCourseCategoryRelationService.selectCategoryIdByCourseId(course.getId()));
        vo.setTags(afterCourseTagRelationService.selectTagIdByCourseId(course.getId()));
        List<String> videos=new ArrayList<>();
        String[] videoStr=course.getVideos().split(",");
        List<Long> videoIds=new ArrayList<>();
        for(String str:videoStr){
            Long idd=Long.parseLong(str);
            FileRouter file=fileRouterService.getBaseMapper().selectById(idd);
            if(file!=null){
                videos.add(FileUtil.getBase64FilePath(file.getFilePath()));
                videoIds.add(idd);
            }
        }
        vo.setVideos(videos);
        vo.setVideoIds(videoIds);
        String[] docStr=course.getDocs().split(",");
        List<String> docs=new ArrayList<>();
        List<Long> docIds=new ArrayList<>();
        for(String str:docStr){
            Long idd=Long.parseLong(str);
            FileRouter file=fileRouterService.getBaseMapper().selectById(idd);
            if(file!=null){
                docs.add(FileUtil.getBase64FilePath(file.getFilePath()));
                docIds.add(idd);
            }
        }
        vo.setDocs(docs);
        vo.setDocsIds(docIds);
        return Result.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> update(OtherCourseDTO dto, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        TeacherTraining old=this.baseMapper.selectById(dto.getId());
        if(!old.getImg().equals(dto.getImg())){
            //需要删除图片文件
            fileRouterService.deleteOldFile(old.getImg());
        }
        String docs=ListUtil.listToString(dto.getDocs());
        if(!old.getDocs().equals(docs)){
            //需要删除文档文件
            fileRouterService.delete(old.getDocs());
        }
        String videos=ListUtil.listToString(dto.getVideos());
        if(!old.getDocs().equals(videos)){
            //需要删除视频文件
            fileRouterService.delete(old.getVideos());
        }
        TeacherTraining entity=new TeacherTraining();
        BeanUtils.copyProperties(dto,entity);
        entity.setDocs(ListUtil.listToString(dto.getDocs()));
        entity.setVideos(ListUtil.listToString(dto.getVideos()));
        QueryWrapper<AfterCourseCategoryRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",dto.getId());
        afterCourseCategoryRelationService.getBaseMapper().delete(queryWrapper);
        dto.getCategories().forEach(c-> afterCourseCategoryRelationService.getBaseMapper().insert(new AfterCourseCategoryRelation(dto.getId(),c)));
        QueryWrapper<AfterCourseTagRelation> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("course_id",dto.getId());
        afterCourseTagRelationService.getBaseMapper().delete(queryWrapper1);
        dto.getTags().forEach(c-> afterCourseTagRelationService.getBaseMapper().insert(new AfterCourseTagRelation(dto.getId(),c)));
        return Result.success(this.getBaseMapper().updateById(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> delete(Long id, HttpServletRequest request) {
        TeacherTraining e = this.getBaseMapper().selectById(id);
        if (e == null) {
            return Result.success(0);
        }
        QueryWrapper<AfterCourseCategoryRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        afterCourseCategoryRelationService.getBaseMapper().delete(queryWrapper);
        QueryWrapper<AfterCourseTagRelation> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("course_id",id);
        afterCourseTagRelationService.getBaseMapper().delete(queryWrapper1);
        fileRouterService.deleteOldFile(e.getImg());
        if(e.getVideos()!=null&&e.getVideos().length()>0){
            String[] strs=e.getVideos().split(",");
            for(String idStr:strs){
                fileRouterService.deleteOldFile(Long.parseLong(idStr));
            }
        }
        if(e.getDocs()!=null&&e.getDocs().length()>0){
            String[] strs=e.getDocs().split(",");
            for(String idStr:strs){
                fileRouterService.deleteOldFile(Long.parseLong(idStr));
            }
        }
        return Result.success(this.getBaseMapper().deleteById(id));
    }

    @Override
    public Result<Object> updateStatus(StatusDTO dto, HttpServletRequest request) {
        TeacherTraining course=new TeacherTraining();
        course.setId(dto.getId());
        course.setStatus(dto.getStatus());
        return Result.success(this.baseMapper.updateById(course));
    }

}
