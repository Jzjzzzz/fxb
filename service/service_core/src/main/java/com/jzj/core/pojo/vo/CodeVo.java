package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/9/24 0:18
 * @Version 1.0
 */

@Data
@ApiModel(description="腾讯云图形验证码")
public class CodeVo {
    @ApiModelProperty(value = "随机生成的参数")
    private String randStr;

    @ApiModelProperty(value = "令牌")
    private String ticket;
}
