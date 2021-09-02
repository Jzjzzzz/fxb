package com.jzj.core.service;

import com.jzj.core.pojo.entity.WebConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.core.pojo.vo.SlideshowVo;
import com.jzj.core.pojo.vo.WebConfigVo;

/**
 * <p>
 * 网站配置 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-30
 */
public interface WebConfigService extends IService<WebConfig> {

    WebConfig getWebConfig();

    boolean editWebConfig(WebConfig webConfig);

    WebConfigVo getWebConfigRedis();


    SlideshowVo getSlideshowRedis();
}
