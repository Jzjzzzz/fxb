package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/8/12 18:45
 */
@Data
@ApiModel(description="题目新增封装对象")
public class EduTopicSaveVo implements Serializable {

    @ApiModelProperty(value = "题目表id")
    private Long id;

    @ApiModelProperty(value = "题干，选项详情表id")
    private Long topicDetailsId;

    @ApiModelProperty(value = "学科id")
    private Long subjectId;

    @ApiModelProperty(value = "题目分数")
    private Integer score;

    @ApiModelProperty(value = "题目难度")
    private Integer difficult;

    @ApiModelProperty(value = "正确答案")
    private String correct;

    @ApiModelProperty(value = "题目内容")
    private String content;

    @ApiModelProperty(value = "题目题干")
    private String titleContent;

    @ApiModelProperty(value = "题目解析")
    private String analyzes;

    @ApiModelProperty(value = "题型")
    private Integer questionId;

}
