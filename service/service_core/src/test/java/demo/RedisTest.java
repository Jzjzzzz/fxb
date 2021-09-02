package demo;

import com.jzj.core.CoreApplication;
import com.jzj.core.pojo.entity.Dict;
import com.jzj.core.pojo.entity.WebConfig;
import com.jzj.core.service.DictService;
import com.jzj.core.service.WebConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/8/9 5:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class RedisTest {
    @Resource
    private DictService dictService;
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private WebConfigService webConfigService;
    /**
     * list
     */
    @Test
    public void test01(){
        List<Dict> list = dictService.list(null);
        redisTemplate.opsForList().rightPushAll("list2",list);
        System.out.println(list);
    }
    @Test
    public void test02(){
        List<Dict> list = (List<Dict>)redisTemplate.opsForValue().get("list");
        for (Dict dict : list) {
            String name = dict.getName();
            System.out.println(name);
        }
    }

    /**
     * list
     */
    @Test
    public void test03(){
        List<Dict> list = redisTemplate.opsForList().range("list2", 0, -1);
        for (Dict dict : list) {
            String name = dict.getName();
            System.out.println(name);
        }

    }
    /**
     * hash
     */


}
