package com.guaboy.commons.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Https通讯时的域名校验
 * 
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class MyHostnameVerifier implements HostnameVerifier {

	@Override
	public boolean verify(String arg0, SSLSession arg1) {
		return true;
	}

}
