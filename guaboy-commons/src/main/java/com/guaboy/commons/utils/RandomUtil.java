package com.guaboy.commons.utils;

import java.util.Random;

/**
 * 字符串工具类
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public final class RandomUtil {
	private final static Random RAND = new Random();
	private final static String DEFAULT_RAND_CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	
	/**
	 * 生成随机字符串，包含a-z大小写字母和0-9数字
	 * 
	 * @param length 随机字符串的长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomStr(int length) {
		return getRandomStr(DEFAULT_RAND_CHAR_SET, length);
	}
	
	
	/**
	 * 生成随机字符串
	 * 
	 * @param sourceStr 随机字符串中允许出现的字符
	 * @param length 随机字符串的长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomStr(String sourceStr, int length) {
		char[] chars = new char[length];
		int sourceLength = sourceStr.length();
		for (int i = 0; i < length; ++i) {
			int r = RAND.nextInt(sourceLength);
			chars[i] = sourceStr.charAt(r);
		}
		return new String(chars);
	}

}