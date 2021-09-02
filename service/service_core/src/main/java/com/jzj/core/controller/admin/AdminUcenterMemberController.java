package com.jzj.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.UcenterMember;
import com.jzj.core.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-08-29
 */
@RestController
@RequestMapping("/admin/core/user")
@Api(tags = "用户操作后台管理")
public class AdminUcenterMemberController {
    @Resource
    private UcenterMemberService memberService;

    @ApiOperation("/获取用户信息列表")
    @PostMapping("/listPage/{page}/{limit}")
    public R getUserList(@PathVariable Long page,@PathVariable Long limit){
        Page<UcenterMember> memberPage = new Page<>(page, limit);
        IPage<Map<String, Object>> maps = memberService.pageMaps(memberPage, null);
        return R.ok().data("list",maps);
    }
}

