package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysLoginVO {

    @ApiModelProperty(value = "token", example = "1213")
    private String token;

    @ApiModelProperty(value = "所属权限", example = "1,2,3")
    private List<Integer> menu;

}
