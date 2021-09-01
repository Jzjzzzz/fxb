package com.jzj.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网站配置
 * </p>
 *
 * @author Jzj
 * @since 2021-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="WebConfig对象", description="网站配置")
public class WebConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "网站设置表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

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

    @ApiModelProperty(value = "轮播图1")
    private String slideshowOne;

    @ApiModelProperty(value = "轮播图2")
    private String slideshowTwo;

    @ApiModelProperty(value = "轮播图3")
    private String slideshowThree;


}
