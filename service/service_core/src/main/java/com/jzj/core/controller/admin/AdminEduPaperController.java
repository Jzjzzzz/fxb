package com.jzj.core.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.EduPaper;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.query.PaperQuery;
import com.jzj.core.pojo.vo.EduPaperSaveVo;
import com.jzj.core.pojo.vo.EduTopicEditVo;
import com.jzj.core.pojo.vo.EduTopicSaveVo;
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
@RequestMapping("/admin/core/paper")
@Api(tags = "试卷后台管理")
public class AdminEduPaperController {
    @Resource
    private EduPaperService paperService;

    @Resource
    private EduSubjectService subjectService;

    @ApiOperation("新增试卷")
    @PostMapping("/savePaper")
    public R savePaper(@RequestBody EduPaperSaveVo paperSaveVo){
        boolean result = paperService.savePaperOrTopic(paperSaveVo);
        if(result) return R.ok().message("新增试卷成功");
        return R.error().message("新增失败");
    }

    @ApiOperation("修改试卷")
    @PutMapping("/updatePaperById")
    public R updateTopicById(@RequestBody EduPaperSaveVo paperSaveVo){
        boolean result = paperService.updatePaper(paperSaveVo);
        if(result) return R.ok().message("修改试卷成功");
        return R.error().message("修改试卷失败");
    }

    @ApiOperation("分页查询科目列表")
    @PostMapping("/listPage/{page}/{limit}")
    public R listByPage(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) PaperQuery paperQuery){
        //列表数据
        Page<EduPaper> pageParam = new Page<>(page, limit);
        Map<String,Object> map = paperService.listPage(pageParam, paperQuery);
        //科目数据
        List<EduSubject> subjectList = subjectService.list(new QueryWrapper<EduSubject>().eq("status", 1));
        map.put("subjectList",subjectList);
        return R.ok().data("map",map);
    }

    @ApiOperation("根据Id删除试卷")
    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = paperService.removePaperById(id);
        if(result) return R.ok().message("删除试卷成功");
        return R.error().message("删除试卷失败");
    }
    @ApiOperation("根据ID获取试卷详情")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        EduPaperSaveVo model = paperService.getByIdPaper(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

}

