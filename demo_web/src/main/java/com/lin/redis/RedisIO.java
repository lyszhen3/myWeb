package com.lin.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by wxc on 2017/3/8.
 *
 * @author wxc
 * @version domwiki 4.0.0
 * @since domwiki 4.0.0
 */
@Component
public class RedisIO {

    private static final Logger logger = LoggerFactory.getLogger(RedisIO.class);

    // 这些配置信息放哪不知道。日后再说
    @Value("${redis.host}")
    private String IP;   // ip
    @Value("${redis.port}")
    private int PORT;           // 端口
    @Value("${redis.pass}")
    private String AUTH;          // 密码(原始默认是没有密码)
    @Value("${redis.maxTotal}")
    private int MAX_ACTIVE;           // 最大连接数
    @Value("${redis.maxIdle}")
    private int MAX_IDLE;// 设置最大空闲数
    @Value("${redis.maxWait}")
    private int MAX_WAIT;            // 最大连接时间
    private int TIMEOUT = 10000;             // 超时时间
    private int DATABASE = 10;                // 数据库编号
    private boolean BORROW = true;           // 在borrow一个事例时是否提前进行validate操作
    private JedisPool pool = null;

    /**
     * 初始化线程池
     */
    @PostConstruct
    public void init(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(BORROW);
        if (StringUtils.isEmpty(AUTH)) {
            AUTH = null;
        }
        pool = new JedisPool(config, IP, PORT, TIMEOUT, AUTH, DATABASE);
    }

    public Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * 归还redis,3.0以后才使用close
     * @param jedis
     */
    public void returnJedis(Jedis jedis){
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

    public boolean put(String key, Object value) {
        Jedis jedis = getJedis();
        try {
            return value != null && "OK".equals(jedis.set(key, JSON.toJSONString(value)));
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 创建临时记录
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean putTemp(String key,Object value,Integer time){
        Jedis jedis = getJedis();
        try {
            return value != null && "OK".equals(jedis.setex(key,time,JSON.toJSONString(value)));
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 按固定时间
     * @return
     */
    public boolean putFixedTemp(String key,Object value,Integer time){
        Jedis jedis = getJedis();
        try {
            Long ttl=jedis.ttl(key);
            if(ttl!=null&&ttl>0){
                time=ttl.intValue();
            }
            return value != null && "OK".equals(jedis.setex(key,time,JSON.toJSONString(value)));
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 加队列
     * @param key
     * @param value
     * @return
     */
    public boolean rpush(String key,Object value){
        Jedis jedis = getJedis();
        try  {
            return value != null && jedis.rpush(key,JSON.toJSONString(value))>=0;
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 获取队列值
     * @param key
     * @return
     */
    public String rpop(String key){
        Jedis jedis = getJedis();
        try {
            return jedis.rpop(key);
        }finally {
            returnJedis(jedis);
        }
    }

    public Long countList(String key){
        Jedis jedis = getJedis();
        try {
            return jedis.llen(key);
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 获取队列值
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T rpop(String key,Class<T> clazz){
            String s=rpop(key);
            if (s != null) {
                return JSON.parseObject(s, clazz);
            }
            return null;
    }

    /**
     * 获取队列值
     * @param key
     * @return
     */
    public String lpop(String key){
        Jedis jedis = getJedis();
        try {
            return jedis.rpop(key);
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 获取队列值
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T lpop(String key,Class<T> clazz){
        String s=lpop(key);
        if (s != null) {
            return JSON.parseObject(s, clazz);
        }
        return null;
    }

    /**
     * 右出,左进,保证线程安全
     * @param key
     * @param key2
     * @return
     */
    public String rpoplPush(String key,  String key2){
        Jedis jedis = getJedis();
        try  {
            return jedis.rpoplpush(key, key2);
        }finally {
            returnJedis(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        }finally {
            returnJedis(jedis);
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        return JSON.parseObject(get(key), clazz);
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        return JSON.parseArray(get(key),clazz);
    }

    public Boolean hset(String key,String field,Object obj){
        Jedis jedis = getJedis();
        try {
            return obj != null && jedis.hset(key,field, JSON.toJSONString(obj))>0L;
        }finally {
            returnJedis(jedis);
        }
    }

    public <T> T hget(String key,String field,Class<T> clazz){
        Jedis jedis = getJedis();
        try {
            return JSON.parseObject(jedis.hget(key,field),clazz);
        }finally {
            returnJedis(jedis);
        }
    }

    public Boolean hmset(String key,Map<String,String> map){
        Jedis jedis = getJedis();
        String result="";
        try {
            result = jedis.hmset(key, map);
        } finally {
            returnJedis(jedis);
        }
        if(result.equalsIgnoreCase("OK")){
            return true;
        }
        return false;
    }


    public List<String> hmget(String key,String... str){
        Jedis jedis = getJedis();
        try {
            List<String> hmget = jedis.hmget(key, str);

            return hmget;
        } finally {
            returnJedis(jedis);
        }

    }


//    @Override
//    protected void finalize() throws Throwable {
//        pool.close();
//        super.finalize();
//    }

    public void del(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        }finally {
            returnJedis(jedis);
        }
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public String getAUTH() {
        return AUTH;
    }

    public void setAUTH(String AUTH) {
        this.AUTH = AUTH;
    }

    public int getMAX_ACTIVE() {
        return MAX_ACTIVE;
    }

    public void setMAX_ACTIVE(int MAX_ACTIVE) {
        this.MAX_ACTIVE = MAX_ACTIVE;
    }

    public int getMAX_IDLE() {
        return MAX_IDLE;
    }

    public void setMAX_IDLE(int MAX_IDLE) {
        this.MAX_IDLE = MAX_IDLE;
    }

    public int getMAX_WAIT() {
        return MAX_WAIT;
    }

    public void setMAX_WAIT(int MAX_WAIT) {
        this.MAX_WAIT = MAX_WAIT;
    }

    public int getTIMEOUT() {
        return TIMEOUT;
    }

    public void setTIMEOUT(int TIMEOUT) {
        this.TIMEOUT = TIMEOUT;
    }

    public int getDATABASE() {
        return DATABASE;
    }

    public void setDATABASE(int DATABASE) {
        this.DATABASE = DATABASE;
    }

    public boolean isBORROW() {
        return BORROW;
    }

    public void setBORROW(boolean BORROW) {
        this.BORROW = BORROW;
    }

    public JedisPool getPool() {
        return pool;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }
}