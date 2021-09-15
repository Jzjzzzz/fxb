package com.jzj.core.service.impl;

import com.jzj.core.pojo.entity.WebConfig;
import com.jzj.core.mapper.WebConfigMapper;
import com.jzj.core.pojo.vo.SlideshowVo;
import com.jzj.core.pojo.vo.WebConfigVo;
import com.jzj.core.service.WebConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

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
            redisTemplate.delete(FILE_PATH + "slideshow");
            return true;
        }
        return false;
    }

    @Override
    public WebConfigVo getWebConfigRedis() {

        WebConfig webConfig;
        //判断是否在redis中有缓存数据
        WebConfigVo webConfigs = (WebConfigVo)redisTemplate.opsForValue().get(FILE_PATH + "config");
        if(webConfigs!=null){
            return webConfigs;
        }
        //当redis中没有数据时，查询数据库
        webConfig = baseMapper.selectOne(null);
        WebConfigVo webConfigVo = new WebConfigVo();
        //赋值
        BeanUtils.copyProperties(webConfig,webConfigVo);
        //存入redis
        redisTemplate.opsForValue().set(FILE_PATH+"config",webConfigVo);
        return webConfigVo;

    }

    @Override
    public SlideshowVo getSlideshowRedis() {
        SlideshowVo slideshowVo = new SlideshowVo();
        WebConfig webConfig =null;
        //判断是否在redis中有缓存数据
        SlideshowVo slideshowList =(SlideshowVo) redisTemplate.opsForValue().get(FILE_PATH + "slideshow");
        if(slideshowList!=null){
            System.out.println("从redis中获取数据");
            return slideshowList;
        }
        //当redis中没有数据时，查询数据库
        webConfig = baseMapper.selectOne(null);
        ArrayList<String> list = new ArrayList<>();
        //组装轮播图数据
        list.add(webConfig.getSlideshowOne());
        list.add(webConfig.getSlideshowTwo());
        list.add(webConfig.getSlideshowThree());
        slideshowVo.setSlideshowList(list);
        //存入redis
        redisTemplate.opsForValue().set(FILE_PATH+"slideshow",slideshowVo);

        return slideshowVo;
    }


}
