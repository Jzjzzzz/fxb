package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Jzj
 * @Date 2021/9/7 17:00
 * @Version 1.0
 */
@Data
@ApiModel("题目详情（带答案）")
public class FrontPaperAnswerVo {
    @ApiModelProperty(value = "题目表id")
    private Long id;

    @ApiModelProperty(value = "题目分数")
    private Integer score;

    @ApiModelProperty(value = "题目难度")
    private Integer difficult;

    @ApiModelProperty(value = "题型")
    private Integer questionId;

    @ApiModelProperty(value = "题目内容")
    private String content;

    @ApiModelProperty(value = "题目题干")
    private String titleContent;

    @ApiModelProperty(value = "题目解析")
    private String analyzes;

    @ApiModelProperty(value = "题目答案")
    private String correct;

}
