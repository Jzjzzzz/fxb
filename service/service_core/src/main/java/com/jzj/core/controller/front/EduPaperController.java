package com.jzj.core.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.EduPaper;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.query.PaperQuery;
import com.jzj.core.pojo.vo.EduPaperSaveVo;
import com.jzj.core.pojo.vo.FrontPaperIndexVo;
import com.jzj.core.pojo.vo.FrontPaperTopicVo;
import com.jzj.core.pojo.vo.WebConfigVo;
import com.jzj.core.service.EduPaperService;
import com.jzj.core.service.EduSubjectService;
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

    @ApiOperation("首页获取热门试卷")
    @GetMapping("/getHotPaper")
    public R getHotPaper(){
        List<FrontPaperIndexVo> paperHotList = paperService.getHotPaperList();
        return R.ok().data("paperHotList",paperHotList);
    }

    @ApiOperation("试卷分页")
    @PostMapping("getFrontPaperList/{page}/{limit}")
    public R getFrontPaperList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) PaperQuery paperQuery){
        Page<EduPaper> eduPaperPage = new Page<>(page, limit);
        Map<String,Object> map = paperService.getFrontPaperList(eduPaperPage,paperQuery);
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
}

