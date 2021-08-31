package com.jzj.core.controller.admin;


import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.WebConfig;
import com.jzj.core.service.WebConfigService;
import com.jzj.core.utils.DictUtils;
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
@RequestMapping("/admin/core/config")
@Api(tags = "网站设置后台管理")
public class AdminWebConfigController {
    @Resource
    private WebConfigService webConfigService;

    @ApiOperation("获取网站配置")
    @GetMapping("/getWebConfig")
    public R getWebConfig(){
        WebConfig webConfig = webConfigService.getWebConfig();
        return R.ok().data("webConfig",webConfig);
    }

    @ApiOperation("修改网站配置")
    @PutMapping("/editWebConfig")
    public R editWebConfig(@RequestBody WebConfig webConfig){
        boolean result = webConfigService.editWebConfig(webConfig);
        if(result){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }


}

