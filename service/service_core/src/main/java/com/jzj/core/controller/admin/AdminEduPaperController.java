package com.jzj.core.controller.admin;


import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.EduPaper;
import com.jzj.core.pojo.vo.EduPaperSaveVo;
import com.jzj.core.pojo.vo.EduTopicSaveVo;
import com.jzj.core.service.EduPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 试卷 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/admin/core/paper")
@Api(tags = "试卷后台管理")
public class AdminEduPaperController {
    @Resource
    private EduPaperService paperService;

    @ApiOperation("新增试卷")
    @PostMapping("/savePaper")
    public R savePaper(@RequestBody EduPaperSaveVo paperSaveVo){
        boolean result = paperService.savePaperOrTopic(paperSaveVo);
        if(result) return R.ok().message("新增试卷成功");
        return R.error().message("新增失败");
    }

}

