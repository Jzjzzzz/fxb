package com.jzj.core.controller.front;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.NoticeMessage;
import com.jzj.core.pojo.query.MessageQuery;
import com.jzj.core.pojo.query.PaperQuery;
import com.jzj.core.service.NoticeMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

