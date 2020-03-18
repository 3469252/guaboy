package com.guaboy.commons.wrap;

import java.util.HashMap;

import com.guaboy.commons.utils.NumberUtil;
import com.guaboy.commons.utils.StringUtil;


/**
 * HashMap包装对象,增加了快捷取值的方法。
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class MapObject extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings("unchecked")
	public <T> T getObject(String key, Class<T> clazz) {
		Object o = get(key);
		if(o == null) {
			return null;
		}
		if(clazz.isAssignableFrom(o.getClass())) {
			return (T)o;
		}
		return null;
	}
	
	/**
	 * 判断key或value是否为空
	 * @param key
	 * @return
	 */
	public boolean isEmptyKeyValue(String key) {
		if(containsKey(key)) {
			return StringUtil.isEmpty(get(key));
		}
		return true;
	}
	
	/**
	 * 判断key或value是否非空
	 * @param key
	 * @return
	 */
	public boolean isNotEmptyKeyValue(String key) {
		if(containsKey(key)) {
			return StringUtil.isNotEmpty(get(key));
		}
		return false;
	}
	
	
	public String getString(String key) {
		Object o = get(key);
		if(o == null) {
			return null;
		}
		return o.toString();
	}
	
	
	public Integer getInteger(String key) {
		return NumberUtil.getInteger(get(key));
	}
	
	
	public Long getLong(String key) {
		return NumberUtil.getLong(get(key));
	}
	
	
	public Double getDouble(String key) {
		return NumberUtil.getDouble(get(key));
	}
	
	
	public Float getFloat(String key) {
		return NumberUtil.getFloat(get(key));
	}
	
	
	public Short getShort(String key) {
		return NumberUtil.getShort(get(key));
	}
	
	
	public Boolean getBoolean(String key) {
		return NumberUtil.getBoolean(get(key));
	}
	
}
