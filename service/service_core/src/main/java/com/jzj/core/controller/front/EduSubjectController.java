package com.jzj.core.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  科目 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-08-10
 */
@RestController
@RequestMapping("/front/core/subject")
@Api(tags = "前台科目操作")
public class EduSubjectController {
    @Resource
    private EduSubjectService subjectService;


    @ApiOperation("查询科目列表")
    @GetMapping("/listSubject")
    public R listSubject(){
        List<EduSubject> list = subjectService.list(new QueryWrapper<EduSubject>().eq("status", 1).select("id","name"));
        return R.ok().data("subjectList",list);
    }
}

