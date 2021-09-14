package com.jzj.core.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.query.SubjectQuery;
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
@RequestMapping("/admin/core/subject")
@Api(tags = "科目后台管理")
public class AdminEduSubjectController {
    @Resource
    private EduSubjectService subjectService;

    @ApiOperation("分页查询科目列表")
    @PostMapping("/listPage/{page}/{limit}")
    public R listByTop(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) SubjectQuery subjectQuery){
        Page<EduSubject> pageParam = new Page<>(page, limit);
        IPage<EduSubject> listPage = subjectService.listPage(pageParam, subjectQuery);
        return R.ok().data("listPage",listPage);
    }
    @ApiOperation("查询科目列表")
    @GetMapping("/listSubject")
    public R listSubject(){
        List<EduSubject> list = subjectService.list(new QueryWrapper<EduSubject>().eq("status", 1));
        return R.ok().data("list",list);
    }

    @ApiOperation("新增科目")
    @PostMapping("/save")
    public R saveSubject(@RequestBody EduSubject subject){
        boolean result = subjectService.saveSubject(subject);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @ApiOperation("修改科目")
    @PutMapping("/update")
    public R update(@RequestBody EduSubject subject){
        boolean result = subjectService.updateSubjectById(subject);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }
    @ApiOperation("根据ID查询科目")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        EduSubject model = subjectService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @ApiOperation("根据Id删除科目")
    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = subjectService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

}

