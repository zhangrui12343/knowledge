package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserIdDTO {

    @ApiModelProperty(value = "id", example = "1")
    @NotNull(message="id不能为空")
    private Long id;

}
