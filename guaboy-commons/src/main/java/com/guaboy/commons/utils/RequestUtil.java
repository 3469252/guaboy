package com.guaboy.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.guaboy.commons.wrap.MapObject;


/**
 * Request工具类
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class RequestUtil {
	
	/**
	 * 获取Request的body流为byte数组
	 * 
	 * @param request
	 * @return
	 */
	public static byte[] getBytesByInputStream(ServletRequest request) {
		byte[] bts = new byte[0];
		try {
			InputStream inputStream = request.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];  //一次读1024个字节,避免浪费内存
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				baos.write(buffer, 0, length);
			}
			bts = baos.toByteArray();
			
			inputStream.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bts;
	}
	
	/**
	 * 获取Request的body流为String
	 * 
	 * @param request
	 * @return
	 */
	public static String getStringByInputStream(ServletRequest request) {
		return getStringByInputStream(request, null);
	}
	
	/**
	 * 获取Request的body流为String,指定编码格式
	 * 
	 * @param request
	 * @return
	 */
	public static String getStringByInputStream(ServletRequest request, String charsetName) {
		byte[] bts = getBytesByInputStream(request);
		if(StringUtil.isNotEmpty(charsetName)) {
			try {
				return new String(bts, charsetName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new String(bts);
	}

	
	/**
	 * 获取所有表单参数的MAP集合,会自动顾虑空参数
	 * 
	 * @param request
	 * 
	 * @return 
	 */
	public static MapObject getMapByParameter(ServletRequest request){
		MapObject paramMap = new MapObject();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			String value = request.getParameter(key);
			if(StringUtil.isNotEmpty(key) && StringUtil.isNotEmpty(value)) {
				paramMap.put(key, value);
			}
		}
		return paramMap;
	}	
	
	/**
	 * 以表单方式,获取String参数,若无则返回Null
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @return String型的输入参数
	 */
	public static String getStringByParameter(ServletRequest request, String paramName) {
		return getStringByParameter(request, paramName, null);
	}
	
	/**
	 * 以表单方式,获取String参数,若无则返回默认值
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @param defaults
	 *            设定的默认值
	 * @return String型的输入参数
	 */
	public static String getStringByParameter(ServletRequest request, String paramName, String defaults) {
		String str = request.getParameter(paramName);
		if(StringUtil.isEmpty(str)) {
			return defaults;
		}
		return str;
	}

	/**
	 * 以表单方式,获取Integer参数,若无则返回默认值Null
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @return 
	 */
	public static Integer getIntByParameter(ServletRequest request, String paramName) {
		return getIntByParameter(request, paramName, null);
	}

	/**
	 * 以表单方式,获取Integer参数,若无则返回默认值
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @param defaults
	 *            设定的默认值
	 * @return 
	 */
	public static Integer getIntByParameter(ServletRequest request, String paramName, Integer defaults) {
		String str = request.getParameter(paramName);
		return NumberUtil.getInteger(str, defaults);
	}

	/**
	 * 以表单方式,获取Long参数,若无则返回Null
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @return
	 */
	public static Long getLongByParameter(ServletRequest request, String paramName) {
		return getLongByParameter(request, paramName, null);
	}
	
	/**
	 * 以表单方式,获取Long参数,若无则返回默认值
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @param defaults
	 *            设定的默认值
	 * @return
	 */
	public static Long getLongByParameter(ServletRequest request, String paramName, Long defaults) {
		String str = request.getParameter(paramName);
		return NumberUtil.getLong(str, defaults);
	}
	
	/**
	 * 以表单方式,获取Short参数,若无则返回Null
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 *
	 * @return
	 */
	public static Short getShortByParameter(ServletRequest request, String paramName) {
		return getShortByParameter(request, paramName, null);
	}
	
	/**
	 * 以表单方式,获取Short参数,若无则返回默认值
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @param defaults
	 *            设定的默认值
	 * @return
	 */
	public static Short getShortByParameter(ServletRequest request, String paramName, Short defaults) {
		String str = request.getParameter(paramName);
		return NumberUtil.getShort(str, defaults);
	}

	/**
	 * 以表单方式,获取Double参数,若无则返回Null
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @return
	 */
	public static Double getDoubleByParameter(ServletRequest request, String paramName) {
		return getDoubleByParameter(request, paramName, null);
	}

	/**
	 * 以表单方式,获取Double参数,若无则返回默认值
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @param defaults
	 *            设定的默认值
	 * @return
	 */
	public static Double getDoubleByParameter(ServletRequest request, String paramName, Double defaults) {
		String str = request.getParameter(paramName);
		return NumberUtil.getDouble(str, defaults);
	}

	/**
	 * 以表单方式,获取Float参数,若无则返回Null
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @return
	 */
	public static Float getFloatByParameter(ServletRequest request, String paramName) {
		return getFloatByParameter(request, paramName, null);
	}

	/**
	 * 以表单方式,获取Float参数,若无则返回默认值
	 * 
	 * @param request
	 *            ServletRequest的实例
	 * @param paramName
	 *            参数名字
	 * @param defaults
	 *            设定的默认值
	 * @return
	 */
	public static Float getFloatByParameter(ServletRequest request, String paramName, Float defaults) {
		String str = request.getParameter(paramName);
		return NumberUtil.getFloat(str, defaults);
	}

	/**
	 * 以表单方式,获取Boolean参数,若无则返回Null
	 * 
	 * @param request
	 * @param paramName
	 * 
	 * @return
	 */
	public static Boolean getBooleanByParameter(ServletRequest request, String paramName) {
		return getBooleanByParameter(request, paramName, null);
	}

	/**
	 * 以表单方式,获取Boolean参数,若无则返回默认值
	 * 
	 * @param request
	 * @param paramName
	 * @param defaults
	 * 
	 * @return Boolean
	 */
	public static Boolean getBooleanByParameter(ServletRequest request, String paramName, Boolean defaults) {
		String str = request.getParameter(paramName);
		return NumberUtil.getBoolean(str, defaults);
	}

	
	/**
	 * 获取request的属性值
	 * 
	 * @param request
	 * @param name
	 * 
	 * @return Object
	 */
	public static Object getObjectAttr(ServletRequest request, String attrName) {
		return request.getAttribute(attrName);
	}

	/**
	 * 获取request中的属性,转换为指定的clazz对象
	 * 
	 * @param request
	 * @param attrName
	 * @param clazz
	 * 
	 * @return NULL or T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectAttr(ServletRequest request, String attrName, Class<T> clazz) {
		Object obj = request.getAttribute(attrName);
		if(null == obj){
			return null;
		}
		if(clazz.isAssignableFrom(obj.getClass())){
			return (T) obj;
		}
		return null;
	}

	/**
	 * 获取String类型的属性
	 * 
	 * @param request
	 * @param attrName
	 * 
	 * @return
	 */
	public static String getStringAttr(ServletRequest request, String attrName) {
		Object obj = request.getAttribute(attrName);
		if(obj == null){
			return null;
		}
		return obj.toString();
	}

	/**
	 * 获取int类型的属性
	 * 
	 * @param request
	 * @param attrName
	 * 
	 * @return
	 */
	public static Integer getIntAttr(ServletRequest request, String attrName) {
		return NumberUtil.getInteger(request.getAttribute(attrName));
	}

	/**
	 * 获取long类型的属性
	 * 
	 * @param request
	 * @param attrName
	 * 
	 * @return
	 */
	public static Long getLongAttr(ServletRequest request, String attrName) {
		return NumberUtil.getLong(request.getAttribute(attrName));
	}
	
	/**
	 * 获取short类型的属性
	 * 
	 * @param request
	 * @param attrName
	 * 
	 * @return
	 */
	public static Short getShortAttr(ServletRequest request, String attrName) {
		return NumberUtil.getShort(request.getAttribute(attrName));
	}

	/**
	 * 获取double类型的属性
	 * 
	 * @param request
	 * @param attrName
	 * 
	 * @return
	 */
	public static Double getDoubleAttr(ServletRequest request, String attrName) {
		return NumberUtil.getDouble(request.getAttribute(attrName));
	}

	/**
	 * 获取float类型的属性
	 * 
	 * @param request
	 * @param attrName
	 * 
	 * @return
	 */
	public static Float getFloatAttr(ServletRequest request, String attrName) {
		return NumberUtil.getFloat(request.getAttribute(attrName));
	}

	/**
	 * 获取boolean类型的属性
	 * 
	 * @param request
	 * @param attrName
	 * 
	 * @return
	 */
	public static Boolean getBooleanAttr(ServletRequest request, String attrName) {
		return NumberUtil.getBoolean(request.getAttribute(attrName));
	}
	
	
	/**
	 * 获取Request请求的URI地址
	 * 
	 * @param request
	 * @param skipContextPath 是否过滤ContextPath路径
	 * 
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest request, boolean skipContextPath){
        String uri = request.getRequestURI().trim();
        if(skipContextPath){
        	String contextPath = request.getContextPath().trim();
        	return uri.replaceFirst(contextPath, "");
        }
        return uri;
	}
	
	
	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 *
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String ip = null;
		try {
			ip = request.getHeader("X-Real-IP");
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("X-Forwarded-For");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getRemoteAddr();
			}
			
			if(ip!=null && ip.length()>0) {
				ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
				int index = ip.indexOf(",");
				if (index != -1) {
					ip = ip.substring(0, index);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;
	}

}
