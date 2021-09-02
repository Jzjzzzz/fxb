package com.jzj.core.pojo.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/9/1 22:52
 * @Version 1.0
 */
@Data
@ApiModel(description="前台网站配置")
public class WebConfigVo {

    @ApiModelProperty(value = "网站logo")
    private String logo;

    @ApiModelProperty(value = "网站名称")
    private String name;

    @ApiModelProperty(value = "网站介绍")
    private String summary;

    @ApiModelProperty(value = "网站关键字")
    private String keyword;

    @ApiModelProperty(value = "网站作者")
    private String author;

    @ApiModelProperty(value = "网站备案号")
    private String recordNum;

    @ApiModelProperty(value = "github地址")
    private String github;

    @ApiModelProperty(value = "码云地址")
    private String gitee;

    @ApiModelProperty(value = "QQ号")
    private String qqNumber;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "微信号")
    private String weChat;

}
