package com.guaboy.commons.dependency.redis;


import java.util.Collections;
import redis.clients.jedis.Jedis;

/**
 * Redis分布式锁,依赖 {@link RedisUtil}
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 */
public class RedisLock {
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";
	private static final long tryLockInterval = 50L;
	
	
	
	/**
	 * 获取分布式锁,可等待重试
	 * 
	 * @param lockKey 加锁的key
	 * @param requestId 锁请求ID
	 * @param expireTime 锁失效,毫秒
	 * @param tryTime 重试时间,毫秒
	 *            
	 * @return 是否获取成功
	 */
	public static boolean tryLock(String lockKey, String requestId, int expireTime, long tryTime) {
		Jedis jedis = RedisUtil.getJedis();
		try {
			long startTry = System.currentTimeMillis();		//开始重试时间
			long nextTry = 0L;								//下次重试时间
			
			while(true) {
				if(System.currentTimeMillis() < nextTry) {
					continue;
				}
				String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
				if (LOCK_SUCCESS.equalsIgnoreCase(result)) {
					return true;
				}
				if((System.currentTimeMillis() - startTry) > tryTime) {
					return false;
				}
				nextTry = System.currentTimeMillis()+tryLockInterval;   //间隔这么久重试一次
			}
		} catch (Exception e) {
		} finally {
			RedisUtil.returnResource(jedis);
		}
		return false;
	}	
	
	
	/**
	 * 获取分布式锁
	 * 
	 * @param lockKey 加锁的key
	 * @param requestId 锁请求ID
	 * @param expireTime 锁失效,毫秒
	 *            
	 * @return 是否获取成功
	 */
	public static boolean lock(String lockKey, String requestId, int expireTime) {
		Jedis jedis = RedisUtil.getJedis();
		try {
			String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
			if (LOCK_SUCCESS.equals(result)) {
				return true;
			}
		} catch (Exception e) {
		} finally {
			RedisUtil.returnResource(jedis);
		}
		return false;
	}
	
	
    /**
     * 释放分布式锁
	 * 
	 * @param lockKey 加锁的key
	 * @param requestId 锁请求ID
     * 
     * @return 是否释放成功
     */
    public static boolean unLock(String lockKey, String requestId) {
    	Jedis jedis = RedisUtil.getJedis();
    	try {
			//利用LUA脚本解锁一步到位,保持操作的原子性,具体说明请参考redis特性。
			String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
			Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
			Long rs = Long.parseLong(result.toString());
			if (rs > 0) {
			    return true;
			}
		} catch (Exception e) {
		} finally {
			RedisUtil.returnResource(jedis);
		}
        return false;
    }
	
	
}
