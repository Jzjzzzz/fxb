package com.jzj.core.controller.front;


import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.EduTestPaperRecords;
import com.jzj.core.pojo.entity.EduTestTopicRecords;
import com.jzj.core.service.EduTestPaperRecordsService;
import com.jzj.core.service.EduTestTopicRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/front/core/testTopic")
@Api(tags = "前台用户考试题目记录")
public class EduTestTopicRecordsController {
    @Resource
    private EduTestTopicRecordsService testTopicRecordsService;
    @Resource
    private EduTestPaperRecordsService testPaperRecordsService;

    @ApiOperation("获取用户考试记录详情")
    @GetMapping("/getTestTopicListById/{paperId}")
    public R getTestRecordList(@PathVariable Long paperId){
        List<EduTestTopicRecords> list = testTopicRecordsService.getTestTopicListById(paperId); //题目列表
        EduTestPaperRecords paper = testPaperRecordsService.getTestPaperById(paperId); //试卷详情
        return R.ok().data("list",list).data("paper",paper);
    }
    @ApiOperation("收藏错题")
        @PutMapping("/collectionError/{topicId}")
    public R collectionError(@PathVariable Long topicId){
        Boolean result = testTopicRecordsService.collectionTopic(topicId);
        if(result) return R.ok().data("result", true);
        return R.error().data("result", false);
    }

    @ApiOperation("取消收藏")
    @PutMapping("/cancelCollection/{topicId}")
    public R cancelCollection(@PathVariable Long topicId){
        Boolean result = testTopicRecordsService.cancelCollection(topicId);
        if(result) return R.ok().data("result", true);
        return R.error().data("result", false);

    }

    @ApiOperation("获取用户收藏错题库")
    @GetMapping("/getCollectionList/{userId}")
    public R getCollectionList(@PathVariable Long userId){
        List<EduTestTopicRecords> list = testTopicRecordsService.getCollectionList(userId); //题目列表
        return R.ok().data("list",list);
    }

}

