package com.zr.test.demo.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileVO {

    @ApiModelProperty(value = "文件id", example = "1")
    private Long id;
    @ApiModelProperty(value = "文件相对路径", example = "路径经过 base64 + urlencode 编码后的字符串")
    private String path;
}
