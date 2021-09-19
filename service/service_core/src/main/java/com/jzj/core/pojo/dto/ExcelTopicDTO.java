package com.jzj.core.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author Jzj
 * @Date 2021/9/19 15:31
 * @Version 1.0
 */
@Data
public class ExcelTopicDTO {
    @ExcelProperty("titleContent")
    private String titleContent;

    @ExcelProperty("correct")
    private String correct;

    @ExcelProperty("contentA")
    private String contentA;

    @ExcelProperty("contentB")
    private String contentB;

    @ExcelProperty("contentC")
    private String contentC;

    @ExcelProperty("contentD")
    private String contentD;

    @ExcelProperty("contentE")
    private String contentE;

    @ExcelProperty("analyzes")
    private String analyzes;
}
