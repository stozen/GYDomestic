package com.gy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.Token;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是订单控制器
 * @date 2017.9.13
 */
@Controller
@RequestMapping(value="/order")
public class OrderControl {
	
	/**
	 * 这是一个查询订单的功能,一般查询是GET请求，获得信息
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> queryOrder(@RequestBody com.gy.model.Order order){
		Map<String, Object> map = new HashMap<String, Object>();
			
		return map;
	}
	
	/**
	 * 这是一个删除订单的功能，一般删除是DELETE请求，删除信息
	 * @param order
	 * @param bindingResult
	 * @return 
	 */
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteOrder(@RequestBody com.gy.model.Order order){
		Map<String,Object> map = new HashMap<String,Object>();
		
		return map;
	}
	
	/**
	 * 这是一个更新订单个功能，一般更新采用是PUT请求，更新信息
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateOrder(@RequestBody com.gy.model.Order order){
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	/**
	 * 这是一个添加订单个功能，一般更新采用是POST请求，添加信息
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/crate",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> createOrder(@RequestBody com.gy.model.Order order,@RequestHeader String token){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("payment", order.getPayment());
		map.put("ordergoods",order.getOrdergoods());
		map.put("token", token);
		return map;
	}
	
}
