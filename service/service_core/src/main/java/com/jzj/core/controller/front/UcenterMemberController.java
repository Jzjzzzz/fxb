package com.jzj.core.controller.front;


import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.UcenterMember;
import com.jzj.core.pojo.vo.*;
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

    @ApiOperation(value = "会员修改个人资料")
    @PostMapping("updatePersonal")
    public R updatePersonal(@RequestBody UcenterMember ucenterMember){
        boolean result = memberService.updateById(ucenterMember);
        if(result) return R.ok().message("修改个人资料成功");
        return R.error().message("修改个人资料失败");
    }

    @ApiOperation(value = "会员修改密码")
    @PostMapping("updatePassword")
    public R updatePassword(@RequestBody UserPasswordVo userPasswordVo){
        boolean result = memberService.updatePasswordById(userPasswordVo);
        if(result) return R.ok().message("修改密码成功");
        return R.error().message("修改密码失败");
    }

    @ApiOperation("根据ID查询会员")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        FrontUserVo model = memberService.getByIdCount(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @ApiOperation("校验图形验证码")
    @PostMapping("/verificationCode")
    public R verificationCode(@RequestBody CodeVo codeVo,HttpServletRequest request){
        // Long result = memberService.verifyCode(codeVo,request); //验证码过期未使用到
        return R.ok().data("captchaCode",1);
    }

    @ApiOperation("校验手机号是否注册")
    @GetMapping("/checkMobile/{mobile}")
    public boolean checkMobile(@PathVariable String mobile){
        return memberService.checkMobile(mobile);
    }



}

