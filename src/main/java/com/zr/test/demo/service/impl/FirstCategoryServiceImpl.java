package com.zr.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zr.test.demo.common.Constant;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.dto.FirstCategoryDTO;
import com.zr.test.demo.model.entity.CourseCategoryEntity;
import com.zr.test.demo.model.entity.CourseEntity;
import com.zr.test.demo.model.entity.FirstCategory;
import com.zr.test.demo.dao.FirstCategoryMapper;
import com.zr.test.demo.service.IFirstCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-16
 */
@Service
@Slf4j
public class FirstCategoryServiceImpl extends ServiceImpl<FirstCategoryMapper, FirstCategory> implements IFirstCategoryService {
    @Value("${file.save.path}")
    private String fileSavePath;
    private String path = fileSavePath + "firstCategory/";
    @Override
    public Result<Object> add(FirstCategoryDTO dto, MultipartFile img) {
        FirstCategory entity = new FirstCategory();
        entity.setName(dto.getName());
        entity.setOrder(dto.getOrder());
        if(img==null||img.isEmpty()){
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR,"上传图片为空");
        }
        String imgPath;
        try {
            imgPath = path + System.currentTimeMillis() + "-" + img.getOriginalFilename();
            img.transferTo(new File(imgPath));
        } catch (Exception e) {
            log.error("上传文件失败:{}", e.getMessage());
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
        }
        entity.setImg(imgPath);
        entity.setTag(ListUtil.listToString(dto.getTag()));
        entity.setCategory(ListUtil.listToString(dto.getCategory()));
        entity.setType(Constant.AFTER_COURSE);
        return Result.success(this.getBaseMapper().insert(entity));
    }



    @Override
    public Result<List<FirstCategory>> queryByType(Integer type) {
        QueryWrapper<FirstCategory> queryWrapper=new QueryWrapper<>(null);
        queryWrapper.eq("type",type);
        List<FirstCategory> list=this.getBaseMapper().selectList(queryWrapper);
        if(!ListUtil.isEmpty(list)){
            list.forEach(e-> e.setImg(FileUtil.getBase64FilePath(e.getImg())));
        }
        return Result.success(list);
    }

    @Override
    public Result<Object> delete(Long id) {
        FirstCategory e=this.getBaseMapper().selectById(id);
        int i=0;
        if (e != null) {
            i= this.getBaseMapper().deleteById(id);
            if(i>0&&!StringUtil.isEmpty(e.getImg())){
                File f=new File(e.getImg());
                if(f.exists()){
                    if(!f.delete()){
                        log.error("文件删除失败，path={}",e.getImg());
                    }
                }
            }
        }
        return Result.success(i);
    }

    @Override
    public Result<Object> update(FirstCategoryDTO dto, MultipartFile img) {
        if(dto.getId()==null){
            throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR);
        }
        FirstCategory entity = new FirstCategory();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setOrder(dto.getOrder());
        if(img!=null&&!img.isEmpty()){
            String imgPath;
            try {
                imgPath = path + System.currentTimeMillis() + "-" + img.getOriginalFilename();
                img.transferTo(new File(imgPath));
            } catch (Exception e) {
                log.error("上传文件失败:{}", e.getMessage());
                throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
            }
            entity.setImg(imgPath);
            //删除原文件
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
        entity.setTag(ListUtil.listToString(dto.getTag()));
        entity.setCategory(ListUtil.listToString(dto.getCategory()));
        entity.setType(Constant.AFTER_COURSE);
        return Result.success(this.getBaseMapper().insert(entity));

    }
}
