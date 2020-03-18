package com.guaboy.commons.dependency.redis;


/**
 * Redis内部配置类
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 */
class RedisConfig {
	/** Redis库下标 */
	private int database;
	
	/** Redis主机地址,默认localhost */
	private String host;
	
	/** Redis端口,默认6379 */
	private int port;

	/** Redis密码,默认空 */
	private String password;
	
	/** Redis创建Jedis连接的超时时间,默认5000毫秒 */
	private int timeout;
	
	/** Jedis连接池中最大连接数,默认-1为不限制 */
	private int maxActive;
	
	/** Jedis连接池中最大空闲数,默认10 */
	private int maxIdle;
	
	/** Jedis连接池中最小空闲数,默认0 */
	private int minIdle;
	
	/** 从Jedis池中获取连接的等待时长,毫秒,默认-1不超时 */
	private long maxWaitMillis;
	
	/** 获取连接前是否进行有效性检验,若是失效连接,则从池中移除连接并重新获取一个,默认true */
	private boolean testOnBorrow;
	
	/** 连接返回连接池前,是否进行有效性检验,默认true */
	private boolean testOnReturn;
	
	/** 连接空闲时是否被检查有效性,默认false */
	private boolean testWhileIdle;
	
	/** 连接至少保持空闲XX毫秒,才能被Idle Object Evitor扫描并移除,该配置只有在timeBetweenEvictionRunsMillis大于0时才有意义,默认值30000毫秒 */
	private long minEvictableIdleTimeMillis;
	
	/** 表示Idle Object Evitor扫描间隔的毫秒数,默认值60000毫秒 */
	private long timeBetweenEvictionRunsMillis;
	
	/** 表示Idle Object Evitor每次检测时扫描对象数的最大上限,默认值1000个 */
	private int numTestsPerEvictionRun;

	

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

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}
	
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RedisConfigure [");
        sb.append("database=").append(database);
        sb.append(", host=").append(host);
        sb.append(", port=").append(port);
        sb.append(", password=").append(password);
        sb.append(", timeout=").append(timeout);
        sb.append(", maxActive=").append(maxActive);
        sb.append(", maxIdle=").append(maxIdle);
        sb.append(", minIdle=").append(minIdle);
        sb.append(", maxWaitMillis=").append(maxWaitMillis);
        sb.append(", testOnBorrow=").append(testOnBorrow);
        sb.append(", testOnReturn=").append(testOnReturn);
        sb.append(", testWhileIdle=").append(testWhileIdle);
        sb.append(", minEvictableIdleTimeMillis=").append(minEvictableIdleTimeMillis);
        sb.append(", timeBetweenEvictionRunsMillis=").append(timeBetweenEvictionRunsMillis);
        sb.append(", numTestsPerEvictionRun=").append(numTestsPerEvictionRun);
        sb.append("]");
        return sb.toString();
    }
	
}
