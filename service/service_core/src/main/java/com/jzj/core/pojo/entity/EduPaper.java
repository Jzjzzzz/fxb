package com.jzj.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 试卷
 * </p>
 *
 * @author Jzj
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduPaper对象", description="试卷")
public class EduPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "试卷表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "状态")
    private Integer status;

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


}
