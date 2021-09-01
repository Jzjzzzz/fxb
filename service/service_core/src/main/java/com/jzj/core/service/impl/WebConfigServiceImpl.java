package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzj.core.pojo.entity.WebConfig;
import com.jzj.core.mapper.WebConfigMapper;
import com.jzj.core.service.WebConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.servicebase.RedisConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网站配置 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-30
 */
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService {

    private static final String FILE_PATH = "fxb:web_config:";


    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public WebConfig getWebConfig() {
        WebConfig webConfig = baseMapper.selectOne(null);
        if(webConfig==null){
            WebConfig model= new WebConfig();
            baseMapper.insert(model);
            return baseMapper.selectOne(null);
        }
        return webConfig;
    }

    @Override
    public boolean editWebConfig(WebConfig webConfig) {
        int sum = baseMapper.updateById(webConfig);
        if(sum>=1){
            //清空redis缓存
            redisTemplate.delete(FILE_PATH + "config");
            return true;
        }
        return false;
    }

    @Override
    public WebConfig getWebConfigRedis() {
        WebConfig webConfig =null;
        //判断是否在redis中有缓存数据
        webConfig = (WebConfig)redisTemplate.opsForValue().get(FILE_PATH + "config");
        if(webConfig!=null){
            return webConfig;
        }
        //当redis中没有数据时，查询数据库
        webConfig = baseMapper.selectOne(null);
        //存入redis
        redisTemplate.opsForValue().set(FILE_PATH+"config",webConfig);
        return webConfig;

    }


}
