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
 * 
 * </p>
 *
 * @author Jzj
 * @since 2021-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduTestTopicRecords对象", description="")
public class EduTestTopicRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "考试记录题目表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "考试记录试卷id")
    private Long testPaperId;

    @ApiModelProperty(value = "题目题干")
    private String titleContent;

    @ApiModelProperty(value = "题目内容")
    private String content;

    @ApiModelProperty(value = "题目结果(0错误，1正确)")
    private Integer result;

    @ApiModelProperty(value = "得分")
    private Integer score;

    @ApiModelProperty(value = "难度")
    private Integer difficult;

    @ApiModelProperty(value = "解析")
    private String analyzes;

    @ApiModelProperty(value = "正确答案")
    private String correct;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "题型")
    private Integer questionId;

    @ApiModelProperty(value = "用户填写答案")
    private String userAnswer;

    @ApiModelProperty(value = "是否收藏该题")
    private Integer isCollect;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "状态")
    private Integer status;


}
