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
 * 数据统计
 * </p>
 *
 * @author Jzj
 * @since 2021-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduStatistics对象", description="数据统计")
public class EduStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据统计表")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "网站会员总数")
    private Long userCount;

    @ApiModelProperty(value = "网站题目总数")
    private Long topicCount;

    @ApiModelProperty(value = "网站试卷总数")
    private Long paperCount;

    @ApiModelProperty(value = "网站考试总次数")
    private Long testTimes;


}
