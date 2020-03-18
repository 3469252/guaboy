package com.guaboy.commons.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.guaboy.commons.utils.StringUtil;


/**
 * JAVA原生Http通讯工具封装类,不依赖第三方JAR<br>
 * 
 * <b>使用示范:</b><br>
 * HttpURLConnection conn = HttpUtil.createConnection(url);<br>
 * HttpResult httpResp = HttpUtil.post(conn,params);<br>
 * if(httpResp.isOK()){<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * String result = httpResp.getResult();<br>
 * }<br>
 * 
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class HttpUtil {
	
	public final static String ContentType_Form = "application/x-www-form-urlencoded";
	public final static String ContentType_Json = "application/json";
	public final static String ContentType_Xml = "application/xml";
	public final static String ContentType_Html = "text/html";
	public final static String ContentType_Text = "text/plain";
	

	/**
	 * 创建连接,支持http或https协议
	 * 
	 * @param url
	 * 
	 * @return HttpURLConnection
	 * 
	 * @throws Exception 
	 *
	 */
	public static HttpURLConnection createConnection(String url) throws Exception {
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setConnectTimeout(10000);        //连接超时,10秒
			conn.setReadTimeout(30000);			  //读写超时,30秒
			conn.setDoInput(true);				  //可读
			conn.setDoOutput(true); 	          //可写
			conn.setUseCaches(false);             //不使用缓存
			if("https".equalsIgnoreCase(u.getProtocol())){
				TrustManager[] xtm = { new MyX509TrustManager()};
				HostnameVerifier hv = new MyHostnameVerifier();
				
				HttpsURLConnection hsconn = (HttpsURLConnection)conn;
				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init( null, xtm, null );
				
				hsconn.setSSLSocketFactory(sslContext.getSocketFactory());
				hsconn.setHostnameVerifier(hv);
				return hsconn;
			}
			return conn;
		} catch (Exception e) {
			throw new RuntimeException("创建HttpURLConnection失败", e);
		}
	}

	
	/**
	 * 发起POST,以表单方式提交数据
	 * 
	 * @param conn
	 * 
	 * @param formData
	 * 
	 * @return HttpResult
	 *
	 */
	public static HttpResult postForm(HttpURLConnection conn, Map<String,Object> formData){
		return postForm(conn, formData, null);
	}
	
	
	/**
	 * 指定编码,发起POST,以表单方式提交数据
	 * 
	 * @param conn
	 * @param formData
	 * @param charsetName
	 *
	 * @return HttpResult
	 *
	 */
	public static HttpResult postForm(HttpURLConnection conn, Map<String,Object> formData, String charsetName){
		String paramStr = "";
		StringBuilder sb = new StringBuilder();
		if(formData != null){
			Set<Entry<String,Object>> entrySet = formData.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String k = entry.getKey();
				Object v = entry.getValue();
				if(k==null || v == null) {
					continue;
				}
				sb.append(k).append("=").append(v.toString()).append("&");
			}
			
			//去掉结尾的&
			paramStr = StringUtil.removeLast(sb.toString(), "&");
		}
		conn.setRequestProperty("Content-Type", ContentType_Form);
		return postData(conn, paramStr, charsetName);
	}
	
	
	/**
	 * 发起POST提交数据
	 * 
	 * @param conn
	 * @param postData
	 * 
	 * @return HttpResult
	 *
	 */
	public static HttpResult postData(HttpURLConnection conn, String postData){
		return postData(conn, postData, null);
	}
	
	
	/**
	 * 指定编码,发起POST提交数据
	 * 
	 * @param conn
	 * @param postData
	 * @param charsetName
	 * 
	 * @return
	 *
	 */
    public static HttpResult postData(HttpURLConnection conn, String postData, String charsetName){
    	HttpResult httpResult = new HttpResult();
    	try{
    		conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            Charset cst = null;
			try {
				cst = Charset.forName(charsetName);
			} catch (Exception e) {
			}
            if(cst == null){
            	os.write(postData.getBytes());
            }else{
            	os.write(postData.getBytes(cst));
            }
            
            int respCode = conn.getResponseCode();
            if( respCode == 200 ){
            	InputStream is = conn.getInputStream();
            	readResult(httpResult, is, charsetName);
            }
            httpResult.setStatus(respCode);
            
            try {
            	os.flush();
            	os.close();
            } catch (Exception e) {
            }
        } catch ( Exception e ) {
        	httpResult.setStatus(0);
        	httpResult.setResult("POST通讯异常");
        	e.printStackTrace();
        } finally {
    		if(conn != null){
    			conn.disconnect();
    		}
		}
    	return httpResult;
    }
    
    
    /**
     * 发起Get请求
     * 
     * @param conn
     * 
     * @return HttpResult
     *
     */
    public static HttpResult get(HttpURLConnection conn){
    	return get(conn, null);
    }
	
	
	/**
	 * 发起Get请求,可指定编码
	 * 
	 * @param conn
	 * @param charsetName
	 * 
	 * @return HttpResult
	 *
	 */
	public static HttpResult get(HttpURLConnection conn, String charsetName){
		HttpResult httpResult = new HttpResult();
		try {
			conn.setRequestMethod("GET");
			conn.connect();
			
            int respCode = conn.getResponseCode();
            if(respCode == 200){
            	InputStream is = conn.getInputStream();
            	readResult(httpResult, is, charsetName);
            }
            httpResult.setStatus(respCode);
        } catch ( Exception e ) {
        	httpResult.setStatus(0);
        	httpResult.setResult("GET通讯异常");
        	e.printStackTrace();
        }  finally {
    		if(conn != null){
    			conn.disconnect();
    		}
		}
    	return httpResult;
	}
	
	
	private static void readResult(HttpResult httpResult, InputStream inputStream, String charsetName) {
        Charset cst = null;
		try {
			cst = Charset.forName(charsetName);
		} catch (Exception e) {
		}
		
		String resultStr = "";
		byte[] bts = new byte[0];
		//字节流方式
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[2048];
		int length;
		try {
			while ((length = inputStream.read(buffer)) > 0) {
				result.write(buffer, 0, length);
			}
			if(cst == null){
				resultStr = result.toString();
			}else{
				resultStr = result.toString(charsetName);
			}
			bts = result.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				result.close();
			} catch (IOException e) {
			}
		}
		httpResult.setResult(resultStr);
		httpResult.setBytes(bts);
	}

}
