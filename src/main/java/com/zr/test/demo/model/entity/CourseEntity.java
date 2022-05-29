package com.zr.test.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zr
 * @since 2022-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Course对象", description="")
@TableName(value = "course")
public class CourseEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String teacher;
    private Long img;

    private String category;

    private String courseTypeId;

    private String app;
    private String description;

    private Long learningTask;
    private Long homework;
    private Long video;

    private Integer excellent;
    private Integer status;

    private Date time;

    private Integer count;
}
