package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/9/5 15:49
 * @Version 1.0
 */
@Data
@ApiModel("前台试卷详情信息和试卷题目")
public class FrontTopicVo {
    @ApiModelProperty(value = "题目表id")
    private Long id;

    @ApiModelProperty(value = "题目分数")
    private Integer score;

    @ApiModelProperty(value = "题目难度")
    private Integer difficult;

    @ApiModelProperty(value = "题目内容")
    private String content;

    @ApiModelProperty(value = "题目题干")
    private String titleContent;

    @ApiModelProperty(value = "题型")
    private Integer questionId;

    @ApiModelProperty(value = "题型名")
    private String questionName;


}
