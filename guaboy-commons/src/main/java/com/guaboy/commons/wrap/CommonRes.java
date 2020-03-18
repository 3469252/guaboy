package com.guaboy.commons.wrap;

import java.io.Serializable;

/**
 * 通用数据响应结构
 *
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class CommonRes<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 响应码 */
	private String code;
	
	/** 响应信息 */
	private String msg;
	
	/** 数据体 */
	private T data;

	
	public CommonRes() {
	}
	
	
	private CommonRes(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data= data;
	}
	
	
	/**
	 * 构造一个CommonRes
	 * 
	 * @param coder Coder实例
	 * @return
	 */
	public static <T> CommonRes<T> makeRes(Coder coder){
		return new CommonRes<T>(coder.id(), coder.desc(), null);
	}
	
	
	/**
	 * 构造一个CommonRes
	 * 
	 * @param coder Coder实例
	 * @param data 数据体
	 * @return
	 */
	public static <T> CommonRes<T> makeRes(Coder coder, T data){
		return new CommonRes<T>(coder.id(), coder.desc(), data);
	}
	
	
	/**
	 * 构造一个CommonRes
	 * @param code 自定义响应码
	 * @param msg 自定义响应信息
	 * @return
	 */
	public static <T> CommonRes<T> makeRes(String code, String msg){
		return new CommonRes<T>(code, msg, null);
	}
	
	
	/**
	 * 构造一个CommonRes
	 * @param code 自定义响应码
	 * @param msg 自定义响应信息
	 * @param data 数据体
	 * @return
	 */
	public static <T> CommonRes<T> makeRes(String code, String msg, T data){
		return new CommonRes<T>(code, msg, data);
	}
	
	
	/**
	 * 判断当前code是否与传参对象的id值,匹配
	 * 
	 * @param coder 响应码实例对象
	 * @return
	 */
	public boolean isEquals(Coder coder) {
		return this.code.equalsIgnoreCase(coder.id());
	}
	
	
	/**
	 * 判断当前code是否与传参对象的id值,不匹配
	 * 
	 * @param coder 响应码实例对象
	 * @return
	 */
	public boolean isNotEquals(Coder coder) {
		return !isEquals(coder);
	}
	
	
	/**
	 * 重写后的equals,可直接对比两个CommonRes的属性是否一样
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		CommonRes<T> pr = null;
		try{
			pr = (CommonRes<T>) o;
		} catch(Exception e) {
		}
		if (pr == null) {
			return false;
		}
		return this.code == pr.code && eq(this.msg, pr.msg) && eq(this.data, pr.data);
	}
	private static <T> boolean eq(T o1, T o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
