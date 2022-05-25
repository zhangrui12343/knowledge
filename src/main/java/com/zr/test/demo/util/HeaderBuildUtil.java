package com.zr.test.demo.util;

import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求头处理工具
 * 如果不需要处理异常则使用CommonUtil 的获取方法
 * @author huang_kangjie
 * @date 2020/6/19 0019 17:58
 */
@Slf4j
public class HeaderBuildUtil {

     @Data
     @ToString
     public static class Header {

          /**
           * 用户登陆鉴权key
           */
          private String key;

     }

     /**
      * 构建请求头
      * @return [HeaderBuildUtil.Header]
      */
     public static HeaderBuildUtil.Header builder(ServletRequest req){
          return build((HttpServletRequest) req);
     }

     /**
      * 构建请求头
      * @return [HeaderBuildUtil.Header]
      */
     public static HeaderBuildUtil.Header builder(){
          return build(null);
     }

     /**
      * 统一请求头参数解析
      * @param webRequest 请求
      * @return HeaderBuildUtil.Header
      */
     public static HeaderBuildUtil.Header builder(NativeWebRequest webRequest){
          return buildNative(webRequest);
     }

     /**
      * 构建请求头
      *
      * 该方法私有
      *
      * @return [HeaderBuildUtil.Header]
      */
     private static HeaderBuildUtil.Header build(HttpServletRequest request){
          HeaderBuildUtil.Header header = new HeaderBuildUtil.Header();
          if(request == null) {
               request = ApplicationContextUtil.getHttpServletRequest();
          }
          header.setKey(getHeaderValue(request, "key"));
          return header;
     }

     /**
      * 构建请求头
      *
      * 该方法私有
      *
      * @return [HeaderBuildUtil.Header]
      */
     private static HeaderBuildUtil.Header buildNative(NativeWebRequest request){
          HeaderBuildUtil.Header header = new HeaderBuildUtil.Header();
          header.setKey(request.getHeader("key"));
          return header;
     }

     /**
      * 得到头部信息
      *
      * @param request request
      * @param key header名称
      * @return header值[String]类型
      */
     private static String getHeaderValue(HttpServletRequest request, String key) {
          String value = request.getHeader(key);
          //if(StringUtils.isEmpty(value)) {
          //     log.error("请求头为空！ key = {}", key);
          //     throw new CustomException(ErrorCode.SYS_REQUEST_HEADER_ERR, "请求头 key = " + key + " 为空");
          //}
          return value;
     }

     /**
      * 得到头部信息
      *
      * @param key header名称
      * @return header值[String]类型
      */
     private static String getHeaderValue(String key) {
          return getHeaderValue(ApplicationContextUtil.getHttpServletRequest(), key);
     }

     /**
      * 得到头部信息
      *
      * @param request request
      * @param key header名称
      * @return header值[Integer]类型
      */
     private static Integer getIntHeaderValue(HttpServletRequest request, String key) {
          String value = getHeaderValue(request, key);
          int intValue;
          try {
               intValue = Integer.parseInt(value);
          } catch (Exception e) {
               log.error("请求头参数不是数字 key = {}, value = {}", key, value);
               throw new CustomException(ErrorCode.SYS_REQUEST_HEADER_ERR, "请求头 key = " + key + " 不是数字");
          }
          return intValue;
     }

     /**
      * 得到头部信息
      *
      * @param key header名称
      * @return header值[Integer]类型
      */
     private static Integer getIntHeaderValue(String key) {
          return getIntHeaderValue(ApplicationContextUtil.getHttpServletRequest(), key);
     }
}

