package com.zr.test.demo.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.config.enums.FileTypeEnums;
import com.zr.test.demo.model.entity.FileRouter;
import com.zr.test.demo.dao.FileRouterMapper;
import com.zr.test.demo.model.vo.FileVO;
import com.zr.test.demo.service.IFileRouterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import com.zr.test.demo.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-21
 */
@Service
@Slf4j
public class FileRouterServiceImpl extends ServiceImpl<FileRouterMapper, FileRouter> implements IFileRouterService {
    @Value("${file.save.path}")
    private String fileSavePath;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<FileVO> upload(MultipartFile file) {
        if (FileUtil.isEmpty(file)) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL,"文件不能为空");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date n = new Date();
        String now =sdf.format(n);
        String fileName = file.getOriginalFilename();
        if(StringUtil.isEmpty(fileName)){
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL,"文件上传失败，文件名为空");
        }
        String type=fileName.substring(fileName.lastIndexOf(".")+1);
        String temp=type+File.separator+now + "-" +fileName;
        File dest = new File(new File(fileSavePath).getAbsolutePath()+ File.separator + temp);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败 filename={}  time={}", file.getOriginalFilename(), now);
            log.error("{}", e.getMessage(), e);
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
        }
        FileRouter fileRouter = new FileRouter();
        fileRouter.setFilePath(temp);
        fileRouter.setAbspath(dest.getAbsolutePath());
        fileRouter.setCreateTime(n);
        this.getBaseMapper().insert(fileRouter);
        FileVO vo=new FileVO();
        vo.setId(fileRouter.getId());
        vo.setPath(FileUtil.getBase64FilePath(temp));
        return Result.success(vo);
    }

    @Override
    public Result<Object> view(Integer id, HttpServletResponse response) {
        // 通常上传的文件会有一个数据表来存储，这里返回的id是记录id
        FileRouter file=this.baseMapper.selectById(id);
        if(file==null){
            throw new CustomException(ErrorCode.SEARCH_TERREC_FAIL,"没有文件");
        }

        File source= new File(file.getAbspath());
        response.setContentType(FileTypeEnums.endWith(file.getFilePath()));

        try {
            FileCopyUtils.copy(new FileInputStream(source), response.getOutputStream());
        } catch (Exception e) {
            log.error("{}",e.getMessage());
            throw new CustomException(ErrorCode.SYS_CUSTOM_ERR,"文件流输出失败:"+e.getMessage());
        }
        return Result.success(null);
    }

    public void delete(List<Long> ids) {
        if (ListUtil.isEmpty(ids)) {
            return;
        }
        for (Long id : ids) {
            FileRouter fileRouter = this.getBaseMapper().selectById(id);
            if (fileRouter != null) {
                File file = new File(fileRouter.getFilePath());
                if (file.exists()) {
                    if (file.delete()) {
                        log.info("文件删除成功,删除数据库记录,path={},id={}", fileRouter.getFilePath(), id);
                        this.getBaseMapper().deleteById(id);
                    } else {
                        log.error("文件删除失败,path={},id={}", fileRouter.getFilePath(), id);
                    }
                }
            }
        }
    }
    public String selectPath(Long id) {
            FileRouter fileRouter = this.getBaseMapper().selectById(id);
            if (fileRouter != null) {
                return Optional.ofNullable(fileRouter.getFilePath()).orElse("");
            }
            return null;
    }
    public void delete(String idsStr) {
        if (StringUtil.isEmpty(idsStr)) {
            return;
        }
        String[] ids=idsStr.split(",");
        for (String idss : ids) {
            Long id=Long.parseLong(idss);
            FileRouter fileRouter = this.getBaseMapper().selectById(id);
            if (fileRouter != null) {
                File file = new File(fileRouter.getFilePath());
                if (file.exists()) {
                    if (file.delete()) {
                        log.info("文件删除成功,删除数据库记录,path={},id={}", fileRouter.getFilePath(), id);
                        this.getBaseMapper().deleteById(id);
                    } else {
                        log.error("文件删除失败,path={},id={}", fileRouter.getFilePath(), id);
                    }
                }
            }
        }
    }
    public void deleteOldFile(Long id) {
        if (id == null) {
            return;
        }
        FileRouter fileRouter = this.getBaseMapper().selectById(id);
        if (fileRouter != null) {
            String path = fileRouter.getFilePath();
            if (!StringUtil.isEmpty(path)) {
                File file = new File(path);
                if (file.exists()) {
                    if (file.delete()) {
                        log.info("文件删除成功,删除数据库记录,path={},id={}", path, id);
                        this.getBaseMapper().deleteById(id);
                    } else {
                        log.error("文件删除失败,path={},id={}", path, id);
                    }
                }
            }
        }

    }
}
