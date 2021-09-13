package com.jzj.core.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.EduPaper;
import com.jzj.core.pojo.entity.EduTestPaperRecords;
import com.jzj.core.service.EduTestPaperRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 考试记录 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/front/core/testRecords")
@Api(tags = "前台用户考试记录")
public class EduTestPaperRecordsController {

    @Resource
    private EduTestPaperRecordsService testPaperRecordsService;
    @ApiOperation("获取用户考试记录列表分页")
    @PostMapping("/getTestRecordList/{page}/{limit}/{userId}")
    public R getTestRecordList(@PathVariable long page,@PathVariable long limit,@PathVariable Long userId){
        Page<EduTestPaperRecords> recordsPage = new Page<>(page, limit);
        Map<String,Object> map = testPaperRecordsService.getTestRecordList(recordsPage,userId);
        return R.ok().data("map",map);
    }
}

