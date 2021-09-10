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
 * 考试记录
 * </p>
 *
 * @author Jzj
 * @since 2021-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduTestPaperRecords对象", description="考试记录")
public class EduTestPaperRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "考试记录表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "状态(0未批改,1已批改)")
    private Integer status;

    @ApiModelProperty(value = "试卷名称")
    private String paperName;

    @ApiModelProperty(value = "学科id")
    private Long subjectId;

    @ApiModelProperty(value = "系统判断得分")
    private Integer systemScore;

    @ApiModelProperty(value = "最终得分")
    private Integer userScore;

    @ApiModelProperty(value = "试卷总分")
    private Integer paperScore;

    @ApiModelProperty(value = "做对题数")
    private Integer questionCorrect;

    @ApiModelProperty(value = "题目总数")
    private Integer questionCount;

    @ApiModelProperty(value = "做题花费时间")
    private Integer doTime;

    @ApiModelProperty(value = "学生id")
    private Long userId;


}
