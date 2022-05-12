package com.zr.test.demo.util;

import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 内部字符串处理工具
 *
 * @author huang_kangjie
 * @date 2020/4/2 0002 11:07
 */
public class StringUtil {

     public static final String POINT = ".";
     private static Pattern PATTERN = Pattern.compile("\\s*|\t|\r|\n");
     public static final int DATE = 10;
     public static final int TIME = 19;

     /** 去掉空格换行等 */
     private static Pattern REPLACE_PATTERN = Pattern.compile("\\s*|\t|\r|\n");

     public static String getIntegerStr(List<Integer> list){
          StringBuilder rs = new StringBuilder();
          if(ListUtil.isEmpty(list)) {
               return rs.toString();
          }
          int i = 1;
          for(Integer s : list) {
               if(i == list.size()){
                    rs.append(s);
               } else {
                    rs.append(s);
                    rs.append(",");
               }
               i++;
          }
          return rs.toString();
     }

     public static String getStringStr(List<String> list){
          StringBuilder rs = new StringBuilder();
          if(ListUtil.isEmpty(list)) {
               return rs.toString();
          }
          int i = 1;
          for(String s : list) {
               if(i == list.size()){
                    rs.append("\"");
                    rs.append(s);
                    rs.append("\"");
               } else {
                    rs.append("\"");
                    rs.append(s);
                    rs.append("\",");
               }
               i++;
          }
          return rs.toString();
     }

     /**
      * 去掉换行、空格 等
      * @param str 字符串
      * @return 被去掉换行、空格的字符串
      */
     public static String replace(String str) {
          String destination = "";
          if (str!=null) {
               Matcher m = PATTERN.matcher(str);
               destination = m.replaceAll("");
          }
          return destination;
     }

     /**
      * 从base64 encode 的获取地址
      * @param path base64 encode
      * @return 文件的绝对地址
      */
     public static String getPathFromBase64(String path){
          try {
               if(path.contains("%")){
                    path = URLDecoder.decode(path, "UTF-8");
               }
               path = new String(Base64Utils.decodeFromString(path));
          } catch (Exception e) {
               throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "参数非法， format: base64 encode");
          }

          return path;
     }

     /**
      * 截取字符串 如果不满足长度则返回源
      * @param str 字符串
      * @param length 指定长度内
      * @return 指定长度内的字符串 0 - length
      */
     public static String mySubtring(String str, int length){
          if(!StringUtils.isEmpty(str) && str.length() > length) {
               return str.substring(0, length);
          }
          return str;
     }

     /**
      * 字符串转base64和urlencode
      * @param str 字符串
      * @return base64 + urlEncode
      */
     public static String toBase64Urlencode(String str){
          try {
               return Base64Utils.encodeToUrlSafeString(str.getBytes("UTF-8"));
          } catch (Exception e) {
               throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "format: base64 encode");
          }
     }

     /**
      * 判断字符串是否为空
      */
     public static boolean isEmpty(String str) {
          if (str != null) {
               str = str.trim();
          }
          return StringUtils.isEmpty(str);
     }

     /**
      * 判断字符串是否非空
      */
     public static boolean isNotEmpty(String str) {
          return !isEmpty(str);
     }


     /**
      * 判断字符串是否包含中文
      * @param str 字符串
      * @return Boolean
      * @throws UnsupportedEncodingException 异常
      */
     public static boolean isChinese(String str) throws UnsupportedEncodingException
     {
          int len = str.length();
          for(int i = 0;i < len;i ++)
          {
               String temp = URLEncoder.encode(str.charAt(i) + "", "utf-8");
               if(temp.equals(str.charAt(i) + "")) {
                    continue;
               }
               String[] codes = temp.split("%");
               //判断是中文还是字符(下面判断不精确，部分字符没有包括)
               for(String code:codes)
               {
                    if(code.compareTo("40") > 0) {
                         return true;
                    }
               }
          }
          return false;
     }

}

