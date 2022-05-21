package com.zr.test.demo.repository;

import com.zr.test.demo.dao.FileRouterMapper;
import com.zr.test.demo.model.entity.FileRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class FileRouterMapperImpl {
    @Autowired
    private FileRouterMapper dao;

    public String getPathById(Long id){
       FileRouter fileRouter=dao.selectById(id);
        if(fileRouter==null){
            return null;
        }
        return fileRouter.getFilePath();
    }
    public int deleteById(Long id){
        return dao.deleteById(id);
    }
}
