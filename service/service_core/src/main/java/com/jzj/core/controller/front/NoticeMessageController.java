package com.jzj.core.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.service.NoticeMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 公告信息 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-09-27
 */
@RestController
@RequestMapping("/front/core/message")
@Api(tags = "前台消息")
public class NoticeMessageController {

    @Resource
    private NoticeMessageService noticeMessageService;

    @ApiOperation("消息分页")
    @PostMapping("getFrontMessageList/{page}/{limit}")
    public R getFrontPaperList(@PathVariable long page, @PathVariable long limit){
        Map<String,Object> map = noticeMessageService.getFrontMessageList(new Page<>(page, limit));
        return R.ok().data("map",map);
    }
}

