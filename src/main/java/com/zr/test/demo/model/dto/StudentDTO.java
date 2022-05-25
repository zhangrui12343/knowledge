package com.zr.test.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentDTO extends PageTDO{

    @ApiModelProperty(value = "学生姓名或者学籍号", example = "1")
    private String name;
}

