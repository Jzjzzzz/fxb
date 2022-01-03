package com.jzj.msm.async;

import com.jzj.commonutils.Assert;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.ResponseEnum;
import com.jzj.msm.utils.TencentSmsProperties;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 发送短信异步方法
 * @Author Jzj
 * @Date 2021/11/10 15:42
 * @Version 1.0
 */
@Component
@Slf4j
public class AsyncTask {
    @Resource
    private RedisTemplate redisTemplate;

    @Async
    public void send(String mobile,String code) {
        String jointMobile="+86"+mobile;
        String secretId = TencentSmsProperties.SECRET_ID;
        String secretKey= TencentSmsProperties.SECRET_KEY;
        //短信应用 ID
        String appid = TencentSmsProperties.APP_ID;
        //短信签名内容
        String sign = TencentSmsProperties.SIGN;
        //短信模板 ID
        String templateID = TencentSmsProperties.TEMPLATE_ID;
        //+86为国家码，182********为手机号，最多不要超过200个手机号
        String[] phoneNumbers = { jointMobile};
        //模板参数: 若无模板参数，则设置为空
        String[] templateParams = { code };//对应模板中{1}
        try {
            //必要步骤： 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
            Credential cred = new Credential(secretId , secretKey);

            ClientProfile clientProfile = new ClientProfile();
            //SDK 默认用 TC3-HMAC-SHA256 进行签名 非必要请不要修改该字段
            clientProfile.setSignMethod("HmacSHA256");
            // 实例化 SMS 的 client 对象 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量
            SmsClient client = new SmsClient(cred, "", clientProfile);
            //实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数 您可以直接查询 SDK 源码确定接口有哪些属性可以设置
            SendSmsRequest req = new SendSmsRequest();

            // 短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666
            req.setSmsSdkAppid(appid);

            // 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息
            req.setSign(sign);

            //短信模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID
            req.setTemplateID(templateID);

            //下发手机号码，采用 e.164 标准，+[国家或地区码][手机号] 例如+8613711112222
            req.setPhoneNumberSet(phoneNumbers);

            req.setTemplateParamSet(templateParams);

            // 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个SendSmsResponse 类的实例，与请求对象对应
            SendSmsResponse res = client.SendSms(req);
            //获取响应结果
            SendStatus[] sendStatusSet = res.getSendStatusSet();
            log.info("短信发送返回的响应："+sendStatusSet);
            Assert.notEquals("LimitExceeded.PhoneNumberThirtySecondLimit",sendStatusSet[0].getCode(), ResponseEnum.TENGXUN_SMS_LIMIT_CONTROL_ERROR);
            Assert.equals("Ok",sendStatusSet[0].getCode(),ResponseEnum.TENGXUN_SMS_ERROR);
            //将验证码存入redis中
            redisTemplate.opsForValue().set("fxb:sms:code:"+mobile,code,5, TimeUnit.MINUTES);
        } catch (TencentCloudSDKException e) {
            log.error("腾讯云短信发送sdk调用失败："+e.getErrorCode()+","+e.getMessage());
            throw new BusinessException(ResponseEnum.TENGXUN_SMS_ERROR,e);
        }
    }
}
