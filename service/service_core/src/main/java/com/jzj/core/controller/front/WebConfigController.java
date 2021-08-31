package com.jzj.core.controller.front;


import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.WebConfig;
import com.jzj.core.service.WebConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 网站配置 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-08-30
 */
@RestController
@RequestMapping("/front/core/config")
@Api(tags = "网站设置")
public class WebConfigController {
    @Resource
    private WebConfigService webConfigService;

    @ApiOperation("前台获取网站配置")
    @GetMapping("/getWebConfigRedis")
    public R getWebConfig(){
        WebConfig webConfig = webConfigService.getWebConfigRedis();
        return R.ok().data("webConfig",webConfig);
    }
}

