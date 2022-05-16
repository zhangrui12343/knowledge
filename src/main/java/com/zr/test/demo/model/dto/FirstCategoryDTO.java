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
@ApiModel(value="教育分类", description="")
public class FirstCategoryDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;
    @NotBlank(message = "类型名称不能为空")
    private String name;
    private String img;
    private Integer order;
    @NotNull(message = "类型不能为空")
    private List<String> category;
    @NotNull(message = "标签不能为空")
    private List<String> tag;

}
