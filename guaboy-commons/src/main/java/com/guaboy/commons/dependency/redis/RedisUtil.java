package com.guaboy.commons.dependency.redis;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.guaboy.commons.dependency.spring.SpringUtil;
import com.guaboy.commons.utils.NumberUtil;
import com.guaboy.commons.utils.ResourceUtil;
import com.guaboy.commons.utils.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Resdis工具类,依赖 jedis-2.9.0以上
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 */
public class RedisUtil {
	
	/** 配置属性 */
	private static RedisConfig config = null;
	
	/** 连接池 */
	private static JedisPool jedisPool = null;


	static {
		init();
	}
	
	
	/**
	 * 初始化Redis连接池
	 */
	private static synchronized void init() {
		if (jedisPool == null) {
			initPool();
		}
	}

	
	/** initPool */
	private static synchronized void initPool() {
		if (jedisPool != null) {
			return;
		}
		
		/** 1、先使用spring注解方式加载的配置*/
		try {
			SpringRedisConfig conf = SpringUtil.getBean(SpringRedisConfig.class);
			if(conf != null) {
				config = new RedisConfig();
				config.setDatabase(conf.getDatabase());
				config.setHost(conf.getHost());
				config.setPort(conf.getPort());
				config.setPassword(conf.getPassword());
				config.setTimeout(conf.getTimeout());
				config.setMaxActive(conf.getMaxActive());
				config.setMaxIdle(conf.getMaxIdle());
				config.setMinIdle(conf.getMinIdle());
				config.setMaxWaitMillis(conf.getMaxWaitMillis());
				config.setTestOnBorrow(true);
				config.setTestOnReturn(true);
				config.setTestWhileIdle(false);
				config.setMinEvictableIdleTimeMillis(30000);
				config.setTimeBetweenEvictionRunsMillis(60000);
				config.setNumTestsPerEvictionRun(1000);
			}
		} catch (Exception ex) {
		}
		
		/** 2、上一步未启用则使用classpath下的redis.properties配置文件 */
		if(config == null) {
			System.err.println("Spring配置模式未启用,准备加载redis.properties配置文件");
			Properties prop = new Properties();
			try {
				InputStream is = ResourceUtil.getResourceInputStream("redis.properties", RedisUtil.class);
				InputStreamReader reader = new InputStreamReader(is, "UTF-8");
				prop.load(reader);
			} catch (Exception ex) {
				System.err.println("解析classpath下的redis.properties配置文件失败,采用系统默认配置");
			}
			
			config = new RedisConfig();
			config.setDatabase(NumberUtil.convertInt(prop.getProperty("redis.database"),0));
			config.setHost(StringUtil.convertStr(prop.getProperty("redis.host"),"localhost"));
			config.setPort(NumberUtil.convertInt(prop.getProperty("redis.port"), 6379));
			config.setPassword(StringUtil.convertStr(prop.getProperty("redis.password"), ""));
			config.setTimeout(NumberUtil.convertInt(prop.getProperty("redis.timeout"),10000));
			config.setMaxActive(NumberUtil.convertInt(prop.getProperty("redis.jedis.pool.maxActive"),10));
			config.setMaxIdle(NumberUtil.convertInt(prop.getProperty("redis.jedis.pool.maxIdle"),10));
			config.setMinIdle(NumberUtil.convertInt(prop.getProperty("redis.jedis.pool.minIdle"),0));
			config.setMaxWaitMillis(NumberUtil.convertLong(prop.getProperty("redis.jedis.pool.maxWait"),10000));
			config.setTestOnBorrow(NumberUtil.convertBoolean(prop.getProperty("redis.jedis.pool.testOnBorrow"),true));
			config.setTestOnReturn(NumberUtil.convertBoolean(prop.getProperty("redis.jedis.pool.testOnReturn"),true));
			config.setTestWhileIdle(NumberUtil.convertBoolean(prop.getProperty("redis.jedis.pool.testWhileIdle"),false));
			config.setMinEvictableIdleTimeMillis(NumberUtil.convertLong(prop.getProperty("redis.jedis.pool.minEvictableIdleTimeMillis"),30000L));
			config.setTimeBetweenEvictionRunsMillis(NumberUtil.convertLong(prop.getProperty("redis.jedis.pool.timeBetweenEvictionRunsMillis"),60000L));
			config.setNumTestsPerEvictionRun(NumberUtil.convertInt(prop.getProperty("redis.jedis.pool.numTestsPerEvictionRun"),1000));
		}
		
		/** 3、初始化Jedis连接池 */
		try {
			JedisPoolConfig cg = new JedisPoolConfig();
			cg.setMaxTotal(config.getMaxActive());
			cg.setMaxIdle(config.getMaxIdle());
			cg.setMinIdle(config.getMinIdle());
			cg.setMaxWaitMillis(config.getMaxWaitMillis());
			cg.setTestOnBorrow(config.isTestOnBorrow());
			cg.setTestOnReturn(config.isTestOnReturn());
			cg.setTestWhileIdle(config.isTestWhileIdle());
			cg.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
			cg.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
			cg.setNumTestsPerEvictionRun(config.getNumTestsPerEvictionRun());

			jedisPool = new JedisPool(cg, config.getHost(), config.getPort(), config.getTimeout(), config.getPassword(), config.getDatabase());
		} catch (Exception e) {
			if (jedisPool != null) {
				jedisPool.close();
			}
			System.err.println("初始化JedisPool连接池出现异常");
			e.printStackTrace();
		}
	}

	
	/**
	 * 获取配置信息
	 * 
	 */
	public static String getConfigureInfo() {
		if(config == null) {
			init();
		}
		return config.toString();
	}
	
	
	/**
	 * 获取连接实时信息
	 * 
	 */
	public static String getJedisPoolInfo() {
		if (jedisPool == null) {
			init();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("JedisPoolInfo [");
		sb.append("numActive=").append(jedisPool.getNumActive());
		sb.append(", numIdle=").append(jedisPool.getNumIdle());
		sb.append(", numWaiters=").append(jedisPool.getNumWaiters());
		sb.append(", maxBorrowWaitTimeMillis=").append(jedisPool.getMaxBorrowWaitTimeMillis());
		sb.append(", meanBorrowWaitTimeMillis=").append(jedisPool.getMeanBorrowWaitTimeMillis());
		sb.append("]");
		
		return sb.toString();
	}
	

	/**
	 * 获取Jedis实例
	 * 
	 */
	public static Jedis getJedis() {
		if (jedisPool == null) {
			init();
		}

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			returnResource(jedis);
			throw new RuntimeException("从Jedis连接池中获取连接异常", e);
		}
		if(jedis == null) {
			throw new RuntimeException("从Jedis连接池中未获取到连接");
		}
		return jedis;
	}

	
	/**
	 * 释放jedis资源
	 * 
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	
	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param seconds 秒
	 * 
	 * @return 操作成功的条数
	 */
	public static long expire(String key, int seconds) {
		Jedis jedis = getJedis();

		long result = 0L;
		try {
			result = jedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	
	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * 
	 * @return
	 */
	public static boolean exists(String key) {
		Jedis jedis = getJedis();

		boolean result = false;
		try {
			result = jedis.exists(key) || jedis.exists(key.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	
	/**
	 * 批量删除key
	 * 
	 * @param keys
	 * 
	 * @return 操作成功的条数
	 */
	public static long del(String... keys) {
		Jedis jedis = getJedis();

		long result = 0L;
		try {
			result = jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	
	/**
	 * 模糊查找key
	 *  
	 * @param keyPattern
	 * 
	 * @return Set 或 null
	 */
	public static Set<String> keys(String keyPattern) {
		Jedis jedis = getJedis();

		Set<String> keySet = new HashSet<String>();
		try {
			keySet = jedis.keys(keyPattern);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return keySet;
	}

	
	/**
	 * 设置值
	 *  
	 * @param key
	 * @param value
	 * @param expireSeconds 过期时间,秒
	 * 
	 * @return OK=成功
	 * 
	 */
	public static String setString(String key, String value, int expireSeconds) {
		Jedis jedis = getJedis();
		
		String result = "";
		try {
			result = jedis.setex(key, expireSeconds, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	
	/**
	 * 获取值
	 * 
	 * @param key
	 * 
	 * @return String 或 NULL
	 */
	public static String getString(String key) {
		Jedis jedis = getJedis();

		String result = null;
		try {
			result = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	
	/**
	 * Set If Not Exists, 即key存在,则setnx不做任何操作,若操作成功则会设置过期时间
	 *  
	 * @param key
	 * @param value
	 * @param expireSeconds 过期时间,秒
	 * 
	 * @return 操作成功的条数
	 */
	public static long setNx(String key, String value, int expireSeconds) {
		Jedis jedis = getJedis();

		long result = 0L;
		try {
			result = jedis.setnx(key, value);
			if(result > 0) {
				jedis.expire(key, expireSeconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return result;
	}

}
