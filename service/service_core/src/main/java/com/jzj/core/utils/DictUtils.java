package com.jzj.core.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzj.core.pojo.entity.Dict;
import com.jzj.core.service.DictService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 字典工具类
 *
 * @author Jzj
 * @version 1.0
 * @date 2021/8/11 11:58
 */
@Component
public class DictUtils {
    private static final String FILE_PATH = "fxb:dict:";
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DictService dictService;

    private static RedisTemplate redis;

    private static DictService dict;

    @PostConstruct
    public void init(){
        redis = redisTemplate;
        dict = dictService;
    }


    /**
     * 根据父类id获取子节点列表
     * @return
     */
    public static List<Dict> getListByParentId(Long id){
        List<Dict> list=null;
        //判断是否在redis中有缓存数据
        list =(List<Dict>) redis.opsForValue().get(FILE_PATH+id);
        if(CollectionUtils.isNotEmpty(list)){
            return list;
        }
        //当redis中没有数据时，查询数据库

        list = dict.list(new QueryWrapper<Dict>().eq("parent_id", id).eq("status",1));
        //存入redis
        redis.opsForValue().set(FILE_PATH+id,list);
        return list;
    }


    /**
     * 移除redis数据
     * @param id
     * @return
     */
    public static boolean emptyRedis(Long id){
        Boolean result = redis.delete(FILE_PATH+id);
        return result;
    }
}
