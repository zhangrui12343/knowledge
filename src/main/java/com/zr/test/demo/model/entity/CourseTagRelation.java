package com.zr.test.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 * @since 2022-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CourseTagRelation对象", description="")
public class CourseTagRelation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "course_id", type = IdType.AUTO)
    private Long courseId;

    private Long typeId;

    public CourseTagRelation(Long courseId, Long typeId) {
        this.courseId = courseId;
        this.typeId = typeId;
    }
}
