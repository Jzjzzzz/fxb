package com.jzj.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/9/27 16:26
 * @Version 1.0
 */
@Data
@ApiModel(description="消息搜索对象")
public class MessageQuery {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "类型")
    private int type;

}
