package com.gy.message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;*/

import com.alibaba.fastjson.JSONObject;
import com.gy.model.VerificationCode;
import com.gy.services.VerificationCodeService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-13
 * @introduce 这是国内短信平台
 */

@Controller
@CrossOrigin(maxAge=3600,origins="*")
@RequestMapping(value="/getDomesticCode")
public class DomesticMessage {
	
	/**
	 * 声明用户账号UTF-8格式
	 */
	private static String USER = "13402040612";
	
	/**
	 * 声明用户密码
	 */
	private final static String PWD = "abc85410d238d4b5bae2ea3830e3d787";
	
	/**
	 * 声明国家代号
	 */
	private final static String MID = "14341";
	
	/*int min = 0;
	int max = 999999;
    int randNum = min + (int)(Math.random() * ((max - min) + 1));*/
    
	@Autowired
	private VerificationCodeService verificationCodeService;
    
	private VerificationCode verificationCode;
	
    public VerificationCodeService getVerificationCodeService() {
		return verificationCodeService;
	}

	public void setVerificationCodeService(
			VerificationCodeService verificationCodeService) {
		this.verificationCodeService = verificationCodeService;
	}
    
	/*http://apis.renxinl.com:8080/smsgate/varsend.do?user=13402040612&pwd=abc85410d238d4b5bae2ea3830e3d787&params=15900785383,【YCGAME】1234&mid=14337*/	

	@RequestMapping(value="/send",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> sentMessageCode(@RequestParam String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.err.println("电话："+phone);
		verificationCode = verificationCodeService.querysql("from VerificationCode vc where vc.mobile ="+"'"+phone+"'");
		if(verificationCode!=null)
		{
			verificationCodeService.delete(verificationCode.getVerificationCodeId());
			verificationCode = new VerificationCode();
			verificationCode.setMobile(phone);
			verificationCode.setCreateTime(new Date());
			int delete_randomNum = (int)((Math.random()*9+1)*100000);
			String delete_update_code = String.valueOf(delete_randomNum);
			System.err.println("删除插入验证码："+delete_update_code);
			verificationCode.setVerificationCode(delete_update_code);
			verificationCodeService.save(verificationCode);
			
			/**
			 * 声明请求的地址
			 */
			String SEND_URL = "http://apis.renxinl.com:8080/smsgate/varsend.do?"+"user="+USER+"&"+"pwd="+PWD+"&"+"params="+phone+","+delete_update_code+"&"+"mid="+MID;
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
		}
		else
		{
			verificationCode = new VerificationCode();
			verificationCode.setMobile(phone);
			verificationCode.setCreateTime(new Date());
			int insert_randomNum = (int)((Math.random()*9+1)*100000);
			String insert_code = String.valueOf(insert_randomNum);
			System.err.println("直接插入验证码："+insert_code);
			verificationCode.setVerificationCode(insert_code);
			verificationCodeService.save(verificationCode);
			System.err.println("直接调用插入短信验证码");
			
			/**
			 * 声明请求的地址
			 */
			String SEND_URL = "http://apis.renxinl.com:8080/smsgate/varsend.do?"+"user="+USER+"&"+"pwd="+PWD+"&"+"params="+phone+","+insert_code+"&"+"mid="+MID;
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
		}
		
		return map;
	}
	
}
