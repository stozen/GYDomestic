package com.gy.pay;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.services.OrderService;

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
}
