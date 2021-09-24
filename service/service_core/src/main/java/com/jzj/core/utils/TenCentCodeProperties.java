package com.jzj.core.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Jzj
 * @Date 2021/9/24 17:26
 * @Version 1.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "tencent.code")
public class TenCentCodeProperties extends TenCentProperties{
    private String captchaAppId;
    private String appSecretKey;

    public static String CAPTCHA_APP_ID;
    public static String APP_SECRET_KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        CAPTCHA_APP_ID=captchaAppId;
        APP_SECRET_KEY=appSecretKey;

    }

}
