package com.zr.test.demo.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * excel导入返回
 *
 * @author zr
 * @date 2020/5/27 0027 13:54
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "导入返回")
public class ExcelImportVO {

     @ApiModelProperty(value = "成功的总数")
     private Integer success;

     @ApiModelProperty(value = "失败的总数")
     private Integer failed;

     @ApiModelProperty(value = "错误的学籍号")
     private List<String> rows;

     @ApiModelProperty(value = "错误信息集合")
     @JsonProperty("error_info")
     private List<String> errorInfo;

}

