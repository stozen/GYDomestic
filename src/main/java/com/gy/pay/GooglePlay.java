package com.gy.pay;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gy.services.OrderService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是GooglePlay支付方式放回的订单处理结果
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value="/googleplay")
public class GooglePlay {
	
	/*1.开启Googleplay支付*/
	
	/**
	 * 实现自动注入
	 */
	@Autowired
	private OrderService orderService;
	
	/**
	 * 创建返回给客户端的状态信息
	 */
	private String status;
	
	/**
	 * 创建返回给客户端的信息提示
	 */
	private String message;
	
	
	/**
	 * 生成orderService的get方法
	 * @return
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	/**
	 * 生成orderService的set方法
	 * @param orderService
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@RequestMapping(value="/checkorder",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkOrder(@RequestBody Map map,@RequestHeader String token) {
		// TODO Auto-generated method stub
		String packageName = (String) map.get("packageName");
		String subscriptionId = (String) map.get("subscriptionId");
		String accessToken = (String) map.get("accessToken");
		
		/*String packageName = "com.youda.android.demo";
		String subscriptionId = "com.youda.product0001";
		String token = "cecgnionbjnlccillajbiggo.AO-J1Ox4ADyvYEdSkcUQxhhRZjePVu1weX7CoQHGW_2f3qWaft-QQAUFPXa_iL-0VZLTMrnyaCgQMppwmrTi_n7AT-bwsIt_8kdpn-uQm_dutXr6eLr1GWVKiwO1hmhmuWN9hMK8a2jf";*/
	
		final String ADD_URL = "https://www.googleapis.com/androidpublisher/v2/applications/"+packageName+"/purchases/subscriptions/"+subscriptionId+"/tokens/"+accessToken;
		
		/*final String ADD_URL = "https://hao.360.cn/";*/
		
		try {
            //创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            /*connection.setRequestProperty("Authorization", "Bearer");
            connection.setRequestProperty("packageName", packageName);*/
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.connect();

            //POST请求
            /*DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.put("packageName", packageName);
            obj.put("subscriptionId", subscriptionId);
            obj.put("token", token);

            out.writeBytes(obj.toString());
            out.flush();
            out.close();*/

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
                
                String capture = new String(sb);
                JSONObject paydata = JSONObject.parseObject(capture);
                Integer paymentState = (Integer) paydata.get("paymentState");
                if(paymentState == 0)
                {
                	status = "0404";
                	message = "付款还有成功待支付状态！";
                	map.put("status", status);
                	map.put("message", message);
                }
                else if(paymentState==1)
                {
                	status = "0200";
                	message = "支付成功！";
                	map.put("status", status);
                	map.put("message", message);
                	map.put("paydata", paydata);
                } 
                else if(paymentState==2)
                {
                	status = "0200";
                	message = "免费试用！";
                	map.put("status", status);
                	map.put("message", message);
                	map.put("paydata", paydata);
                }
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		map.remove("packageName");
		map.remove("subscriptionId");
		map.remove("token");
		map.remove("accessToken");
		return map;
	}
	
}
