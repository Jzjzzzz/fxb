package com.jzj.msm.controller;

import com.jzj.commonutils.Assert;
import com.jzj.commonutils.R;
import com.jzj.commonutils.ResponseEnum;
import com.jzj.msm.async.AsyncTask;
import com.jzj.msm.client.CoreUserInfoClient;
import com.jzj.util.RandomUtils;
import com.jzj.util.RegexValidateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Jzj
 * @Date 2021/11/9 19:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/sms")
@Api(tags = "短信管理")
public class ApiSmsController {
    @Resource
    private AsyncTask asyncTask;
    @Resource
    private CoreUserInfoClient coreUserInfoClient;

    @ApiOperation("获取验证码")
    @GetMapping("/send/{mobile}")
    public R send(@PathVariable String mobile){
        //手机号码不能为空
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        //是否是合法的手机号码
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile),ResponseEnum.MOBILE_ERROR);
        //手机号是否注册
        boolean result = coreUserInfoClient.checkMobile(mobile);
        Assert.isTrue(!result, ResponseEnum.MOBILE_EXIST_ERROR);
        String code= RandomUtils.getFourBitRandom();
        asyncTask.send(mobile,code);
        return R.ok().message("短信发送成功");
    }
}
