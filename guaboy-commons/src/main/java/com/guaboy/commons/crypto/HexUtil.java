package com.guaboy.commons.crypto;

/**
 * HEX十六制工具类
 * 
 * @since 1.0.0-RELEASE
 * 
 */
public class HexUtil {
	private static final char[] digits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static final byte[] emptybytes = new byte[0];

	
	/**
	 * 将单个字节转成Hex String
	 * 
	 * @param b 字节
	 * 
	 * @return String
	 */
	public static String byte2HexStr(byte b) {
		char[] buf = new char[2];
		buf[1] = digits[b & 0xF];
		b = (byte) (b >>> 4);
		buf[0] = digits[b & 0xF];
		return new String(buf);
	}

	
	/**
	 * 将字节数组转成Hex String
	 * 
	 * @param bytes 字节数组
	 * 
	 * @return String or Null
	 */
	public static String bytes2HexStr(byte[] bytes) {
		return bytes2HexStr(bytes, 0, bytes.length);
	}

	
	/**
	 * 将字节数组指定长度转为Hex String
	 * 
	 * @param bytes 字节数组
	 * @param offset 开始位置
	 * @param length 需要转换的长度
	 * 
	 * @return String or Null
	 */
	public static String bytes2HexStr(byte[] bytes, int offset, int length) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		if (offset < 0) {
			throw new IllegalArgumentException("offset(" + offset + ")");
		}

		if (offset + length > bytes.length) {
			throw new IllegalArgumentException("offset + length(" + offset + length + ") > bytes.length(" + bytes.length + ")");
		}

		char[] buf = new char[2 * length];
		for (int i = 0; i < length; i++) {
			byte b = bytes[i + offset];
			buf[2 * i + 1] = digits[b & 0xF];
			b = (byte) (b >>> 4);
			buf[2 * i + 0] = digits[b & 0xF];
		}
		return new String(buf);
	}

	
	/**
	 * 将单个Hex String转换成字节
	 * 
	 * @param hexStr
	 * 
	 * @return byte
	 */
	public static byte hexStr2Byte(String hexStr) {
		if (hexStr != null && hexStr.length() == 1) {
			return char2Byte(hexStr.charAt(0));
		} else {
			return 0;
		}
	}

	
	/**
	 * 字符到字节
	 * 
	 * @param ch
	 * 
	 * @return byte
	 */
	public static byte char2Byte(char ch) {
		if (ch >= '0' && ch <= '9') {
			return (byte) (ch - '0');
		} else if (ch >= 'a' && ch <= 'f') {
			return (byte) (ch - 'a' + 10);
		} else if (ch >= 'A' && ch <= 'F') {
			return (byte) (ch - 'A' + 10);
		} else {
			return 0;
		}
	}

	
	/**
	 * 将Hex String转换成字节数组
	 * 
	 * @param hexStr
	 * 
	 * @return
	 */
	public static byte[] hexStr2Bytes(String hexStr) {
		if (hexStr == null || hexStr.equals("")) {
			return emptybytes;
		}
		byte[] bytes = new byte[hexStr.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			char high = hexStr.charAt(i * 2);
			char low = hexStr.charAt(i * 2 + 1);
			bytes[i] = (byte) (char2Byte(high) * 16 + char2Byte(low));
		}
		return bytes;
	}

}
