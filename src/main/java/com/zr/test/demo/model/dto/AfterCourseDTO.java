package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@ApiModel(value = "课后教育入参", description = "")
public class AfterCourseDTO implements Serializable {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Long img;
    @NotNull
    private List<Long> types;
    @NotNull
    private List<Long> categories;
    @NotNull
    private List<Long> tags;
    private List<Long> videos;
    private List<Long> docs;
    @NotBlank
    private String description;
    @NotNull
    private Integer excellent;
    @NotNull
    private Integer status;

}
