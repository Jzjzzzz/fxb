package com.jzj.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author Jzj
 * @Date 2021/9/16 15:49
 * @Version 1.0
 */
@Data
@ApiModel("后台首页统计数据")
public class IndexCountVo {
    @ApiModelProperty(value = "会员人数")
    private Integer userCount;

    @ApiModelProperty(value = "题目数量")
    private Integer topicCount;

    @ApiModelProperty(value = "试卷数量")
    private Integer paperCount;

    @ApiModelProperty(value = "考试数")
    private Integer TestTimes;

    @ApiModelProperty(value = "近七天日期")
    private List<String> recentDate;

    @ApiModelProperty(value = "题目增长数量")
    private List<Integer> topicNumber;

    @ApiModelProperty(value = "会员增长数量")
    private List<Integer> userNumber;
}
