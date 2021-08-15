package com.jzj.core.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.Dict;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.entity.EduTopic;
import com.jzj.core.pojo.query.TopicQuery;
import com.jzj.core.pojo.vo.EduTopicMultipleVo;
import com.jzj.core.service.EduSubjectService;
import com.jzj.core.service.EduTopicService;
import com.jzj.core.utils.DictUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 题目 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/admin/core/topic")
@Api(tags = "题目后台管理")
public class AdminEduTopicController {

    @Resource
    private EduTopicService topicService;

    @Resource
    private EduSubjectService subjectService;

    @ApiOperation("字典数据")
    @GetMapping("/getByParentId")
    public R getByParentId(){
        List<Dict> list = DictUtils.getListByParentId(100L);
        return R.ok().data("list",list);
    }
    @ApiOperation("新增单选题")
    @PostMapping("/saveMultipleChoice")
    public R saveMultipleChoice(@RequestBody EduTopicMultipleVo multipleVo){
        boolean result = topicService.saveMultiple(multipleVo);
        if(result){
            return R.ok().message("新增单选题成功");
        }
        return R.error().message("新增失败");
    }
    @ApiOperation("题目列表数据")
    @PostMapping("/listByTopic/{page}/{limit}")
    public R listByTop(@PathVariable Long page,@PathVariable Long limit,@RequestBody(required = false) TopicQuery topicQuery){
        Page<EduTopic> pageParam = new Page<>(page, limit);
        //列表数据
        Map<String, Object> map = topicService.listPage(pageParam, topicQuery);
        //字典数据
        List<Dict> dictList = DictUtils.getListByParentId(100L);
        map.put("dictList",dictList);
        //科目数据
        List<EduSubject> subjectList = subjectService.list(new QueryWrapper<EduSubject>().eq("status", 1));
        map.put("subjectList",subjectList);
        return R.ok().data("map",map);
    }

    @ApiOperation("根据Id删除题目")
    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = topicService.removeTopicById(id);
        if(result){
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }
}

