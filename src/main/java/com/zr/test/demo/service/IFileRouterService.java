package com.zr.test.demo.service;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.entity.FileRouter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.test.demo.model.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zr
 * @since 2022-05-21
 */
public interface IFileRouterService extends IService<FileRouter> {

    Result<FileVO> upload(MultipartFile file);

    Result<Object> view(Integer id, HttpServletResponse response);
}
