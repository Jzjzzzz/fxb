package com.jzj.msm.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Jzj
 * @Date 2021/11/9 18:58
 * @Version 1.0
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "tencent.sms")
public class TencentSmsProperties implements InitializingBean {
    private String secretId;
    private String secretKey;
    private String appid;
    private String sign;
    private String templateID;
    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static String APP_ID;
    public static String SIGN;
    public static String TEMPLATE_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_ID=secretId;
        SECRET_KEY=secretKey;
        APP_ID=appid;
        SIGN=sign;
        TEMPLATE_ID=templateID;
    }
}
