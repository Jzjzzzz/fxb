package com.jzj.core.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.R;
import com.jzj.commonutils.ResponseEnum;
import com.jzj.commonutils.ResultCode;
import com.jzj.core.pojo.entity.Dict;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.entity.EduTopic;
import com.jzj.core.pojo.query.TopicQuery;
import com.jzj.core.pojo.vo.EduTopicEditVo;
import com.jzj.core.pojo.vo.EduTopicSaveVo;
import com.jzj.core.service.EduSubjectService;
import com.jzj.core.service.EduTopicService;
import com.jzj.core.utils.DictUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
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
    @ApiOperation("新增题目")
    @PostMapping("/saveTopic")
    public R saveTopic(@RequestBody EduTopicSaveVo topicSaveVo){
        boolean result = topicService.saveTopic(topicSaveVo);
        if(result) return R.ok().message("新增题目成功");
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
        if(result) return R.ok().message("删除题目成功");
        return R.error().message("删除题目失败");
    }
    @ApiOperation("根据ID查询题目和详情信息")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        EduTopicEditVo model = topicService.getByIdTopic(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }
    @ApiOperation("修改题目")
    @PutMapping("/updateTopicById")
    public R updateTopicById(@RequestBody EduTopicSaveVo topicSaveVo){
        boolean result = topicService.updateTopic(topicSaveVo);
        if(result) return R.ok().message("修改题目成功");
        return R.error().message("修改题目失败");
    }
    @ApiOperation("根据课程id查询题目列表")
    @GetMapping("/getTopicListBySubjectId/{id}/{page}/{limit}")
    public R getTopicListBySubjectId(@PathVariable Long id,@PathVariable Long page,@PathVariable Long limit){
        Page<EduTopic> pageParam = new Page<>(page, limit);
        Map<String, Object> map = topicService.getTopicListById(id,pageParam);
        return R.ok().data("map",map);
    }

    @ApiOperation("Excel批量导入题目")
    @PostMapping("/import")
    public R batchImport(
            @ApiParam(value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            topicService.importData(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            throw new BusinessException(ResultCode.ERROR, e.getMessage());
        }
    }
}

