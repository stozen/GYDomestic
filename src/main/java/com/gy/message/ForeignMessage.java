package com.gy.message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-13
 * @introduce 这是国际短信平台
 */

@Controller
@RequestMapping(value="/getForeignCode")
public class ForeignMessage {
		
	/**
	 * 声明用户账号UTF-8格式
	 */
	private static String USER = "13402040612";
	
	/**
	 * 声明用户密码
	 */
	private final static String PWD = "abc85410d238d4b5bae2ea3830e3d787";
	
	/**
	 * 声明合法的手机号码
	 */
	/*private static String phone = "15900627595";*/
	
	/**
	 * 声明国家代码
	 */
	/*private static String CountryCode;*/
	
	/**
	 * 声明国家代号
	 */
	private final static String MID = "14337";
	
	/**
	 * 声明随机验证码
	 */
	private static String VerificationCode = String.valueOf(MessageCode.getRandNum());
	
	/**
	 * 声明返回生成的验证码
	 * @return
	 */
	public static String getVerificationCode() {
		return VerificationCode;
	}

	/**
	 * 声明生成的验证码
	 * @param validateCode
	 */
	public static void setVerificationCode(String VerificationCode) {
		DomesticMessage.VerificationCode = VerificationCode;
	}

	
	/*http://apis.renxinl.com:8080/smsgate/varsend.do?user=13402040612&pwd=abc85410d238d4b5bae2ea3830e3d787&params=15900785383,【YCGAME】1234&mid=14337*/	
	
	@RequestMapping(value="/send",method=RequestMethod.GET)
	public static @ResponseBody Map<String, Object> sentMessageCode(@RequestParam String countryCode,@RequestParam String phone) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 声明请求的地址
		 */
		String SEND_URL = "http://apis.renxinl.com:8080/smsgate/wtemplatesend.do?"+"user="+USER+"&"+"pwd="+PWD+"&"+"phone="+countryCode+phone+","+VerificationCode+"&"+"mid="+MID;
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
	            JSONObject codeData = JSONObject.parseObject(capture);
	            String code = (String) codeData.get("code");
	            if(code.equals("0000"))
	            {
	            	map.put("status", "0200");
	            	map.put("message", "验证码发送成功!");
	            	map.put("detail", codeData);
	            }
	            else
	            {
	            	map.put("status", "0404");
	            	map.put("message", "验证码发送失败!");
	            	map.put("detail", codeData);
	            }
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
		return map;
	}
}
