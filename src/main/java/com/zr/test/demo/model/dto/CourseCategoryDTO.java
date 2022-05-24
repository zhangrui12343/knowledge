package com.zr.test.demo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
public class CourseCategoryDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;
    @NotNull
    @ApiModelProperty(value = "父级id", example = "1213")
    private Long pid;
    @NotBlank
    @ApiModelProperty(value = "分类名称", example = "1213")
    private String name;

}
