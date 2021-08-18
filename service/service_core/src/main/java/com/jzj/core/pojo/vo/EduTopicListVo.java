package com.jzj.core.pojo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/8/13 14:51
 */
@Data
public class EduTopicListVo {

    @ApiModelProperty(value = "题目表id")
    private Long id;

    @ApiModelProperty(value = "科目名称")
    private String name;

    @ApiModelProperty(value = "题型id")
    private Integer questionId;

    @ApiModelProperty(value = "题型")
    private String questionName;

    @ApiModelProperty(value = "题目题干")
    private String titleContent;

    @ApiModelProperty(value = "题干，选项详情表id")
    private Long topicDetailsId;

    @ApiModelProperty(value = "题目分数")
    private Integer score;

    @ApiModelProperty(value = "题目难度")
    private Integer difficult;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
