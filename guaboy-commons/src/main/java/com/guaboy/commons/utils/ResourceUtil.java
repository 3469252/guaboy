package com.guaboy.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;


/**
 * Resource资源加载工具类
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class ResourceUtil {
	
	
	/**
	 * 获取指定资源文件的URL对象
	 * 
	 * @param resource 资源路径
	 * @param clazz 当前引用类
	 * 
	 * @return URL对象或NULL
	 */
	public static URL getResourceURL(String resource, Class<?> clazz) {
		ClassLoader classLoader = null;
		try {
			Method method = Thread.class.getMethod("getContextClassLoader");
			classLoader = (ClassLoader) method.invoke(Thread.currentThread());
		} catch (Exception e) {
		}
		if (classLoader == null) {
			classLoader = clazz.getClassLoader();
		}
		try {
			if (classLoader != null) {
				URL url = classLoader.getResource(resource);
				if (url == null) {
					System.out.println("Not Found Resource: " + resource);
					return null;
				}
				if (url.toString().startsWith("jar:file:")) {
					System.out.println("Get Resource: '" + resource + "', From JAR: " + url.toString());
				} else {
					System.out.println("Get Resource: '" + resource + "', From: " + url.toString());
				}
				return url;
			}
		} catch (Exception e) {
			System.err.println("Load Resource Error: "+resource);
		}
		return null;
	}
	
	
	/**
	 * 获取指定资源文件的全路径
	 * 
	 * @param resource 资源路径
	 * @param clazz 当前引用类
	 * 
	 * @return
	 */
	public static String getResourcePath(String resource, Class<?> clazz) {
		URL url = getResourceURL(resource, clazz);
		if (url == null) {
			return "";
		}
		return url.getPath();
	}
	
	
	/**
	 * 获取指定资源文件的InputStream流
	 * 
	 * @param resource 资源路径
	 * @param clazz 当前引用类
	 * 
	 * @return
	 */
	public static InputStream getResourceInputStream(String resource, Class<?> clazz) {
		URL url = getResourceURL(resource, clazz);
		if (url == null) {
			return null;
		}
		try {
			if (url.toString().startsWith("jar:file:")) {
				String path = resource.startsWith("/") ? resource : "/" + resource;
				return clazz.getResourceAsStream(path);
			} else {
				return new FileInputStream(new File(url.getPath()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取项目的classes路径
	 * 
	 * @return
	 */
	public static String getClassesPath() {
		return getResourcePath("", ResourceUtil.class);
	}
	
}
