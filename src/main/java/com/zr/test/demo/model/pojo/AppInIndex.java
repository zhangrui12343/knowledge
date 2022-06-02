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
@ApiModel(value="首页app", description="")
public class AppInIndex implements Serializable {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "app id")
    private Long id;
    @ApiModelProperty(value = "logo id")
    private Long logo;

}
