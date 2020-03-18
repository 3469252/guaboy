package com.guaboy.commons.crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类,封装原生的MD5
 * 
 * @since 1.0.0-RELEASE
 * 
 */
public class MD5Crypto {

	
	/**
	 * MD5算法加密字节数组
	 * 
	 * @param bytes
	 * 
	 * @return 加密后的字节数组,若加密失败返回null
	 */
	public static byte[] encode(byte[] bytes) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(bytes);
			byte[] digesta = digest.digest();
			return digesta;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 用MD5算法加密后再转换成Hex String
	 * 
	 * @param bytes
	 * 
	 * @return String
	 */
	public static String encode2HexStr(byte[] bytes) {
		return HexUtil.bytes2HexStr(encode(bytes));
	}

	
	/**
	 * 用MD5算法加密后再转换成BASE64 String
	 * 
	 * @param bytes
	 * 
	 * @return String
	 */
	public static String encode2Base64Str(byte[] bytes) {
		return Base64Util.encode(encode(bytes));
	}

	
	/**
	 * 计算文件的md5
	 * 
	 * @param filePath 文件路径
	 * 
	 * @return md5结果,若加密失败,则返回null
	 */
	public static byte[] encodeFile(String filePath) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			FileInputStream fis = new FileInputStream(filePath);
			byte[] buffer = new byte[1024];
			byte[] digesta = null;
			int readed = -1;
			try {
				while ((readed = fis.read(buffer)) != -1) {
					digest.update(buffer, 0, readed);
				}
				digesta = digest.digest();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
			return digesta;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 计算文件的md5,转换成Hex String
	 * 
	 * @param filePath 文件路径
	 * 
	 * @return md5结果，若加密失败，则返回null
	 */
	public static String encodeFile2HexStr(String filePath) {
		return HexUtil.bytes2HexStr(encodeFile(filePath));
	}

	
	/**
	 * 计算文件的md5,转换成Base64 String
	 * 
	 * @param filePath 文件路径
	 * 
	 * @return md5结果，若加密失败，则返回null
	 */
	public static String encodeFile2Base64(String filePath) {
		byte[] bytes = encodeFile(filePath);
		if (bytes == null) {
			return null;
		}
		return Base64Util.encode(bytes);
	}

}
