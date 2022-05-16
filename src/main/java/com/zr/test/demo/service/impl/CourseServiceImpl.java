package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.dao.SecondCategoryMapper;
import com.zr.test.demo.dao.TagMapper;
import com.zr.test.demo.model.dto.CourseDTO;
import com.zr.test.demo.model.dto.CourseQueryDTO;
import com.zr.test.demo.model.entity.CourseCategoryEntity;
import com.zr.test.demo.model.entity.CourseEntity;
import com.zr.test.demo.model.entity.SecondCategory;
import com.zr.test.demo.model.entity.Tag;
import com.zr.test.demo.model.vo.CourseVO;
import com.zr.test.demo.repository.CourseCategoryMapperImpl;
import com.zr.test.demo.repository.CourseMapperImpl;
import com.zr.test.demo.service.ICourseService;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.config.cache.LocalUtil;
import com.zr.test.demo.util.StringUtil;
import com.zr.test.demo.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private final TagMapper tagMapper;
    private final SecondCategoryMapper secondCategoryMapper;
    @Value("${file.save.path}")
    private String fileSavePath;
    private String path = fileSavePath + "course/";
    private String split = "????";
    private final LocalUtil localUtil;

    @Autowired
    public CourseServiceImpl(CourseMapperImpl service, CourseCategoryMapperImpl categoryMapper, TagMapper tagMapper, SecondCategoryMapper secondCategoryMapper, LocalUtil localUtil) {
        this.service = service;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.secondCategoryMapper = secondCategoryMapper;
        this.localUtil = localUtil;
    }

    @Override
    public Result<Object> add(CourseDTO dto, MultipartFile img, MultipartFile[] pdf, MultipartFile video, HttpServletRequest request) {
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        String imgPath = null;
        String videoPath = null;
        StringBuilder pdfPath = new StringBuilder();

        File p = new File(path);
        if (!p.exists()) {
            p.mkdir();
        }
        try {
            if (img!=null&&!img.isEmpty()) {
                //获取文件的原始文件名
                //保存到服务器transferTo()方法
                imgPath = path + System.currentTimeMillis() + "-" + img.getOriginalFilename();
                img.transferTo(new File(imgPath));
            }
            //如果接收过来的数组不为空便遍历出每个文件，然后上传
            if (pdf!=null&&pdf.length > 0) {
                for (MultipartFile photo : pdf) {
                    if(photo!=null&&!photo.isEmpty()){
                        String originalFilename = photo.getOriginalFilename();
                        String temp = path + System.currentTimeMillis() + "-" + originalFilename;
                        photo.transferTo(new File(temp));
                        pdfPath.append(temp).append(split);
                    }
                }
                pdfPath.substring(0, pdfPath.length() - split.length());
            }
            if (video!=null&&!video.isEmpty()) {
                //获取文件的原始文件名
                //保存到服务器transferTo()方法
                videoPath = path + System.currentTimeMillis() + "-" + video.getOriginalFilename();
                video.transferTo(new File(videoPath));
            }
        } catch (Exception e) {
            log.error("上传文件失败:{}", e.getMessage());
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
        }
        entity.setTime(new Date());
        entity.setImg(imgPath);
        entity.setVideo(videoPath);
        entity.setPdf(pdfPath.toString());
        return Result.success(service.insertOne(entity));
    }

    @Override
    public Result<PageInfo<CourseVO>> query(CourseQueryDTO dto, HttpServletRequest request) {
        PageInfo<CourseVO> pageInfo = new PageInfo<>();
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        IPage<CourseEntity> es = service.selectPageByTime(entity, dto.getStartTime(), dto.getEndTime(), dto.getPage(), dto.getPageSize());
        if (es.getTotal() == 0) {
            pageInfo.setPage(dto.getPage());
            pageInfo.setPageSize(dto.getPageSize());
            pageInfo.setTotal(es.getTotal());
            return Result.success(pageInfo);
        }
        //可使用本地缓存
        Map<Long, String> all;
        if (localUtil.get("course_category") == null) {
            all = categoryMapper.selectByEntity(null).stream().collect(Collectors.toMap(CourseCategoryEntity::getId, CourseCategoryEntity::getName));
            localUtil.set("course_category", all);
        } else {
            all = (Map<Long, String>) localUtil.get("course_category");
        }
        Map<Long, String> tagAll;
        if (localUtil.get("tagAll") == null) {
            tagAll = tagMapper.selectList(null).stream().collect(Collectors.toMap(Tag::getId, Tag::getName));
            localUtil.set("tagAll", tagAll);
        } else {
            tagAll = (Map<Long, String>) localUtil.get("tagAll");
        }
        Map<Long, String> categoryAll;
        if (localUtil.get("categoryAll") == null) {
            categoryAll = secondCategoryMapper.selectList(null).stream().collect(Collectors.toMap(SecondCategory::getId, SecondCategory::getName));
            localUtil.set("categoryAll", categoryAll);
        } else {
            categoryAll = (Map<Long, String>) localUtil.get("categoryAll");
        }
        List<CourseVO> vos = new ArrayList<>();
        es.getRecords().forEach(e -> {
            CourseVO v = new CourseVO();
            v.setSubject(all.get(e.getSubject()));
            v.setTime(TimeUtil.getTime(e.getTime()));
            v.setBooks(all.get(e.getBooks()));
            v.setStatus(e.getStatus());
            v.setGrade(all.get(e.getGrade()));
            v.setXueduan(all.get(e.getXueduan()));
            v.setName(e.getName());
            v.setId(e.getId());
            if (!StringUtil.isEmpty(e.getImg())) {
                v.setImg(FileUtil.getBase64FilePath(e.getImg()));
            }
            if (!StringUtil.isEmpty(e.getPdf())) {
                String[] arr = e.getPdf().split(split);
                for (String temp : arr) {
                    FileUtil.getBase64FilePath(temp);
                }
                v.setPdf(arr);
            }
            if (!StringUtil.isEmpty(e.getVideo())) {
                v.setVideo(FileUtil.getBase64FilePath(e.getVideo()));
            }
            v.setSecondCategoryName(categoryAll.get(e.getSecondCategoryId()));
            v.setTagName(tagAll.get(e.getTagId()));
            vos.add(v);
        });
        pageInfo.setPage(dto.getPage());
        pageInfo.setPageSize(dto.getPageSize());
        pageInfo.setTotal(es.getTotal());
        pageInfo.setList(vos);
        return Result.success(pageInfo);
    }

    @Override
    public Result<Object> update(CourseDTO dto, MultipartFile img, MultipartFile[] pdf, MultipartFile video, HttpServletRequest request) {
        if (dto.getId() == null) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        try {
            if (!img.isEmpty()) {
                String imgPath = path + System.currentTimeMillis() + "-" + img.getOriginalFilename();
                img.transferTo(new File(imgPath));
                entity.setImg(imgPath);
                //如果重新上传了文件，需要将旧文件的文件路径放到dto里
                if(!StringUtil.isEmpty(dto.getImg())){
                    String oldPath = FileUtil.getFilePath(dto.getImg());
                    File oldImg = new File(oldPath);
                    if (oldImg.exists()) {
                        if(!oldImg.delete()){
                            log.error("图片文件删除失败,path={}",oldPath);
                        }
                    }
                }

            }
            if (!video.isEmpty()) {
                String imgPath = path + System.currentTimeMillis() + "-" + video.getOriginalFilename();
                video.transferTo(new File(imgPath));
                entity.setVideo(imgPath);
                if(!StringUtil.isEmpty(dto.getVideo())){
                    String oldPath = FileUtil.getFilePath(dto.getVideo());
                    File oldImg = new File(oldPath);
                    if (oldImg.exists()) {
                        if(!oldImg.delete()){
                            log.error("视频文件删除失败,path={}",oldPath);
                        }
                    }
                }
            }
            if (pdf != null && pdf.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdf.length; i++) {
                    String imgPath = path + System.currentTimeMillis() + "-" + pdf[i].getOriginalFilename();
                    pdf[i].transferTo(new File(imgPath));
                    sb.append(imgPath).append(split);
                    if(!StringUtil.isEmpty(dto.getPdf()[i])){
                        String oldPath = FileUtil.getFilePath(dto.getPdf()[i]);
                        File oldImg = new File(oldPath);
                        if (oldImg.exists()) {
                            if(!oldImg.delete()){
                                log.error("pdf文件删除失败,path={}",oldPath);
                            }
                        }
                    }

                }
                sb.substring(0, sb.length() - split.length());
                entity.setPdf(sb.toString());
            }
        } catch (IOException e) {
            log.error("修改课程出错,e:{}",e.getMessage());
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
        }
        entity.setTime(new Date());
        return Result.success(service.updateById(entity));
    }

    @Override
    public Result<Object> delete(Long id, HttpServletRequest request) {
        CourseEntity entity = new CourseEntity();
        entity.setId(id);
        List<CourseEntity> entities = service.selectByEntity(entity);
        if (ListUtil.isEmpty(entities)) {
            return Result.success(0);
        }
        CourseEntity e = entities.get(0);
        if (!StringUtil.isEmpty(e.getVideo())) {
            File video = new File(e.getVideo());
            if (video.exists()) {
                if(!video.delete()){
                    log.error("视频文件删除失败,path={}",e.getVideo());
                }
            }
        }
        if (!StringUtil.isEmpty(e.getImg())) {
            File img = new File(e.getImg());
            if (img.exists()) {
                if(!img.delete()){
                    log.error("图片文件删除失败,path={}",e.getImg());
                }
            }
        }

        if (!StringUtil.isEmpty(e.getPdf())) {
            for (String p : e.getPdf().split(split)) {
                File pd = new File(p);
                if (pd.exists()) {
                    if(!pd.delete()){
                        log.error("pdf文件删除失败,path={}",p);
                    }
                }
            }
        }
        return Result.success(service.deleteById(id));
    }

}
