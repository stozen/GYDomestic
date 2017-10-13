package com.gy.message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;*/

import com.alibaba.fastjson.JSONObject;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-13
 * @introduce 这是国内短信平台
 */

public class DomesticMessage {
	
	/**
	 * 声明用户账号UTF-8格式
	 */
	private final static String USER = "13402040612";
	
	/**
	 * 声明用户密码
	 */
	private static final String PWD = "yd123456";
	
	/**
	 * 声明合法的手机号码
	 */
	private String phone;
	
	/**
	 * 声明国家代号
	 */
	private static final String MID = "86";
	
	/**
	 * 声明请求的地址
	 */
	private final static String SEND_URL = "http://dy.renxinl.com:8443/wtemplatesend.do";
	
	public DomesticMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public static void sentMessageCode() {
		// TODO Auto-generated method stub
		try {
	        //创建连接
	        URL url = new URL(SEND_URL);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	       /* connection.setRequestProperty("Authorization", token);*/
	        connection.setUseCaches(false);
	        connection.setInstanceFollowRedirects(true);
	        connection.setRequestProperty("Content-Type","application/json");
	        connection.connect();

	        //POST请求
	        DataOutputStream out = new DataOutputStream(
	                connection.getOutputStream());
	        JSONObject obj = new JSONObject();
	        obj.put("user", USER);
	        obj.put("pwd", PWD);
	        obj.put("phone", "15900785383");
	        obj.put("mid", "86");
	       
	        out.writeBytes(obj.toString());
	        out.flush();
	        out.close();

	        //读取响应
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                connection.getInputStream()));
	        String lines;
	        StringBuffer sb = new StringBuffer("");
	        
	        while ((lines = reader.readLine()) != null) {
	            lines = new String(lines.getBytes(), "utf-8");
	            sb.append(lines);
	            /*map.put("data", sb);*/
	            String capture = new String(sb);
	            JSONObject paydata = JSONObject.parseObject(capture);
	            String state = (String) paydata.get("state");
	            System.err.println(capture);
	        }
	        reader.close();
	        // 断开连接
	        connection.disconnect();
	    } catch (MalformedURLException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    } catch (UnsupportedEncodingException e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    } catch (IOException e3) {
	        // TODO Auto-generated catch block
	        e3.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		sentMessageCode();
	}
}
