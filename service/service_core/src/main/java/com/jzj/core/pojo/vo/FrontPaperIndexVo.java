package com.jzj.core.pojo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/9/2 11:22
 * @Version 1.0
 */
@Api(tags = "首页热门试卷模型")
@Data
public class FrontPaperIndexVo {

    @ApiModelProperty(value = "试卷表id")
    private Long id;

    @ApiModelProperty(value = "试卷总分")
    private Integer score;

    @ApiModelProperty(value = "学科")
    private String subjectName;

    @ApiModelProperty(value = "考试时长")
    private Integer suggestTime;

    @ApiModelProperty(value = "试卷名称")
    private String paperName;
}
