package com.jzj.msm.client;

import com.jzj.msm.client.fallback.CoreUserInfoClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 核心服务方法调用
 * @Author Jzj
 * @Date 2021/11/10 16:24
 * @Version 1.0
 */
@FeignClient(value = "service-core",fallback = CoreUserInfoClientFallback.class)
public interface CoreUserInfoClient {
    @GetMapping("/ucenterservice/apimember/checkMobile/{mobile}")
    boolean checkMobile(@PathVariable String mobile);
}
