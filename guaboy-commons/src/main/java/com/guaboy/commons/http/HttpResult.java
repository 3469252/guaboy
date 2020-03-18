package com.guaboy.commons.http;

/**
 * Http通讯结果类
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class HttpResult {
	/** Http请求状态码 */
	private int status;
	
	/** Http请求结果 */
	private String result;
	
	/** HTTP请求结果字节数组 */
	private byte[] bytes;
	
	public boolean isOK(){
		return (this.status == 200);
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}

