package com.jzj.core.controller.admin;

import com.jzj.commonutils.R;
import com.jzj.core.pojo.vo.IndexCountVo;
import com.jzj.core.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 后台首页 前端控制器
 * </p>
 *
 * @Author Jzj
 * @Date 2021/9/16 14:51
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/core/index")
@Api(tags = "首页统计后台管理")
public class AdminIndexController {

    @Resource
    private IndexService indexService;

    @ApiOperation("获取首页统计数据")
    @GetMapping("/getCount")
    public R getCount(){
        IndexCountVo model = indexService.getCount();
        return R.ok().data("model",model);
    }
}
