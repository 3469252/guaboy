package com.guaboy.commons.dependency.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringRedisConfig {
	@Value("${spring.redis.database:0}")
	private int database;
	
	@Value("${spring.redis.host:localhost}")
	private String host;
	
	@Value("${spring.redis.port:6379}")
	private int port;

	@Value("${spring.redis.password:''}")
	private String password;
	
	@Value("${spring.redis.timeout:10000}")
	private int timeout;
	
	@Value("${spring.redis.jedis.pool.max-active:10}")
	private int maxActive;
	
	@Value("${spring.redis.jedis.pool.max-idle:10}")
	private int maxIdle;
	
	@Value("${spring.redis.jedis.pool.min-idle:0}")
	private int minIdle;
	
	@Value("${spring.redis.jedis.pool.max-wait:10000}")
	private long maxWaitMillis;
	
	
	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
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

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

}
