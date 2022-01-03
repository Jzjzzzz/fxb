package com.jzj.msm.client.fallback;

import com.jzj.msm.client.CoreUserInfoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Jzj
 * @Date 2021/11/18 18:15
 * @Version 1.0
 */
@Slf4j
@Service
public class CoreUserInfoClientFallback implements CoreUserInfoClient {
    @Override
    public boolean checkMobile(String mobile) {
        log.error("远程调用失败，服务熔断");
        return true;
    }
}
