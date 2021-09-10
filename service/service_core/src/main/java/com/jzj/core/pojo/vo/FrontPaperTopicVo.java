package com.jzj.core.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Jzj
 * @Date 2021/9/4 10:29
 * @Version 1.0
 */
@Data
@ApiModel("前台试卷详情信息和试卷题目(未带答案)")
public class FrontPaperTopicVo {

    @ApiModelProperty(value = "试卷表id")
    private Long id;

    @ApiModelProperty(value = "试卷总分")
    private Integer score;

    @ApiModelProperty(value = "学科")
    private Long subjectId;

    @ApiModelProperty(value = "题目总数")
    private Integer questionCount;

    @ApiModelProperty(value = "考试时长")
    private Integer suggestTime;

    @ApiModelProperty(value = "试卷名称")
    private String paperName;

    @ApiModelProperty(value = "试卷标题")
    private String paperTitle;

    @ApiModelProperty(value = "单选题数量")
    private Integer singleChoiceNumber;

    @ApiModelProperty(value = "多选题数量")
    private Integer multipleChoiceNumber;

    @ApiModelProperty(value = "判断题数量")
    private Integer judgeNumber;

    @ApiModelProperty(value = "简答题数量")
    private Integer shortAnswerNumber;

    @ApiModelProperty(value = "单选题集合")
    private List<FrontTopicVo> singleChoiceList;

    @ApiModelProperty(value = "多选题集合")
    private List<FrontTopicVo> multipleChoiceList;

    @ApiModelProperty(value = "判断题集合")
    private List<FrontTopicVo> judgeList;

    @ApiModelProperty(value = "简答题集合")
    private List<FrontTopicVo> shortAnswerList;
}
