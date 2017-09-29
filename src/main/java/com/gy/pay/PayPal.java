package com.gy.pay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	/*1.开启Paypal支付功能*/
	
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
	 * 只是使用paypal支付功能
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="pay",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> paypal(@RequestBody Map map,BindingResult bindingResult){
		
		String reqprice = (String)map.get("price");
		String reqnumber = (String)map.get("number");
		
		double price = Double.parseDouble(reqprice);
		int number = Integer.parseInt(reqnumber);
		double total = price*number;
		map.put("price", price);
		map.put("number", number);
		map.put("total", total);
		return map;
	}
	
	@RequestMapping(value="/recharge",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> recharge(@RequestBody Map map,BindingResult bindingResult){
		// Replace these values with your clientId and secret. You can use these to get started right now.
        String clientId = "AVlFMZHdBmThdIZTgtEkGpQYO96RxnbUWKm_GrFRVqFkA6q-iFaAmgl1Ae3UwpWBEcmNfjBbiWTwuvry";
        String clientSecret = "EBqDDRk8SwoJgfUYMzcdib9PhfqNSHu8hXbpZ6Uu_sXD5X3ITPF9aheYxUPHMqjPNsjnlnMAionavJW7";

        // Create a Credit Card
        CreditCard card = new CreditCard()
                .setType("visa")
                .setNumber("4417119669820331")
                .setExpireMonth(11)
                .setExpireYear(2019)
                .setCvv2(012)
                .setFirstName("Joe")
                .setLastName("Shopper");
        try {
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            card.create(context);
            System.out.println(card);
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
        
		return map;
	}
	
	@RequestMapping(value="/check",method=RequestMethod.POST)
	public void checkOrder() {
		// TODO Auto-generated method stub
		final String ADD_URL = "https://api.sandbox.paypal.com/v1/payments/orders/O-3SP845109F051535C";
		try {
            //创建连接
            URL url = new URL(ADD_URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.put("amount", "asdf");
            obj.put("app_ip", "10.21.243.234");
            obj.put("app_port", 8080);
            obj.put("is_final_capture", true);
            obj.put("app_area", "asd");

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
            }
            System.out.println(sb);
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
	}
}
