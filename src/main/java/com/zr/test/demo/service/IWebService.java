package com.zr.test.demo.service;

import com.zr.test.demo.common.PageInfo;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.model.dto.KeywordDTO;
import com.zr.test.demo.model.vo.IndexVO;


public interface IWebService {

    Result<Object> query(KeywordDTO dto);

    Result<IndexVO> index();
}
