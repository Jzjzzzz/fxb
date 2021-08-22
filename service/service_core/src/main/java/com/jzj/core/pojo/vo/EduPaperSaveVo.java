package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/8/18 19:35
 */
@Data
@ApiModel(description="试卷新增对象")
public class EduPaperSaveVo {
    @ApiModelProperty(value = "试卷表id")
    private Long id;

    @ApiModelProperty(value = "学科")
    private Long subjectId;

    @ApiModelProperty(value = "试卷名称")
    private String paperName;

    @ApiModelProperty(value = "试卷标题")
    private String paperTitle;

    @ApiModelProperty(value = "考试时长")
    private Integer suggestTime;

    @ApiModelProperty(value = "题目")
    private List<EduTopicListVo> topicListVoList;
}
