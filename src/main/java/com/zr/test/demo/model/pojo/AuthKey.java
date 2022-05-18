package com.zr.test.demo.model.pojo;

import lombok.Data;

/**
 * 解析用户的加密信息
 *
 * 第1个是roleId
 * 第2个是userId
 * 第3个是时间串
 * @date 2020/4/15 0015 9:50
 */
@Data
public class AuthKey {

     /** 角色id */
     private Integer roleId;
     /** 用户id */
     private Integer userId;
     /** 是否是学生 */
     private Integer student;
     /** 是否是内网 */
     private Integer intranet;
     /** 是否是系统用户 */
     private Integer system;

     private String time;

}

