package com.zr.test.demo.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author huang_kangjie
 * @date 2020/3/25 0025 15:25
 */
public class ListUtil {

     /**
      * 集合是否为空 或者 null
      *
      * @param list 集合
      * @return true 空 false 非空
      */
     public static boolean isEmpty(List<?> list) {
          return list == null || list.size() <= 0;
     }

     /**
      * 对list进行分组
      *
      * @param list 源list
      * @param size 每个组需要存放的大小
      * @return List<? extends Object [ ]> 切割成数组
      */
     public static List<? extends Object[]> listToArray(List<?> list, int size) {
          List<Object[]> list2 = new ArrayList<>();
          Object[] arr = null;
          for (int j = 1, len = list.size() + 1; j < len; j++) {
               if (j == 1) {
                    //初始化数组大小
                    if (len > size) {
                         arr = new Object[size];
                    } else {
                         arr = new Object[len - 1];
                    }
               }
               //给数组赋值
               arr[j - (size * list2.size()) - 1] = list.get(j - 1);
               if (j % size == 0) {
                    //数组填值满后放到集合中
                    list2.add(arr);
                    if (len - j - 1 > size) {
                         arr = new Object[size];
                         //不允许数组有空值创建最后一个数组的大小(如果都要一定大小可以去掉)  
                    } else {
                         arr = new Object[len - (size * list2.size()) - 1];
                    }
               } else if (j == len - 1) {
                    //最后一个数组可能没有规定大小
                    list2.add(arr);
               }
          }
          return list2;
     }

     /**
      * list 进行子集分页
      *
      * @param list       源数据
      * @param pageNumber 页码 从1开始
      * @param pageSize   分页大小
      * @param <T>        泛型方法
      * @return 对应分页的数据 List<T>
      */
     public static <T> List<T> page(List<T> list, int pageNumber, int pageSize) {
          List<T> pageList = new ArrayList<>();
          int currIdx = (pageNumber > 1 ? (pageNumber - 1) * pageSize : 0);
          for (int i = 0; i < pageSize && i < list.size() - currIdx; i++) {
               T listNew = list.get(currIdx + i);
               pageList.add(listNew);
          }
          return pageList;
     }

     /**
      * list去重
      * @param list 源数据
      * @return 去重后的list
      */
     public static List<?> removeDuplicates(List<?> list ){
          return list.stream().distinct().collect(Collectors.toList());
     }


}

