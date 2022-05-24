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
 * @since 2022-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AfterCourseCategoryRelation对象", description="")
public class AfterCourseCategoryRelation implements Serializable {

    private static final long serialVersionUID=1L;

    private Long afterCourseId;

    private Long categoryId;

    public AfterCourseCategoryRelation(Long afterCourseId, Long categoryId) {
        this.afterCourseId = afterCourseId;
        this.categoryId = categoryId;
    }
}
