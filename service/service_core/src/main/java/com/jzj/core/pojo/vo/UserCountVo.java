package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Jzj
 * @Date 2021/9/18 3:07
 * @Version 1.0
 */
@Data
@ApiModel(description="用户统计数据")
public class UserCountVo {

    @ApiModelProperty(value = "次数")
    private int value;

    @ApiModelProperty(value = "试卷名")
    private String name;


}
