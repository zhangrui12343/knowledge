package com.zr.test.demo.model.dto;

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
@ApiModel(value = "根据关键字查询的入参", description = "")
public class KeywordDTO extends PageTDO implements Serializable {
    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "1:课程，2：课后,3:专题，4师研", example = "1")
    private Integer type;
    @NotBlank(message = "关键字不能为空")
    @ApiModelProperty(value = "关键字", example = "1213")
    private String keyword;

}
