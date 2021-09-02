package com.jzj.core.controller.admin;


import com.jzj.core.service.UcenterMemberService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

}

