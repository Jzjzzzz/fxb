package com.jzj.core.pojo.query;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/8/19 18:43
 */
@Data
@ApiModel(description="试卷搜索对象")
public class PaperQuery {

    @ApiModelProperty(value = "试卷名称")
    private String paperName;

    @ApiModelProperty(value = "学科id")
    private Long subjectId;

    @ApiModelProperty(value = "创建时间")
    private String gmtCreate;
}
