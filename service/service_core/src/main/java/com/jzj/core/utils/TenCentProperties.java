package com.jzj.core.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Jzj
 * @Date 2021/9/8 18:16
 * @Version 1.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "tencent.npl")
public class TenCentProperties implements InitializingBean {

    private String secretId;
    private String secretKey;

    public static String SECRET_ID;
    public static String SECRET_KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_ID=secretId;
        SECRET_KEY=secretKey;

    }
}
