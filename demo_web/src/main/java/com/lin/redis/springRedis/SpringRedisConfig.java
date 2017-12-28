package com.lin.redis.springRedis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lys on 11/17/2017.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
//@Configuration
@EnableRedisHttpSession
public class SpringRedisConfig {
    @Value("${redis.host}")
    private String IP;   // ip
    @Value("${redis.port}")
    private int PORT;           // 端口
    @Value("${redis.pass}")
    private String AUTH;          // 密码(原始默认是没有密码)
    @Value("${redis.maxTotal}")
    private int MAX_TOTAL;           // 最大连接数
    @Value("${redis.maxIdle}")
    private int MAX_IDLE;// 设置最大空闲数
    @Value("${redis.maxWait}")
    private int MAX_WAIT;            // 最大连接时间
    private int TIMEOUT = 10000;             // 超时时间
    private int DATABASE = 10;                // 数据库编号
    private boolean BORROW = true;           // 在borrow一个事例时是否提前进行validate操作

    @Bean(destroyMethod = "destroy")
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(MAX_IDLE);
        jedisPoolConfig.setMaxTotal(MAX_TOTAL);
        jedisPoolConfig.setMaxWaitMillis(MAX_WAIT);
        jedisPoolConfig.setTestOnBorrow(BORROW);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setHostName(IP);
        jedisConnectionFactory.setPort(PORT);
        jedisConnectionFactory.setPassword(AUTH);
        jedisConnectionFactory.setDatabase(DATABASE);
        jedisConnectionFactory.setTimeout(TIMEOUT);

        return jedisConnectionFactory;
    }


}
