package com.jzj.core.controller.front;


import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.UcenterMember;
import com.jzj.core.pojo.vo.LoginVo;
import com.jzj.core.pojo.vo.RegisterVo;
import com.jzj.core.service.UcenterMemberService;
import com.jzj.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-08-29
 */
@RestController
@RequestMapping("/ucenterservice/apimember")
@Api(tags = "前台用户操作")
public class UcenterMemberController {
    @Resource
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        Integer id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(id);
        return R.ok().data("userInfo",member);
    }

}

