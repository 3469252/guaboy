package com.guaboy.commons.wrap;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;



/**
 * 可重复读取流的HttpServletRequest
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class HttpServletRequestReader extends HttpServletRequestWrapper {
	private final byte[] body;
	
	public HttpServletRequestReader(HttpServletRequest request) {
		super(request);
		this.body = getByInputStream(request);
	}
	
	private byte[] getByInputStream(HttpServletRequest request) {
		byte[] bts = new byte[0];
		InputStream input = null;
		try {
			input = request.getInputStream();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = input.read(buffer)) > 0) {
				baos.write(buffer, 0, length);
			}
			bts = baos.toByteArray();
			
			input.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return bts;
	}
	
	public byte[] getBody() {
		return body;
	}
	

	@Override
	public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        ServletInputStream sis = new ServletInputStream() {
        	private boolean finished = false;

			@Override
			public int read() throws IOException {
				int data = bais.read();
				if (data == -1) {
					this.finished = true;
				}
				return data;
			}
        	
			@Override
			public int available() throws IOException {
				return bais.available();
			}

			@Override
			public void close() throws IOException {
				super.close();
				bais.close();
			}

			@Override
			public boolean isFinished() {
				return this.finished;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				throw new UnsupportedOperationException();
			}
        };
        return sis;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	
}
