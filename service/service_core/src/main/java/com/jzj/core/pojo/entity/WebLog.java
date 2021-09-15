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
 * 接口调用日志
 * </p>
 *
 * @author Jzj
 * @since 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="WebLog对象", description="接口调用日志")
public class WebLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "接口调用日志表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作用户id")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "uri")
    private String uri;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "请求类型")
    private String method;

    @ApiModelProperty(value = "请求ip")
    private String ip;

    @ApiModelProperty(value = "请求返回结果")
    private String result;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "消耗时间")
    private Integer spendTime;


}
