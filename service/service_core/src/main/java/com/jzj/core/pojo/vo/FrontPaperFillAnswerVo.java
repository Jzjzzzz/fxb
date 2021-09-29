package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author Jzj
 * @Date 2021/9/7 9:55
 * @Version 1.0
 */
@Data
@ApiModel("前台试卷填写数据")
public class FrontPaperFillAnswerVo {
    @ApiModelProperty(value = "试卷表id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用时")
    private Integer doTime;

    @ApiModelProperty(value = "是否使用问答题自动批改")
    private Integer isAuto;

    @ApiModelProperty(value = "单选题用户填写的答案")
    private List<String> singleRadio;

    @ApiModelProperty(value = "多选题用户填写的答案")
    private String [][] multipleRadio;

    @ApiModelProperty(value = "判断题用户填写的答案")
    private List<String> judgeRadio;

    @ApiModelProperty(value = "问答题用户填写的答案")
    private List<String> essayQuestion;

    @ApiModelProperty(value = "单选题id列表")
    private List<Long> singleIds;

    @ApiModelProperty(value = "多选题id列表")
    private List<Long> multipleIds;

    @ApiModelProperty(value = "判断题id列表")
    private List<Long> judgeIds;

    @ApiModelProperty(value = "问答题id列表")
    private List<Long> essayIds;


}
