package com.jzj.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.UcenterMember;
import com.jzj.core.pojo.entity.WebLog;
import com.jzj.core.pojo.query.UserQuery;
import com.jzj.core.service.WebLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 接口调用日志 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-09-15
 */
@RestController
@RequestMapping("/admin/core/log")
@Api(tags = "后台日志管理")
public class AdminWebLogController {

    @Resource
    private WebLogService webLogService;

    @ApiOperation("获取日志信息列表")
    @GetMapping("/listPage/{page}/{limit}")
    public R listPage(@PathVariable Long page, @PathVariable Long limit){
        Page<WebLog> webLogPage = new Page<>(page, limit);
        IPage<WebLog> listPage = webLogService.page(webLogPage,null);
        return R.ok().data("list",listPage);
    }

}

