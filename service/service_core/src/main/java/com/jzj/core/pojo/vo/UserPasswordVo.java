package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/9/17 16:28
 * @Version 1.0
 */
@Data
@ApiModel(description="用户修改密码")
public class UserPasswordVo {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "老密码")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
