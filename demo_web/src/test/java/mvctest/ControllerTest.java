package mvctest;

import com.lin.redis.RedisIO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by pc on 2017-08-30.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/lys_spring_test.xml")
public class ControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    MockMvc mockMvc;
    @Autowired
    RedisIO redisIO;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

     @Test
    public void test() throws Exception {
        String actual = "{\"shopId\": \"4955\",\"webSite\": \"hz\"}";
        String expect = "{\"shopId\": \"4955\",\"webSite\": \"hz\"}";

       this.mockMvc
            .perform(post("/testDemo2.json")
                     .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
               .param("actual", actual)
               .param("expect", expect))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json;charset=UTF-8"))
               .andExpect(jsonPath("$.result").value("error"));
//               .andExpect(jsonPath("$[0].actual").value("4955"))
//               .andExpect(jsonPath("$[0].expected").value("4956"));
    }

    //测试键值put get
    @Test
    public void testRedisPut(){
        redisIO.put("lin","xx");
        String value = redisIO.get("lin");
        System.out.println(value);
    }
    //测试hash表set和get

    /**
     * HSET key field value

     将哈希表 key 中的域 field 的值设为 value 。

     如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。

     如果域 field 已经存在于哈希表中，旧值将被覆盖。

     可用版本：
     >= 2.0.0
     时间复杂度：
     O(1)
     返回值：
     如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
     如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
     */
    @Test
    public void testRedisHset(){
        Car car = new Car("宝马");
        redisIO.hset("hash_lin","1",car);
        System.out.println(redisIO.hget("hash_lin","1",Car.class).getName());
    }
    //测试hash all

    /**
     *
     * HGETALL key

  返回哈希表 key 中，所有的域和值。

  在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。

  可用版本：
  >= 2.0.0
  时间复杂度：
  O(N)， N 为哈希表的大小。
  返回值：
  以列表形式返回哈希表的域和域的值。
  若 key 不存在，返回空列表。

     */
    @Test
    public void testRedisHAll(){
        redisIO.hset("hash_lin","2","销售量突破一百哦");
        Jedis jedis = redisIO.getJedis();
        try {
            Map<String, String> hash_lin = jedis.hgetAll("hash_lin");
            System.out.println(hash_lin);
        } finally {
            redisIO.returnJedis(jedis);
        }
    }
    @Test
    public void testRedisHmset(){
        Map<String,String> map = new HashMap<>();
        map.put("秦始皇","嬴政");
        map.put("汉武帝","刘彻");
        System.out.println(redisIO.hmset("hash_lin2",map));
        redisIO.hmget("hash_lin2","秦始皇","汉武帝").forEach(System.out::println);

    }

}
