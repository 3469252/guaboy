package com.guaboy.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * 字符串处理工具
 *
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public final class StringUtil {
	/** UTF-8编码常量 */
	public static final String ENC_UTF8 = "UTF-8";
	/** GBK编码常量 */
	public static final String ENC_GBK = "GBK";
	
	/** UTF-8的Charset对象 */
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	/** GBK的Charset对象 */
	public static final Charset GBK = Charset.forName("GBK");


	/**
	 * 判断对象或字符串为空 [null 或 obj.toString().trim().isEmpty()]
	 * 
	 * @param obj
	 * 
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		return obj.toString().trim().isEmpty();
	}

	/**
	 * 判断对象或字符串不为空
	 * 
	 * @param obj
	 * 
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
	/**
	 * 格式化对象为字符串,为空则返回默认值
	 * 
	 * @param obj
	 * @param defaultStr
	 * 
	 * @return
	 */
	public static String convertStr(Object obj, String defaultStr) {
		if (isEmpty(obj)) {
			return defaultStr;
		} else {
			return obj.toString();
		}
	}

	/**
	 * 分割字符串,并忽略空值
	 * 
	 * @param src 原始字符串
	 * @param seperator 分隔符
	 * 
	 * @return String[]
	 */
	public static String[] split(String src, String seperator) {
		if (isEmpty(src) || isEmpty(seperator)) {
			return new String[0];
		}
		ArrayList<String> list = new ArrayList<String>();
		String[] array = src.split(seperator);
		for (String s : array) {
			if (isNotEmpty(s)) {
				list.add(s);
			}
		}
		return list.toArray(new String[0]);
	}

	/**
	 * 分割字符串，并转换为int数组
	 * 
	 * @param src 原始字符串
	 * @param seperator 分隔符
	 * @param defaultValue 默认值,无法转换的元素会用该值代替
	 * 
	 * @return int[]
	 */
	public static int[] splitInt(String src, String seperator, int defaultValue) {
		String[] ss = split(src, seperator);
		int[] r = new int[ss.length];
		for (int i = 0; i < r.length; ++i) {
			r[i] = NumberUtil.convertInt(ss[i], defaultValue);
		}
		return r;
	}

	/**
	 * 分割字符串，并转换为long数组
	 * 
	 * @param src 原始字符串
	 * @param seperator 分隔符
	 * @param defaultValue 默认值,无法转换的元素会用该值代替
	 * 
	 * @return long[]
	 */
	public static long[] splitLong(String src, String seperator, long defaultValue) {
		String[] ss = split(src, seperator);
		long[] r = new long[ss.length];
		for (int i = 0; i < r.length; ++i) {
			r[i] = NumberUtil.convertLong(ss[i], defaultValue);
		}
		return r;
	}

	/**
	 * 分割字符串,并以驼峰命名规则拼接。
	 * 
	 * @param src 原字符串
	 * @param seperator 分隔符
	 * 
	 * @return
	 */
	public static String splitToCamelCase(String src, String seperator) {
		String[] array = split(src, seperator);
		if (array == null || array.length == 0) {
			return src;
		}

		StringBuilder sb = new StringBuilder(toLowerCaseFirst(array[0]));
		for (int i = 1; i < array.length; i++) {
			sb.append(toUpperCaseFirst(array[i]));
		}
		return sb.toString();
	}

	/**
	 * 字符串全量替换
	 * 
	 * @param src 原始字符串
	 * @param replace 需要被替换的内容
	 * @param dest 替换后的内容
	 * 
	 * @return
	 */
	public static String replaceAll(String src, String replace, String dest) {
		if (src == null || replace == null || dest == null || replace.length() == 0)
			return src;
		int pos = src.indexOf(replace); 				// 查找第一个替换的位置
		if (pos < 0) {
			return src;
		}
		int capacity = dest.length() > replace.length() ? src.length() * 2 : src.length();
		StringBuilder sb = new StringBuilder(capacity);
		int writen = 0;
		for (; pos >= 0;) {
			sb.append(src, writen, pos); 				// append 原字符串不需替换部分
			sb.append(dest); 							// append 新字符串
			writen = pos + replace.length(); 			// 忽略原字符串需要替换部分
			pos = src.indexOf(replace, writen); 		// 查找下一个替换位置
		}
		sb.append(src, writen, src.length()); 			// 替换剩下的原字符串
		return sb.toString();
	}

	/**
	 * 替换第一个匹配的字符串
	 * 
	 * @param src 原始字符串
	 * @param replace 需要被替换的内容
	 * @param dest 替换后的内容
	 * 
	 * @return
	 */
	public static String replaceFirst(String src, String replace, String dest) {
		if (src == null || replace == null || dest == null || replace.length() == 0)
			return src;
		int pos = src.indexOf(replace);
		if (pos < 0) {
			return src;
		}
		StringBuilder sb = new StringBuilder(src.length() - replace.length() + dest.length());

		sb.append(src, 0, pos);
		sb.append(dest);
		sb.append(src, pos + replace.length(), src.length());
		return sb.toString();
	}

	/**
	 * 替换最后一个匹配的字符串
	 * 
	 * @param src 原始字符串
	 * @param replace 需要被替换的内容
	 * @param dest 替换后的内容
	 * 
	 * @return
	 */
	public static String replaceLast(String src, String replace, String dest) {
		if (src == null || replace == null || dest == null || replace.length() == 0)
			return src;
		int pos = src.lastIndexOf(replace);
		if (pos < 0) {
			return src;
		}
		StringBuilder sb = new StringBuilder(src.length() - replace.length() + dest.length());

		sb.append(src, 0, pos);
		sb.append(dest);
		sb.append(src, pos + replace.length(), src.length());
		return sb.toString();
	}

	/**
	 * 字符串全量删除
	 * 
	 * @param src 原字符串
	 * @param del 需要删除的内容
	 * 
	 * @return
	 */
	public static String removeAll(String src, String del) {
		return replaceAll(src, del, "");
	}

	/**
	 * 删除指定的第一个字符串
	 * 
	 * @param src 原字符串
	 * @param del 需要删除的内容
	 * 
	 * @return
	 */
	public static String removeFirst(String src, String del) {
		return replaceFirst(src, del, "");
	}

	/**
	 * 删除指定的最后一个字符串
	 * 
	 * @param src 原字符串
	 * @param del 需要删除的内容
	 * 
	 * @return
	 */
	public static String removeLast(String src, String del) {
		return replaceLast(src, del, "");
	}

	/**
	 * 大写指定位置的字符
	 * 
	 * @param src 原字符串
	 * @param index 需要大写的字符下标
	 * 
	 * @return
	 */
	public static String toUpperCase(String src, int index) {
		char[] cs = src.toCharArray();
		char c = cs[index];
		if (c >= 97 && c <= 122) {
			cs[index] -= 32;
		}
		return String.valueOf(cs);
	}

	/**
	 * 大写字首字母
	 * 
	 * @param src
	 * 
	 * @return
	 */
	public static String toUpperCaseFirst(String src) {
		return toUpperCase(src, 0);
	}

	/**
	 * 小写指定位置的字符
	 * 
	 * @param src 原字符串
	 * @param index 需要小写的字符下标
	 * 
	 * @return
	 */
	public static String toLowerCase(String src, int index) {
		char[] cs = src.toCharArray();
		char c = cs[index];
		if (c >= 35 && c <= 90) {
			cs[index] += 32;
		}
		return String.valueOf(cs);
	}

	/**
	 * 小写首字母
	 * 
	 * @param src 原字符串
	 * 
	 * @return
	 */
	public static String toLowerCaseFirst(String src) {
		return toLowerCase(src, 0);
	}
	
	/**
	 * 获取字符串的UTF-8编码字节数组
	 * 
	 * @param str
	 * 
	 * @return
	 */
	public static byte[] getBytesByUTF8(String str) {
		if (isNotEmpty(str)) {
			return str.getBytes(UTF_8);
		}
		return new byte[0];
	}

	/**
	 * 获取字节数组的UTF-8编码字符串
	 * 
	 * @param bytes
	 * 
	 * @return
	 */
	public static String getStringByUTF8(byte[] bytes) {
		if (bytes != null) {
			return new String(bytes, UTF_8);
		}
		return "";
	}

	/**
	 * 获取字符串的GBK编码字节数组
	 * 
	 * @param str
	 * 
	 * @return
	 */
	public static byte[] getBytesByGBK(String str) {
		if (isNotEmpty(str)) {
			return str.getBytes(GBK);
		}
		return new byte[0];
	}

	/**
	 * 获取字节数组的GBK编码字符串
	 * 
	 * @param bytes
	 * 
	 * @return
	 */
	public static String getStringByGBK(byte[] bytes) {
		if (bytes != null) {
			return new String(bytes, GBK);
		}
		return "";
	}

	/**
	 * 对字符串以 UTF-8编码方式进行URLEncode
	 * 
	 * @param str
	 * @return
	 */
	public static String URLEncodeByUTF8(String str) {
		if (isNotEmpty(str)) {
			try {
				return URLEncoder.encode(str, ENC_UTF8);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return str;
	}

	/**
	 * 对字符串以 UTF-8编码方式进行URLDecode
	 * 
	 * @param str
	 * 
	 * @return
	 */
	public static String URLDecodeByUTF8(String str) {
		if (isNotEmpty(str)) {
			try {
				return URLDecoder.decode(str, ENC_UTF8);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return str;
	}

	/**
	 * 对字符串以 GBK编码方式进行URLEncode
	 * 
	 * @param str
	 * 
	 * @return
	 */
	public static String URLEncodeByGBK(String str) {
		if (isNotEmpty(str)) {
			try {
				return URLEncoder.encode(str, ENC_GBK);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return str;
	}

	/**
	 * 对字符串以 GBK编码方式进行URLDecode
	 * 
	 * @param str
	 * 
	 * @return
	 */
	public static String URLDecodeByGBK(String str) {
		if (isNotEmpty(str)) {
			try {
				return URLDecoder.decode(str, ENC_GBK);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return str;
	}
	
}
