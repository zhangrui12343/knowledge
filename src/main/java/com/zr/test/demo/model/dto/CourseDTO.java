package com.zr.test.demo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class CourseDTO implements Serializable {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Long xueduan;
    @NotNull
    private Long grade;
    @NotNull
    private Long subject;
    @NotNull
    private Long books;
    @NotNull
    private Long secondCategoryId;
    @NotNull
    private Long tagId;
    @NotBlank
    private String description;
    @NotBlank
    private String detail;
    @NotNull
    private Integer status;

    private String img;
    private String video;
    private String[] pdf;
}
