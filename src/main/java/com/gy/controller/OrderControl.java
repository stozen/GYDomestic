package com.gy.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
	 * 创建商品服务
	 */
	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 创建订单详情服务
	 */
	@Autowired
	private OrderGoodsService orderGoodsService;

	/**
	 * 创建用户服务
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 创建游戏服务接口
	 */
	@Autowired
	private GameService gameService;
	
	/**
	 * 创建返回给客户端的状态信息
	 */
	private String status;
	
	/**
	 * 创建返回客户端的信息提示
	 */
	private String message;
	
	/**
	 * 创建返回给客户端订单编号
	 */
	private int orderid;
	
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
	 * 实现商品服务层的get方法
	 * @return
	 */
	public GoodsService getGoodsService() {
		return goodsService;
	}

	/**
	 * 实现商品服务层的set方法
	 * @return
	 */
	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}
	
	/**
	 * 实现用户服务层的get方法
	 * @return
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * 实现用户服务层的set方法
	 * @return
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 实现订单详情服务层的get方法
	 * @return
	 */
	public OrderGoodsService getOrderGoodsService() {
		return orderGoodsService;
	}

	/**
	 * 实现订单详情服务层的set方法
	 * @return
	 */
	public void setOrderGoodsService(OrderGoodsService orderGoodsService) {
		this.orderGoodsService = orderGoodsService;
	}
	
	/**
	 * 实现游戏服务层的get方法
	 * @return
	 */
	public GameService getGameService() {
		return gameService;
	}

	/**
	 * 实现游戏服务层的set方法
	 * @return
	 */
	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	/**
	 * 这是一个查询订单的功能,一般查询是GET请求，获得信息
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> queryOrder(@RequestBody Order order){
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
	public @ResponseBody Map<String, Object> deleteOrder(@RequestBody Order order,@RequestHeader String token){
		Map<String,Object> map = new HashMap<String,Object>();
		
		/*1.首先创建订单包含用户信息，商品信息，游戏信息，订单详情信息*/
		/*2.先获得用户信息*/
		User user = order.getUser();
		System.out.println(user.getUserid());
		/*3.再获得商品信息*/
		Set<OrderGoods> ordergoods = order.getOrdergoods();
		OrderGoods ordergood = new OrderGoods();
		Iterator it = ordergoods.iterator();
		while (it.hasNext()) {
			ordergood = (OrderGoods) it.next();
			/*ordergood.setOrder(order);*/
		}
		Goods goods = new Goods();
		goods.setGoodsname(ordergood.getTitle());
		goods.setGoodsnumber(ordergood.getNumber());
		goods.setGoodsprice(ordergood.getPrice());
		goods.setGoodstotal(ordergood.getTotalprice());
		goods.setGoodspicture(ordergood.getPicpath());
		goods.setUser(user);
		/*goodsService.delete(goods);*/
		
		System.err.println("创建Ordergoodsservice服务："+orderGoodsService);
		
		System.err.println("订单详情："+ordergood);
		System.err.println(goods.getGoodsname()+goods.getGoodsprice());
		/*4.最后保存订单信息，用户信息，订单详情信息*/
		/*order.setUser(order.getUser());*/
		order.setClosetime(new Date());
		order.setCreatetime(new Date());
		order.setEndtime(new Date());
		order.setOrdergoods(order.getOrdergoods());
		order.setPayment(order.getPayment());
		order.setPaytype(order.getPaytype());
		order.setPaytime(new Date());
		order.setSerialnumber("12345646454");
		order.setUpdatetime(new Date());
		
		ordergood.setGoods(goods);
		/*5.保存订单详情里面的orderid*/
		/*这个还没有实现*/
		/*ordergood.setOrder(order);*/
		
		orderService.create(order);
		
		/*ordergood.setOrder(order);*/
		orderGoodsService.create(ordergood);
		
		map.put("payment", order.getPayment());
		map.put("ordergoods",order.getOrdergoods());
		map.put("token", token);
		map.put("status", order.getPaystatus());
		
		return map;
	}
	
	/**
	 * 这是一个更新订单个功能，一般更新采用是PUT请求，更新信息
	 * @param order
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/modify",method=RequestMethod.PUT)
	public @ResponseBody Map<String, Object> modifyOrder(@RequestBody Order order){
		Map<String, Object> map = new HashMap<String, Object>();
		
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
		/*1.首先创建订单包含用户信息，商品信息，游戏信息，订单详情信息*/
		/*2.先获得用户信息*/
		User user = order.getUser();
		Game game = order.getGames();
		/*String sql = "from Game where userid="+"'"+user.getUserid()+"'"+"and gameid="+"'"+game.getGameid()+"'";
		game = gameService.queryBysql(sql);*/
		user = userService.query(user.getUserid());

		
		if(user == null)
		{
			status = "0404";
			message = "取消订单失败，不存在该用户！";
			orderid = 0;
			map.put("status", status);
			map.put("message", message);
			map.put("orderid", orderid);
		}
		else
		{
			/*3.再获得商品信息*/
			Set<OrderGoods> ordergoods = order.getOrdergoods();
			OrderGoods ordergood = new OrderGoods();
			Iterator it = ordergoods.iterator();
			while (it.hasNext()) {
				ordergood = (OrderGoods) it.next();
			}
			Goods goods = new Goods();
			goods.setGoodsname(ordergood.getTitle());
			goods.setGoodsnumber(ordergood.getNumber());
			goods.setGoodsprice(ordergood.getPrice());
			goods.setGoodstotal(ordergood.getTotalprice());
			goods.setGoodspicture(ordergood.getPicpath());
			goods.setUser(user);
			goodsService.save(goods);
			

			/*4.最后保存订单信息，用户信息，订单详情信息*/
			order.setClosetime(new Date());
			order.setCreatetime(new Date());
			order.setEndtime(new Date());
			order.setOrdergoods(order.getOrdergoods());
			BigDecimal payment = new BigDecimal("0.00");
			order.setPayment(payment);
			order.setPaytype(order.getPaytype());
			order.setPaytime(new Date());
			PrimaryGenerater Serialnumber = PrimaryGenerater.getInstance();
			int randomcode = RandomCode.getRandNum();
			String liqud = Serialnumber.generaterNextNumber(String.valueOf(randomcode));
			order.setSerialnumber(liqud);
			/*paytype:1代表paypal支付2代表googlepay支付 paystatus:1未付款2已付款3交易成功4取消订单*/
			order.setPaystatus(4);
			order.setUpdatetime(new Date());
			
			ordergood.setGoods(goods);
			/*5.保存订单详情里面的orderid*/
			orderService.create(order);
			
			String orderid_sql = "from Order where userid= "+"'"+user.getUserid()+"'"+" and gameid="+"'"+game.getGameid()+"'"+"and serialnumber="+"'"+liqud+"'";
			Order orderdata = orderService.queryBysql(orderid_sql);
			
			
			if(orderdata == null)
			{
				status = "0404";
				message = "取消订单失败，没有取消成功！";
				orderid = 0;
			}
			else
			{
				status = "0200";
				message = "取消订单成功！";
				orderid = orderdata.getOrderid();
			}
			
			map.put("status", status);
			map.put("message", message);
			map.put("orderid", orderid);
		}
		
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
		
		/*1.首先创建订单包含用户信息，商品信息，游戏信息，订单详情信息*/
		/*2.先获得用户信息*/
		User user = order.getUser();
		Game game = order.getGames();
		/*String sql = "from Game where userid="+"'"+user.getUserid()+"'"+"and gameid="+"'"+game.getGameid()+"'";
		game = gameService.queryBysql(sql);*/
		user = userService.query(user.getUserid());
		/*System.out.println(game);*/
		
		if(user == null)
		{
			status = "0404";
			message = "创建订单失败，不存在该用户！";
			orderid = 0;
			map.put("status", status);
			map.put("message", message);
			map.put("orderid", orderid);
		}
		else
		{
			/*3.再获得商品信息*/
			Set<OrderGoods> ordergoods = order.getOrdergoods();
			OrderGoods ordergood = new OrderGoods();
			Iterator it = ordergoods.iterator();
			while (it.hasNext()) {
				ordergood = (OrderGoods) it.next();
				/*ordergood.setOrder(order);*/
			}
			
			Goods goods = new Goods();
			goods.setGoodsname(ordergood.getTitle());
			goods.setGoodsnumber(ordergood.getNumber());
			goods.setGoodsprice(ordergood.getPrice());
			goods.setGoodstotal(ordergood.getTotalprice());
			goods.setGoodspicture(ordergood.getPicpath());
			goods.setUser(user);
			goodsService.save(goods);
			
			System.err.println("创建Ordergoodsservice服务："+orderGoodsService);
			
			System.err.println("订单详情："+ordergood);
			System.err.println(goods.getGoodsname()+goods.getGoodsprice());
			/*4.最后保存订单信息，用户信息，订单详情信息*/
			/*order.setUser(order.getUser());*/
			order.setClosetime(new Date());
			order.setCreatetime(new Date());
			order.setEndtime(new Date());
			order.setOrdergoods(order.getOrdergoods());
			order.setPayment(order.getPayment());
			order.setPaytype(order.getPaytype());
			order.setPaytime(new Date());
			PrimaryGenerater Serialnumber = PrimaryGenerater.getInstance();
			int randomcode = RandomCode.getRandNum();
			String liqud = Serialnumber.generaterNextNumber(String.valueOf(randomcode));
			order.setSerialnumber(liqud);
			order.setPaystatus(order.getPaystatus());
			order.setUpdatetime(new Date());
			
			ordergood.setGoods(goods);
			/*5.保存订单详情里面的orderid*/
			System.err.println("获得的orderid："+orderService.query(order.getOrderid()));
			orderService.create(order);
			
			String orderid_sql = "from Order where userid= "+"'"+user.getUserid()+"'"+" and gameid="+"'"+game.getGameid()+"'"+"and serialnumber="+"'"+liqud+"'";
			Order orderdata = orderService.queryBysql(orderid_sql);
			
			
			if(orderdata == null)
			{
				status = "0404";
				message = "创建订单失败，没有创建成功！";
				orderid = 0;
			}
			else
			{
				status = "0200";
				message = "创建订单成功！";
				orderid = orderdata.getOrderid();
				
				ordergood.setOrder(orderdata);
				orderGoodsService.saveorupdate(ordergood);
			}
			
			map.put("status", status);
			map.put("message", message);
			map.put("orderid", orderid);
			map.put("serialnumber",liqud);
		}
		return map;
	}
	
}
