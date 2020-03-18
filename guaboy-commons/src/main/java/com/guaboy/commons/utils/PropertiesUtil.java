package com.guaboy.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Properties文件处理工具类
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 */
public class PropertiesUtil {
	
	/** 已加载过的properties名称 */
	private static HashSet<String> alreadyLoad = new HashSet<>();
	
	/** 加载成功properties对象 */
	private static ConcurrentHashMap<String, Properties> propCache = new ConcurrentHashMap<>();
	
	
	private static Properties loadProperties(String propName) {
		if(alreadyLoad.contains(propName)) {
			return null;
		}
		InputStream is = ResourceUtil.getResourceInputStream(propName, PropertiesUtil.class);
		if(is == null) {
			alreadyLoad.add(propName);
			return null;
		}
		
		Properties prop = new Properties();
		try {
			InputStreamReader reader = new InputStreamReader(is);
			prop.load(reader);
			propCache.put(propName, prop);
			return prop;
		} catch (IOException e) {
			System.err.println(String.format("PropertiesUtil加载[%s]配置失败", propName));
			e.printStackTrace();
		}
		
		alreadyLoad.add(propName);
		return null;
	}
	
	
	/**
	 * 获取Properties对象
	 * 
	 * @param propName
	 * @return
	 */
	public static Properties getProperties(String propName) {
		Properties prop = propCache.get(propName);
		if(prop == null) {
			prop = loadProperties(propName);
		}
		return prop;
	}
	
	
	/**
	 * 判断Properties文件中是否存在指定key
	 * 
	 * @param propName
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String propName, String key) {
		Properties prop = getProperties(propName);
		if(prop == null) {
			return false;
		}
		return prop.containsKey(key);
	}
	
	
	/**
	 * 获取指定Properties文件中key的String值,不存在则返回NULL
	 * 
	 * @param propName
	 * @param key
	 * 
	 * @return
	 */
	public static String getString(String propName, String key) {
		return getString(propName, key, null);
	}
	
	
	/**
	 * 获取指定Properties文件中key的String值,获取失败则返回默认值
	 * 
	 * @param propName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(String propName, String key, String defaultValue) {
		Properties prop = getProperties(propName);
		if(prop == null) {
			return defaultValue;
		}
		String value = prop.getProperty(key);
		if(StringUtil.isEmpty(value)) {
			return defaultValue;
		}
		return value;
	}
	
	
	/**
	 * 获取指定Properties文件中key的int值,获取失败则返回0
	 * 
	 * @param propName
	 * @param key
	 * @return
	 */
	public static int getInt(String propName, String key) {
		return getInt(propName, key, 0);
	}
	
	
	/**
	 * 获取指定Properties文件中key的int值,获取失败则返回默认值
	 * 
	 * @param propName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(String propName, String key, int defaultValue) {
		String val = getString(propName, key);
		return NumberUtil.convertInt(val, defaultValue);
	}
	
	
	/**
	 * 获取指定Properties文件中key的long值,获取失败则返回0
	 * 
	 * @param propName
	 * @param key
	 * @return
	 */
	public static long getLong(String propName, String key) {
		return getLong(propName, key, 0);
	}
	
	
	/**
	 * 获取指定Properties文件中key的int值,获取失败则返回默认值
	 * 
	 * @param propName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(String propName, String key, long defaultValue) {
		String val = getString(propName, key);
		return NumberUtil.convertLong(val, defaultValue);
	}
	
	
	/**
	 * 获取指定Properties文件中key的double值,获取失败则返回0.0
	 * 
	 * @param propName
	 * @param key
	 * @return
	 */
	public static double getDouble(String propName, String key) {
		return getDouble(propName, key, 0.0);
	}
	
	
	/**
	 * 获取指定Properties文件中key的double值,获取失败则返回默认值
	 * 
	 * @param propName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static double getDouble(String propName, String key, double defaultValue) {
		String val = getString(propName, key);
		return NumberUtil.convertDouble(val, defaultValue);
	}
	
	
	/**
	 * 获取指定Properties文件中key的boolean值,false
	 * 
	 * @param propName
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String propName, String key) {
		return getBoolean(propName, key, false);
	}
	
	
	/**
	 * 获取指定Properties文件中key的boolean值,获取失败则返回默认值
	 * 
	 * @param propName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(String propName, String key, boolean defaultValue) {
		String val = getString(propName, key);
		return NumberUtil.convertBoolean(val, defaultValue);
	}
	
}
