package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Jzj
 * @Date 2021/9/1 23:12
 * @Version 1.0
 */
@Data
@ApiModel(description="前台轮播图")
public class SlideshowVo {

    @ApiModelProperty("轮播图列表")
    private List<String> SlideshowList;
}
