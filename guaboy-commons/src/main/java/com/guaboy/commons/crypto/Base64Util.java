package com.guaboy.commons.crypto;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * BASE64工具类,封装原生BASE64Encoder类,不抛异常。
 * 
 * @since 1.0.0-RELEASE
 * 
 */
@SuppressWarnings("restriction")
public class Base64Util {

	
	/**
	 * 对字符串进行BASE64编码,失败返回NULL
	 * 
	 * @param str
	 * 
	 * @return String
	 */
	public static String encode(String str) {
		try {
			String result = new BASE64Encoder().encode(str.getBytes());
			return result.replaceAll("\\r\\n", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 按指定编码对字符串进行BASE64编码,失败返回NULL
	 * 
	 * @param str
	 * @param charsetName
	 * 
	 * @return String
	 */
	public static String encode(String str, String charsetName) {
		try {
			String result;
			Charset charset = Charset.forName(charsetName);
			if (charset != null) {
				result = new BASE64Encoder().encode(str.getBytes(charset));
			} else {
				result = new BASE64Encoder().encode(str.getBytes());
			}
			return result.replaceAll("\\r\\n", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 对byte[]进行BASE64编码,失败返回NULL
	 * 
	 * @param bytes
	 * 
	 * @return String
	 */
	public static String encode(byte[] bytes) {
		try {
			String result = new BASE64Encoder().encode(bytes);
			return result.replaceAll("\\r\\n", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 对ByteBuffer进行BASE64编码,失败返回NULL
	 * 
	 * @param buffer
	 * 
	 * @return String
	 */
	public static String encode(ByteBuffer buffer) {
		try {
			String result = new BASE64Encoder().encode(buffer);
			return result.replaceAll("\\r\\n", ""); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 解码BASE64字符串为btye[],失败返回NULL
	 * 
	 * @param base64Str
	 * 
	 * @return byte[]
	 */
	public static byte[] decodeBuffer(String base64Str) {
		try {
			return new BASE64Decoder().decodeBuffer(base64Str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 解码BASE64字符串为String,失败返回NULL
	 * 
	 * @param base64Str
	 * 
	 * @return String
	 */
	public static String decode(String base64Str) {
		try {
			byte[] bytes = new BASE64Decoder().decodeBuffer(base64Str);
			return new String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 按指定编码解码BASE64字符串为String,失败返回NULL
	 * 
	 * @param base64Str
	 * @param charsetName
	 * 
	 * @return String
	 */
	public static String decode(String base64Str, String charsetName) {
		try {
			byte[] bytes = new BASE64Decoder().decodeBuffer(base64Str);
			Charset cs = Charset.forName(charsetName);
			if (cs != null) {
				return new String(bytes, charsetName);
			} else {
				return new String(bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
