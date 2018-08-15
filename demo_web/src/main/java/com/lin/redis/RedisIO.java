package com.lin.redis;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@SuppressWarnings({"unused,javadoc", "WeakerAccess", "UnusedReturnValue"})
@Component
public class RedisIO {

	private static final Logger logger = LogManager.getLogger(RedisIO.class);


	@Value("${redis.host}")
	private String ip;
	@Value("${redis.port}")
	private int port;

	/**
	 * 认证密码
	 */
	@Value("${redis.pass}")
	private String auth;

	/**
	 * 最大连接数
	 */
	@Value("${redis.maxTotal}")
	private int maxActive;

	/**
	 * 最大空闲数
	 */
	@Value("${redis.maxIdle}")
	private int maxIdle;
	/**
	 * 最大连接时间
	 */
	@Value("${redis.maxWait}")
	private int maxWait;
	/**
	 * 超时时间
	 */
	private int timeout = 10000;
	/**
	 * 数据库编号
	 */
	private int database = 10;
	/**
	 * 在borrow一个事例时是否提前进行validate操作
	 */
	private boolean borrow = true;
	private JedisPool pool = null;

	/**
	 * 初始化线程池
	 */
	@PostConstruct
	public void init() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxActive);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWait);
		config.setTestOnBorrow(borrow);
		if (StringUtils.isEmpty(auth)) {
			auth = null;
		}
		pool = new JedisPool(config, ip, port, timeout, auth, database);
	}

	public Jedis getJedis() {
		return pool.getResource();
	}

	/**
	 * 归还redis,3.0以后才使用close
	 *
	 * @param jedis jedis
	 */
	public void returnJedis(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	public boolean put(String key, Object value) {
		Jedis jedis = getJedis();
		try {
			return value != null && "OK".equals(jedis.set(key, JSON.toJSONString(value)));
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 创建临时记录
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  保持时间
	 * @return boolean
	 */
	public boolean putTemp(String key, Object value, Integer time) {
		Jedis jedis = getJedis();
		try {
			return value != null && "OK".equals(jedis.setex(key, time, JSON.toJSONString(value)));
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 按固定时间
	 *
	 * @return
	 */
	public boolean putFixedTemp(String key, Object value, Integer time) {
		Jedis jedis = getJedis();
		try {
			Long ttl = jedis.ttl(key);
			if (ttl != null && ttl > 0) {
				time = ttl.intValue();
			}
			return value != null && "OK".equals(jedis.setex(key, time, JSON.toJSONString(value)));
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 加队列
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean rpush(String key, Object value) {
		Jedis jedis = getJedis();
		try {
			return value != null && jedis.rpush(key, JSON.toJSONString(value)) >= 0;
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 获取队列值
	 *
	 * @param key
	 * @return
	 */
	public String rpop(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.rpop(key);
		} finally {
			returnJedis(jedis);
		}
	}

	public Long countList(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.llen(key);
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 获取队列值
	 *
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T rpop(String key, Class<T> clazz) {
		String s = rpop(key);
		if (s != null) {
			return JSON.parseObject(s, clazz);
		}
		return null;
	}

	/**
	 * 获取队列值
	 *
	 * @param key
	 * @return
	 */
	public String lpop(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.rpop(key);
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 获取队列值
	 *
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T lpop(String key, Class<T> clazz) {
		String s = lpop(key);
		if (s != null) {
			return JSON.parseObject(s, clazz);
		}
		return null;
	}

	/**
	 * 右出,左进,保证线程安全
	 *
	 * @param key
	 * @param key2
	 * @return
	 */
	public String rpoplPush(String key, String key2) {
		Jedis jedis = getJedis();
		try {
			return jedis.rpoplpush(key, key2);
		} finally {
			returnJedis(jedis);
		}
	}

	public String get(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.get(key);
		} finally {
			returnJedis(jedis);
		}
	}

	public <T> T get(String key, Class<T> clazz) {
		return JSON.parseObject(get(key), clazz);
	}

	public <T> List<T> getList(String key, Class<T> clazz) {
		return JSON.parseArray(get(key), clazz);
	}

	public Boolean hset(String key, String field, Object obj) {
		Jedis jedis = getJedis();
		try {
			return obj != null && jedis.hset(key, field, JSON.toJSONString(obj)) > 0L;
		} finally {
			returnJedis(jedis);
		}
	}

	public <T> T hget(String key, String field, Class<T> clazz) {
		Jedis jedis = getJedis();
		try {
			return JSON.parseObject(jedis.hget(key, field), clazz);
		} finally {
			returnJedis(jedis);
		}
	}

	public Boolean hmset(String key, Map<String, String> map) {
		Jedis jedis = getJedis();
		String result = "";
		try {
			result = jedis.hmset(key, map);
		} finally {
			returnJedis(jedis);
		}
		if (result.equalsIgnoreCase("OK")) {
			return true;
		}
		return false;
	}


	public List<String> hmget(String key, String... str) {
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
		} finally {
			returnJedis(jedis);
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public boolean isBorrow() {
		return borrow;
	}

	public void setBorrow(boolean borrow) {
		this.borrow = borrow;
	}

	public JedisPool getPool() {
		return pool;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}
}