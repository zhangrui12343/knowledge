package com.zr.test.demo.util;

import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;

import java.security.MessageDigest;

/**
 * md5的工具类
 *
 * @author huang_kangjie
 * @date 2020/11/9 0009 16:40
 */
public class Md5Util {

     /**
      * 对字符串md5加密
      *
      * @param str 字符串
      * @return md5字符串
      */
     public static String getMD5(String str) {
          try {
               byte[] tem = str.getBytes();
               MessageDigest md5 = MessageDigest.getInstance("md5");
               md5.reset();
               md5.update(tem);
               StringBuilder sb = new StringBuilder();
               for (byte t : md5.digest()) {
                    String s=Integer.toHexString(t & 0xFF);
                    sb.append(s.length()==1?"0"+s:s);
               }
               return sb.toString();
          } catch (Exception e) {
               throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "MD5 error " + e.getMessage());
          }
     }

     /**
      * 获取md5
      * @param bytes 字节数组
      * @return md5的hash值
      */
     public static String getMD5(byte[] bytes) {
          try {
               MessageDigest m = MessageDigest.getInstance("MD5");
               StringBuilder sb = new StringBuilder();
               for (byte t : m.digest(bytes)) {
                    String s=Integer.toHexString(t & 0xFF);
                    sb.append(s.length()==1?"0"+s:s);
               }
               return sb.toString();
          } catch (Exception e) {
               throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "MD5 error " + e.getMessage());
          }
     }

}

