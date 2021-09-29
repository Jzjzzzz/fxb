package com.jzj.core.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.query.PaperQuery;
import com.jzj.core.pojo.vo.*;
import com.jzj.core.service.EduPaperService;
import com.jzj.core.service.EduTestTopicRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试卷 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/front/core/paper")
@Api(tags = "前台试卷操作")
public class EduPaperController {
    @Resource
    private EduPaperService paperService;

    @Resource
    private EduTestTopicRecordsService testTopicRecordsService;

    @ApiOperation("首页获取热门试卷")
    @GetMapping("/getHotPaper")
    public R getHotPaper(){
        List<FrontPaperIndexVo> paperHotList = paperService.getHotPaperList();
        return R.ok().data("paperHotList",paperHotList);
    }

    @ApiOperation("试卷分页")
    @PostMapping("getFrontPaperList/{page}/{limit}")
    public R getFrontPaperList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) PaperQuery paperQuery){
        Map<String,Object> map = paperService.getFrontPaperList(new Page<>(page, limit),paperQuery);
        return R.ok().data("map",map);
    }

    @ApiOperation("根据ID查询试卷")
    @GetMapping("getPaperById/{id}")
    public R getPaperById(@PathVariable Long id){
        FrontPaperIndexVo paper = paperService.getPaperById(id);
        return R.ok().data("paper",paper);
    }

    @ApiOperation("根据ID查询试卷全部题目详情和试卷详情")
    @GetMapping("getPaperTopicById/{id}")
    public R getPaperTopicById(@PathVariable Long id){
        FrontPaperTopicVo model = paperService.getPaperTopicById(id);
        return R.ok().data("model",model);
    }

    @ApiOperation("自动改卷")
    @PostMapping("/automaticCorrecting")
    public R automaticCorrecting(@RequestBody FrontPaperFillAnswerVo fillAnswer){
        paperService.automaticGrading(fillAnswer);
        return R.ok().message("交卷成功");
    }

    @ApiOperation("手动批改问答题")
    @PutMapping("/manualCorrect/{topicId}/{result}")
    public R manualCorrect(@PathVariable Long topicId,@PathVariable int result){
        Boolean success = testTopicRecordsService.manualCorrect(topicId,result);
        if(success) return R.ok().message("批改成功");
        return R.error().message("批改失败");
    }



}

