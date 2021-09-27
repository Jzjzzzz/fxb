package com.jzj.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.entity.NoticeMessage;
import com.jzj.core.pojo.query.MessageQuery;
import com.jzj.core.pojo.query.SubjectQuery;
import com.jzj.core.service.NoticeMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 公告信息 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-09-27
 */
@RestController
@RequestMapping("/admin/core/message")
@Api(tags = "消息后台管理")
public class AdminNoticeMessageController {

    @Resource
    private NoticeMessageService noticeMessageService;

    @ApiOperation("管理员新增公告")
    @PostMapping("/save")
    public R save(@RequestBody NoticeMessage noticeMessage){
        boolean result = noticeMessageService.saveMessage(noticeMessage);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @ApiOperation("分页查询公告列表")
    @PostMapping("/listPage/{page}/{limit}")
    public R listByTop(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) MessageQuery messageQuery){
        IPage<NoticeMessage> listPage = noticeMessageService.listPage(new Page<>(page, limit), messageQuery);
        return R.ok().data("listPage",listPage);
    }

    @ApiOperation("根据Id删除消息")
    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = noticeMessageService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

    @ApiOperation("修改消息")
    @PutMapping("/update")
    public R update(@RequestBody NoticeMessage noticeMessage){
        boolean result = noticeMessageService.updateById(noticeMessage);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }

    @ApiOperation("根据ID查询消息")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        NoticeMessage model = noticeMessageService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }
}

