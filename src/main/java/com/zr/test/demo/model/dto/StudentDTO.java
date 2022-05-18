package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentDTO extends PageTDO{

    @ApiModelProperty(value = "是否启用", example = "1")
    private Integer status;
    @ApiModelProperty(value = "学籍号", example = "1")
    private String studentNo;
    @ApiModelProperty(value = "学生姓名", example = "1")
    private String name;
}

