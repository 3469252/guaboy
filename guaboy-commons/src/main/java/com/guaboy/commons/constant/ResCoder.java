package com.guaboy.commons.constant;

import com.guaboy.commons.wrap.Coder;

/**
 * 通用返回码常量类,项目自定义业务coder可集成该类,建议从1000开始
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE 
 *
 */
public class ResCoder {
	public final static Coder FAIL = new Coder("9999", "失败");
	public final static Coder SUCCESS = new Coder("0000", "成功");
	public final static Coder HANDLE = new Coder("0010", "处理中");
	public final static Coder UN_LOGIN = new Coder("0020", "未登录系统");
	public final static Coder UN_AUTH = new Coder("0030", "未获得授权");
	public final static Coder SYS_ERROR = new Coder("0040", "系统繁忙");
	public final static Coder PARAM_ERROR = new Coder("0050", "参数错误");
	public final static Coder DATA_EXIST = new Coder("0060", "数据已存在");
	public final static Coder DATA_NOT_EXIST = new Coder("0070", "数据不存在");
}
