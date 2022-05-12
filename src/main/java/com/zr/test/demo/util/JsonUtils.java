package com.zr.test.demo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * Json工具类
 *
 * @author huang_kangjie
 * @create 2020-03-17 14:28
 **/
@Log4j2
public class JsonUtils {

     private static final ObjectMapper MAPPER = new ObjectMapper();

     static {
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          MAPPER.setDateFormat(format);
     }

     /**
      * 转换成json字符串
      *
      * @param obj 序列化对象
      * @return json字符串
      */
     public static String toJson(Object obj) {
          if (obj == null) {
               return null;
          }
          try {
               return MAPPER.writeValueAsString(obj);
          } catch (IOException e) {
               log.error(String.format("obj=[%s]", obj.toString()), e);
               throw new CustomException(ErrorCode.SYS_CUSTOM_ERR, "json is illegal");
          }
     }

     /**
      * 将json转化为对象
      *
      * @param json  json字符串
      * @param clazz 反序列化对象的class
      * @param <T>   反序列化对象
      * @return 反序列化对象
      */
     public static <T> T fromJson(String json, Class<T> clazz) {
          if (json == null) {
               return null;
          }
          try {
               return MAPPER.readValue(json, clazz);
          } catch (IOException e) {
               log.error(String.format("json=[%s]", json), e);
               throw new CustomException(ErrorCode.SYS_CUSTOM_ERR, "json is illegal");
          }
     }

     /**
      * 将json对象转化为集合类型
      *
      * @param json            json对象
      * @param collectionClazz 具体的集合类的class，如：ArrayList.class
      * @param clazz           集合内存放的对象的class
      * @return 反序列化对象
      */
     public static <T> Collection<T> fromJson(String json, Class<? extends Collection> collectionClazz,
                                              Class<T> clazz) {
          if (json == null) {
               return null;
          }
          try {
               return MAPPER.readValue(json, getCollectionType(collectionClazz, clazz));
          } catch (IOException e) {
               log.error(String.format("json=[%s]", json), e);
               throw new CustomException(ErrorCode.SYS_CUSTOM_ERR, "json is illegal");
          }
     }

     /**
      * 获取JavaType
      *
      * @param collectionClass 集合的class
      * @param elementClasses  元素的class
      * @return java的类型
      */
     private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
          return MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
     }

     /**
      * 美化json字符串
      *
      * @param json json字符串
      * @return 美化后的json
      */
     public static String pretty(String json) {
          if (StringUtils.isEmpty(json)) {
               return "";
          }
          ObjectMapper mapper = new ObjectMapper();
          Object obj;
          try {
               obj = mapper.readValue(json, Object.class);
               return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
          } catch (IOException ignore) {

          }
          return json;
     }

     /**
      * 泛型方法反序列化
      * 使用方法 JsonUtils.fromJson(json, new TypeReference<Result<User>>() {});
      * 解决泛型擦除问题
      *
      * @param json          json
      * @param typeReference 类型
      * @param <T>           得到的返回类型
      * @return 解析出来的数据
      */
     public static <T> T fromJson(String json, TypeReference<T> typeReference) {
          try {
               return new ObjectMapper().readValue(json, typeReference);
          } catch (Exception e) {
               log.error(String.format("json=[%s]", json), e);
               throw new CustomException(ErrorCode.SYS_CUSTOM_ERR, "json is illegal");
          }
     }

}
