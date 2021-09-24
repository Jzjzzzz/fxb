package com.jzj.core.utils;

import com.jzj.core.pojo.vo.CodeVo;
import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码工具类
 * @Author Jzj
 * @Date 2021/9/24 17:22
 * @Version 1.0
 */
public class CodeUtils {

    /**
     * 腾讯云图形验证码
     * @param codeVo 前端传递的codeModel
     * @return 结果值
     */
    public static Long tenCentCode(CodeVo codeVo, HttpServletRequest request) {
        try{
            String ip = requestUtils.getVisitorIp(request); //获取调用者真实IP
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(TenCentCodeProperties.SECRET_ID, TenCentCodeProperties.SECRET_KEY);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("captcha.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            CaptchaClient client = new CaptchaClient(cred, "", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeCaptchaResultRequest req = new DescribeCaptchaResultRequest();

            req.setCaptchaType(9L);
            req.setTicket(codeVo.getTicket()); //前端传来的ticket
            req.setUserIp(ip);
            req.setRandstr(codeVo.getRandStr()); //前端传来的randStr
            req.setCaptchaAppId(Long.valueOf(TenCentCodeProperties.CAPTCHA_APP_ID));
            req.setAppSecretKey(TenCentCodeProperties.APP_SECRET_KEY);
            // 返回的resp是一个DescribeCaptchaResultResponse的实例，与请求对象对应
            DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);
            Long captchaCode = resp.getCaptchaCode();
            return captchaCode;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
