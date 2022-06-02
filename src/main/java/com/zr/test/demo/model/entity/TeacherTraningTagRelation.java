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
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TeacherTraningTagRelation对象", description="")
public class TeacherTraningTagRelation implements Serializable {

    private static final long serialVersionUID=1L;

    private Long afterCourseId;

    private Long tagId;

    public TeacherTraningTagRelation(Long afterCourseId, Long tagId) {
        this.afterCourseId = afterCourseId;
        this.tagId = tagId;
    }
}
