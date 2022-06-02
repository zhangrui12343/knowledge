package com.zr.test.demo.model.vo;

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
@ApiModel(value="Carousel对象", description="")
public class CarouselVO implements Serializable {

    private static final long serialVersionUID=1L;


    private Integer id;
    @ApiModelProperty(value = "轮播图文件")
    private FileVO img;
    @ApiModelProperty(value = "轮播图连接")
    private String url;
    @ApiModelProperty(value = "权重")
    private Integer orderr;
    @ApiModelProperty(value = "上下线")
    private Integer status;
}
