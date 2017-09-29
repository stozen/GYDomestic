package com.gy.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.model.Game;
import com.gy.model.Goods;
import com.gy.model.Order;
import com.gy.model.OrderGoods;
import com.gy.model.User;
import com.gy.services.GameService;
import com.gy.services.GoodsService;
import com.gy.services.OrderGoodsService;
import com.gy.services.OrderService;
import com.gy.services.UserService;
import com.gy.servicesImpl.OrderGoodsServiceImpl;
import com.gy.util.PrimaryGenerater;
import com.gy.util.RandomCode;


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
	 * 创建订单服务
	 */
	@Autowired
	private OrderService orderService;
	
	/**
	 * 生成订单服务的get方法
	 * @return
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	/**
	 * 生成订单服务的set方法
	 * @param orderService
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * 这是一个查询订单的功能,一般查询是GET请求，获得信息
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> queryOrder(@RequestHeader String orderid){
		Map<String, Object> map = new HashMap<String, Object>();
		
		orderService.query(orderid, map);
		
		return map;
	}
	
	/**
	 * 这是一个删除订单的功能，一般删除是DELETE请求，删除信息
	 * @param order
	 * @param bindingResult
	 * @return 
	 */
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteOrder(@RequestBody Order order,@RequestHeader String token){
		Map<String,Object> map = new HashMap<String,Object>();
		
		orderService.delete(order, token, map);
		
		return map;
	}
	
	/**
	 * 这是一个更新订单个功能，一般更新采用是PUT请求，更新信息
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/modify",method=RequestMethod.PUT)
	public @ResponseBody Map<String, Object> modifyOrder(@RequestBody Order order,@RequestHeader String token){
		Map<String, Object> map = new HashMap<String, Object>();
		
		orderService.modify(order, token, map);
		
		return map;
	}
	
	/**
	 * 这是一个更新订单个功能，一般更新采用是PUT请求，更新信息
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/cancel",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> cancelOrder(@RequestBody Order order,@RequestHeader String token){
		Map<String, Object> map = new HashMap<String, Object>();
		
		orderService.cancel(order, token, map);
		
		return map;
	}
	
	/**
	 * 这是一个添加订单个功能，一般更新采用是POST请求，添加信息
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> createOrder(@RequestBody com.gy.model.Order order,@RequestHeader String token){
		Map<String, Object> map = new HashMap<String, Object>();
		
		orderService.create(order, token, map);
		
		return map;
	}
	
}
