package com.gy.pay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.services.OrderService;
import com.paypal.api.payments.CreditCard;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

/**
 * @author Administrator
 * @date 2017.9.11
 */
@Controller
@RequestMapping(value="/paypal")
public class PayPal {

	/**
	 * 实现自动注入
	 */
	@Autowired
	private OrderService orderService;
	
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
	
	/**
	 * 创建返回给客户端的状态信息
	 */
	private String status;
	
	/**
	 * 创建返回给客户端的信息提示
	 */
	private String message;
	
	
	@RequestMapping(value="/checkorder",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkOrder(@RequestBody Map map,@RequestHeader String token) {
		// TODO Auto-generated method stub
		String orderid = ((String) map.get("orderid")).trim();
		
		LinkedHashMap amount = (LinkedHashMap)map.get("amount");
		String currency = (String) amount.get("currency");
		String total = (String) amount.get("total");
		boolean is_final_capture = true;
		
		final String ADD_URL = "https://api.sandbox.paypal.com/v1/payments/orders/"+"'"+orderid+"'";	
		/*final String ADD_URL = "http://localhost:8080/GYForeign/user/login";*/
		
		/*这是paypal的沙箱返查支付信息
		https://api.sandbox.paypal.com/v1/payments/orders/O-3SP845109F051535C*/		
		try {
            //创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", token);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.put("amount", amount);
            obj.put("is_final_capture", is_final_capture);

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
                if(state.equals("") || "".equals(state))
                {
                	status = "0404";
                	message = "没有获取到付款信息！";
                	map.put("status", status);
                	map.put("message", message);
                }
                else if(state.equals("completed"))
                {
                	status = "0200";
                	message = "支付成功！";
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
		
		map.remove("amount");
		map.remove("orderid");
		return map;
	}
	
	
	@RequestMapping(value="/getorder",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> getOrder(@RequestHeader String orderid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String,Object>();
		
		/*final String ADD_URL = "https://api.sandbox.paypal.com/v1/payments/orders/O-3SP845109F051535C";*/
		final String ADD_URL = "http://localhost:8080/GYForeign/order/query";
		
		/*这是paypal的沙箱返查支付信息
		https://api.sandbox.paypal.com/v1/payments/orders/O-3SP845109F051535C*/		
		try {
            //创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("orderid", orderid);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.connect();

            //POST请求
            /*DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.put("username", username);
            obj.put("password", password);
            obj.put("type", type);

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
               /* String capture_tojson = JSON.toJSONString(capture, true);*/
                JSONObject paydata = JSONObject.parseObject(capture);
                Integer goodid = (Integer) paydata.get("goodid");
                if(goodid==1)
                {
                	status = "0200";
                	message = "付款成功！";
                	map.put("status", status);
                	map.put("message", message);
                }
                else
                {
                	status = "0404";
                	message = "付款失败！";
                	map.put("status", status);
                	map.put("message", message);
                	map.put("paydata",paydata);
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
		
		return map;
	}
}
