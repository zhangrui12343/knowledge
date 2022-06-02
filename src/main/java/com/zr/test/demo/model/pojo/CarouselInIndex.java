package com.zr.test.demo.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="首页轮播图", description="")
public class CarouselInIndex implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "轮播图文件id")
    private Long img;
    @ApiModelProperty(value = "轮播图连接")
    private String url;

}
