package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserLoginVO {

    @ApiModelProperty(value = "token", example = "1213")
    private String token;
    @ApiModelProperty(value = "姓名", example = "1213")
    private String username;

}
