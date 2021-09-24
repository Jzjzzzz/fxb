package com.jzj.core.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求体工具类
 * @Author Jzj
 * @Date 2021/9/24 17:52
 * @Version 1.0
 */
public class requestUtils {

    public static String getVisitorIp(HttpServletRequest request){
        String ip = request.getHeader("X-Real-IP");
                 if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                         return ip;
                     }
                 ip = request.getHeader("X-Forwarded-For");
                 if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                         // 多次反向代理后会有多个IP值，第一个为真实IP。
                         int index = ip.indexOf(',');
                         if (index != -1) {
                                 return ip.substring(0, index);
                             } else {
                                 return ip;
                             }
                     } else {
                         return request.getRemoteAddr();
                     }
    }
}
