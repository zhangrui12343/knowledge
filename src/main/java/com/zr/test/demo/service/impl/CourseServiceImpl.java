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
import com.zr.test.demo.model.entity.*;
import com.zr.test.demo.model.vo.CourseVO;
import com.zr.test.demo.repository.CourseCategoryMapperImpl;
import com.zr.test.demo.repository.CourseMapperImpl;
import com.zr.test.demo.repository.CourseTypeMapperImpl;
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
    private final CourseTypeMapperImpl courseTypeMapper;
    @Value("${file.save.path}")
    private String fileSavePath;
    private String path = fileSavePath + "course/";
    private String split = "????";

    @Autowired
    public CourseServiceImpl(CourseMapperImpl service, CourseCategoryMapperImpl categoryMapper, TagMapper tagMapper,
                             CourseTypeMapperImpl courseTypeMapper) {
        this.service = service;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.courseTypeMapper = courseTypeMapper;
    }

    @Override
    public Result<Object> add(CourseDTO dto, MultipartFile img, MultipartFile learningTask, MultipartFile homework,
                              MultipartFile video, HttpServletRequest request) {
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(dto, entity);
        String imgPath = null;
        String videoPath = null;
        String learningTaskPath = null;
        String homeworkPath = null;
        File p = new File(path);
        if (!p.exists()) {
            p.mkdir();
        }
        try {
            long now=System.currentTimeMillis();
            if (!FileUtil.isEmpty(img)) {
                //获取文件的原始文件名
                //保存到服务器transferTo()方法
                imgPath = path + now + "-" + img.getOriginalFilename();
                img.transferTo(new File(imgPath));
            }
            if (!FileUtil.isEmpty(video)) {
                videoPath = path + now + "-" + video.getOriginalFilename();
                video.transferTo(new File(videoPath));
            }
            if (!FileUtil.isEmpty(learningTask)) {
                learningTaskPath = path + now + "-" + learningTask.getOriginalFilename();
                learningTask.transferTo(new File(learningTaskPath));
            }
            if (!FileUtil.isEmpty(homework)) {
                homeworkPath = path + now + "-" + homework.getOriginalFilename();
                homework.transferTo(new File(homeworkPath));
            }
        } catch (Exception e) {
            log.error("上传文件失败:{}", e.getMessage());
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
        }
        entity.setApp(getIdsToString(dto.getApps()));
        entity.setCourseTag(getIdsToString(dto.getCourseTagIds()));
        entity.setCourseType(getIdsToString(dto.getCourseTypeIds()));
        entity.setImg(imgPath);
        entity.setVideo(videoPath);
        entity.setLearningTask(learningTaskPath);
        entity.setHomework(homeworkPath);
        entity.setTime(new Date());
        return Result.success(service.insertOne(entity));
    }

    private String getIdsToString(List<Long> ids) {
        StringBuilder sb=new StringBuilder();
        ids.forEach(item-> sb.append(item).append(","));
        return sb.deleteCharAt(sb.length()-1).toString();
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
            v.setSubject(all.get(e.getSubject()));
            v.setBooks(all.get(e.getBooks()));
            v.setStatus(e.getStatus());
            v.setGrade(all.get(e.getGrade()));
            v.setXueduan(all.get(e.getXueduan()));
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
//                if(!StringUtil.isEmpty(dto.getImg())){
//                    String oldPath = FileUtil.getFilePath(dto.getImg());
//                    File oldImg = new File(oldPath);
//                    if (oldImg.exists()) {
//                        if(!oldImg.delete()){
//                            log.error("图片文件删除失败,path={}",oldPath);
//                        }
//                    }
//                }

            }
            if (!video.isEmpty()) {
                String imgPath = path + System.currentTimeMillis() + "-" + video.getOriginalFilename();
                video.transferTo(new File(imgPath));
                entity.setVideo(imgPath);
//                if(!StringUtil.isEmpty(dto.getVideo())){
//                    String oldPath = FileUtil.getFilePath(dto.getVideo());
//                    File oldImg = new File(oldPath);
//                    if (oldImg.exists()) {
//                        if(!oldImg.delete()){
//                            log.error("视频文件删除失败,path={}",oldPath);
//                        }
//                    }
//                }
            }
            if (pdf != null && pdf.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdf.length; i++) {
                    String imgPath = path + System.currentTimeMillis() + "-" + pdf[i].getOriginalFilename();
                    pdf[i].transferTo(new File(imgPath));
                    sb.append(imgPath).append(split);

                }
                sb.substring(0, sb.length() - split.length());
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

        return Result.success(service.deleteById(id));
    }

}
