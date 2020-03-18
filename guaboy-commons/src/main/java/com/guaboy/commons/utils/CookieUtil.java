package com.guaboy.commons.utils;

import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class CookieUtil {
	private final HashMap<String, Cookie> cookieMap;
	private final HttpServletResponse response;
	private final HttpServletRequest request;

	public CookieUtil(HttpServletRequest request, HttpServletResponse response) {
		this.response = response;
		this.request = request;
		cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie == null) {
					continue;
				}
				cookieMap.put(cookie.getName(), cookie);
			}
		}
	}

	
	/**
	 * 取得cookie
	 * 
	 * @param name
	 * @return Cookie
	 */
	public Cookie getCookie(String name) {
		return this.cookieMap.get(name);
	}

	
	/**
	 * 获取所有cookie名
	 * 
	 * @return
	 *
	 */
	public String[] getCookieNames() {
		Iterator<String> it = this.cookieMap.keySet().iterator();
		String[] cookieNames = new String[this.cookieMap.size()];
		int i = 0;
		while (it.hasNext()) {
			cookieNames[i] = it.next();
			i++;
		}
		return cookieNames;
	}

	
	/**
	 * 获取所有cookie对象
	 * 
	 * @return
	 *
	 */
	public Cookie[] getCookies() {
		Iterator<Cookie> it = this.cookieMap.values().iterator();
		Cookie[] cookies = new Cookie[this.cookieMap.size()];
		int i = 0;
		while (it.hasNext()) {
			cookies[i] = it.next();
			i++;
		}
		return cookies;
	}

	
	/**
	 * 取得指定cookie的值
	 * 
	 * @param name
	 * @return String
	 */
	public String getCookieValue(String name) {
		Cookie cookie = this.cookieMap.get(name);
		return cookie == null ? null : cookie.getValue();
	}

	
	/**
	 * 设置cookie
	 * 
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 * @param domain
	 *            域名
	 * @param maxAge
	 *            有效时间,秒
	 * @param path
	 *            路径
	 */
	public void setCookie(String name, String value, String domain, int maxAge, String path) {
		Cookie cookie = getCookie(name);
		if (cookie == null) {
			cookie = new Cookie(name, value);
			this.cookieMap.put(name, cookie);
		} else {
			cookie.setValue(value);
		}

		// 只设置本域的cookie
		String serverName = request.getServerName();
		if (!serverName.endsWith(domain)) {
			return;
		}

		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}

}
