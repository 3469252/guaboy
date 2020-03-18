package com.guaboy.commons.wrap;

/**
 * 带响应码的运行时异常
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class ResException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/** 响应码 */
	private String code;

	
	public ResException(String errCode, String errMsg) {
		super(errMsg);
		this.code = errCode;
	}
	
	public ResException(String errCode, String errMsg, Throwable th) {
		super(errMsg, th);
		this.code = errCode;
	}
	
	public ResException(Coder coder) {
		super(coder.desc());
		this.code = coder.id();
	}

	public ResException(Coder coder, String errMsg) {
		super(errMsg);
		this.code = coder.id();
	}
	
	public String getErrCode() {
		return this.code;
	}
	
}
