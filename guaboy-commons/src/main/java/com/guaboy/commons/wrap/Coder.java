package com.guaboy.commons.wrap;


/**
 * 响应码对象
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class Coder {
	private String id;
	private String desc;
	
	public Coder(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}
	
	/**
	 * 获取响应码的ID
	 * @return
	 */
	public String id() {
		return this.id;
	}
	
	/**
	 * 获取响应码的描述
	 * @return
	 */
	public String desc() {
		return this.desc;
	}
	
}
