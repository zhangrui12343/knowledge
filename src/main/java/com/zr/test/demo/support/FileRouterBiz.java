package com.zr.test.demo.support;

import com.zr.test.demo.dao.FileRouterMapper;
import com.zr.test.demo.model.entity.FileRouter;
import com.zr.test.demo.model.vo.FileVO;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FileRouterBiz {
    @Autowired
    private FileRouterMapper fileRouterMapper;
    public void delete(List<Long> ids) {
        if (ListUtil.isEmpty(ids)) {
            return;
        }
        for (Long id : ids) {
            FileRouter fileRouter = this.fileRouterMapper.selectById(id);
            if (fileRouter != null) {
                File file = new File(fileRouter.getFilePath());
                if (file.exists()) {
                    if (file.delete()) {
                        log.info("文件删除成功,删除数据库记录,path={},id={}", fileRouter.getFilePath(), id);
                        this.fileRouterMapper.deleteById(id);
                    } else {
                        log.error("文件删除失败,path={},id={}", fileRouter.getFilePath(), id);
                    }
                }
            }
        }
    }
    public String selectPath(Long id) {
        FileRouter fileRouter = this.fileRouterMapper.selectById(id);
        if (fileRouter != null) {
            return Optional.ofNullable(fileRouter.getFilePath()).orElse("");
        }
        return null;
    }
    public FileVO selectFile(Long id) {
        FileRouter fileRouter = this.fileRouterMapper.selectById(id);
        FileVO vo=new FileVO();
        if (fileRouter != null) {
            String path=fileRouter.getFilePath();
            if(StringUtil.isEmpty(path)){
                return vo;
            }
            vo.setPath(FileUtil.getBase64FilePath(fileRouter.getFilePath()));
            vo.setId(fileRouter.getId());
            vo.setName(fileRouter.getName());
            return vo;
        }
        return vo;
    }
    public void delete(String idsStr) {
        if (StringUtil.isEmpty(idsStr)) {
            return;
        }
        String[] ids=idsStr.split(",");
        for (String idss : ids) {
            Long id=Long.parseLong(idss);
            FileRouter fileRouter = this.fileRouterMapper.selectById(id);
            if (fileRouter != null) {
                File file = new File(fileRouter.getFilePath());
                if (file.exists()) {
                    if (file.delete()) {
                        log.info("文件删除成功,删除数据库记录,path={},id={}", fileRouter.getFilePath(), id);
                        this.fileRouterMapper.deleteById(id);
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
        FileRouter fileRouter = this.fileRouterMapper.selectById(id);
        if (fileRouter != null) {
            String path = fileRouter.getFilePath();
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

    }
}
