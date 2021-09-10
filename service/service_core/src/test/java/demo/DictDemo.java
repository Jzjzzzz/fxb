package demo;

import com.jzj.core.CoreApplication;
import com.jzj.core.pojo.entity.Dict;
import com.jzj.core.utils.DictUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/8/11 12:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class DictDemo {

    @Test
    public void test01(){
        List<Dict> list = DictUtils.getListByParentId(100L);
        System.out.println(list);
    }
    @Test
    public void test02(){
        boolean b = DictUtils.emptyRedis(100L);
        System.out.println(b);
    }

    public static void main(String[] args) {
        String [] a = {"b","B","C"};
        String [] b = {"A","B","C"};
        boolean b1 = Arrays.asList(a).containsAll(Arrays.asList(b));
        System.out.println(b1);
    }
}
