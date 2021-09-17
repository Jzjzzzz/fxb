package com.jzj.core.pojo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author Jzj
 * @Date 2021/9/18 3:04
 * @Version 1.0
 */
@Data
@ApiModel(description="用户个人资料")
public class FrontUserVo {

    @ApiModelProperty(value = "会员id")
    private Long id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别 1 女，2 男")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "试卷统计")
    private List<UserCountVo> userCountVoList;

}
