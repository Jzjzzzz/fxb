package com.jzj.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/8/10 3:57
 */
@Data
@ApiModel(description="科目搜索对象")
public class SubjectQuery {
    @ApiModelProperty(value = "科目名称")
    private String name;
}
