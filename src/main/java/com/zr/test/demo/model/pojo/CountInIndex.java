package com.zr.test.demo.model.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zr
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="首页统计", description="")
public class CountInIndex implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "今日更新数")
    private Integer today;
    @ApiModelProperty(value = "昨日更新数")
    private Integer yesterday;
    @ApiModelProperty(value = "会员总数")
    private Integer vips;
    @ApiModelProperty(value = "资源总数")
    private Integer fileTotal;
    @ApiModelProperty(value = "资料增量(GB)")
    private Double fileAdd;
    @ApiModelProperty(value = "学校数")
    private Integer schools;
}
