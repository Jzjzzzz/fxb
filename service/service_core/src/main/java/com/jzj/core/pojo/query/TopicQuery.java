package com.jzj.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/8/13 14:33
 */
@Data
@ApiModel(description="题目搜索对象")
public class TopicQuery {

    @ApiModelProperty(value = "学科id")
    private Long subjectId;

    @ApiModelProperty(value = "题型")
    private Integer questionId;

    @ApiModelProperty(value = "题目题干")
    private String titleContent;
}
