package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pc on 2017-03-14.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( value="/lys_spring.xml" )
public class TestRedis {

    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void test(){

        redisTemplate.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//存
                redisConnection.setNX("mytest1".getBytes(),"mytest1value".getBytes());
//取
                System.out.println(new String(redisConnection.get("mytest1".getBytes())));
                return null;
            }
        });

    }

}
