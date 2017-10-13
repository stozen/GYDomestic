package com.gy.servicesImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.OrderDao;
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
import com.gy.util.PrimaryGenerater;
import com.gy.util.RandomCode;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是订单服务层接口实现类
 * @date 2017.9.12
 */

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	/**
	 * 创建OrderDao
	 */
	@Autowired
	private OrderDao orderDao;
	
	/**
	 * 创建游戏服务类
	 */
	@Autowired
	private GameService gameService;

	/**
	 * 创建用户服务类
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 创建商品服务类
	 */
	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 创建订单详情的服务类
	 */
	@Autowired
	private OrderGoodsService orderGoodsService;
	
	/**
	 * 创建返回给客户端的状态信息
	 */
	private String status;
	 
	/**
	 * 创建返回给客户端的信息
	 */
	private String message;
	
	/**
	 * 创建返回给客户端的订单id信息
	 */
	private int orderid;
	
	/**
	 * 创建获取orderdao的get方法
	 * @return
	 */
	public OrderDao getOrderDao() {
		return orderDao;
	}

	/**
	 * 创建获取orderdao的set方法
	 * @return
	 */
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	/**
	 * 创建游戏服务类的get方法
	 * @return
	 */
	public GameService getGameService() {
		return gameService;
	}

	/**
	 * 创建游戏服务类的set方法
	 * @return
	 */
	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}
	
	/**
	 * 创建用户服务类的get方法
	 * @return
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * 创建用户服务类的set方法
	 * @return
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 创建商品服务类的get方法
	 * @return
	 */
	public GoodsService getGoodsService() {
		return goodsService;
	}

	/**
	 * 创建商品服务类的set方法
	 * @param goodsService
	 */
	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	/**
	 * 创建订单详情服务类的get方法
	 * @return
	 */
	public OrderGoodsService getOrderGoodsService() {
		return orderGoodsService;
	}

	/**
	 * 创建订单服务类的set方法
	 * @param orderGoodsService
	 */
	public void setOrderGoodsService(OrderGoodsService orderGoodsService) {
		this.orderGoodsService = orderGoodsService;
	}

	/**
	 * 创建查询订单根据id来查询
	 * @return
	 */
	@Override
	public Order query(int orderid) {
		// TODO Auto-generated method stub
		return orderDao.query(orderid);
	}

	/**
	 * 创建查询所有订单
	 * @return
	 */
	@Override
	public List<Order> queryAll() {
		// TODO Auto-generated method stub
		return orderDao.queryAll();
	}

	/**
	 * 生成订单信息
	 * @return
	 */
	@Override
	public boolean create(Order order) {
		// TODO Auto-generated method stub
		return orderDao.create(order);
	}

	/**
	 * 创建保存所有订单
	 * @return
	 */
	@Override
	public void saveAll(Order[] order) {
		// TODO Auto-generated method stub
		orderDao.saveAll(order);
	}

	/**
	 * 创建删除订单
	 * @return
	 */
	@Override
	public boolean delete(int orderid) {
		// TODO Auto-generated method stub
		return orderDao.delete(orderid);
	}

	/**
	 * 删除所有订单
	 */
	@Override
	public boolean deleteAll(Order[] order) {
		// TODO Auto-generated method stub
		return orderDao.deleteAll(order);
	}

	/**
	 * 根据ID更新一个订单
	 */
	@Override
	public boolean update(Order order) {
		// TODO Auto-generated method stub
		return orderDao.update(order);
	}

	/**
	 * 更新所有订单
	 */
	@Override
	public boolean updateAll(Order[] order) {
		// TODO Auto-generated method stub
		return orderDao.updateAll(order);
	}

	@Override
	public Order queryBysql(String sql) {
		// TODO Auto-generated method stub
		return orderDao.queryBySql(sql);
	}
	
	/**
	 * 创建订单功能实现
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public void create(Order order,String token,Map map) {
		// TODO Auto-generated method stub
		/*1.首先创建订单包含用户信息，商品信息，游戏信息，订单详情信息*/
		/*2.先获得用户信息*/
		User user = order.getUser();
		Game game = order.getGames();
		String sql = "from Game where userid="+"'"+user.getUserid()+"'"+"and gameid="+"'"+game.getGameid()+"'";
		game = gameService.queryBysql(sql);
		
		user = userService.query(user.getUserid());
		
		/*System.out.println(game);*/
		
		if(user == null || game==null)
		{
			status = "0404";
			message = "创建订单失败，不存在该用户或者游戏！";
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
			if(ordergoods==null)
			{
				status = "0403";
				message = "订单详情没有，没有填写购物的商品！";
				orderid = 0;
				map.put("status", status);
				map.put("message", message);
				map.put("orderid", orderid);
			}
			else
			{
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
				String liqud = Serialnumber.generaterNextNumber();
				order.setSerialnumber(liqud);
				order.setPaystatus(order.getPaystatus());
				order.setGames(game);
				order.setUpdatetime(new Date());
				
				ordergood.setGoods(goods);
				/*5.保存订单详情里面的orderid*/
				this.create(order);
				
				String orderid_sql = "from Order where userid= "+"'"+user.getUserid()+"'"+" and gameid="+"'"+game.getGameid()+"'"+"and serialnumber="+"'"+liqud+"'";
				Order orderdata = this.queryBysql(orderid_sql);
				
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
		}
	}
	
	/**
	 * 创建取消订单功能实现
	 */
	@Override
	public void cancel(Order order,String token,Map map) {
		// TODO Auto-generated method stub
		/*1.首先创建订单包含用户信息，商品信息，游戏信息，订单详情信息*/
		/*2.先获得用户信息*/
		User user = order.getUser();
		Game game = order.getGames();
		game = gameService.query(game.getGameid());
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
			
			String liqud = Serialnumber.generaterNextNumber();
			order.setSerialnumber(liqud);
			/*paytype:1代表paypal支付2代表googlepay支付 paystatus:1未付款2已付款3交易成功4取消订单*/
			order.setPaystatus(4);
			order.setUpdatetime(new Date());
			order.setGames(game);
			ordergood.setGoods(goods);
			/*5.保存订单详情里面的orderid*/
			this.create(order);
			
			Order orderdata = null;
			if(game==null)
			{
				status = "0403";
				message = "游戏资源不存在！";
				orderid = 0;
			}
			else
			{
				String orderid_sql = "from Order where userid= "+"'"+user.getUserid()+"'"+" and gameid="+"'"+game.getGameid()+"'"+"and serialnumber="+"'"+liqud+"'";
				orderdata = this.queryBysql(orderid_sql);
				
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
					
					ordergood.setOrder(orderdata);
					orderGoodsService.saveorupdate(ordergood);
				}
				
			}
			
			map.put("status", status);
			map.put("message", message);
			map.put("orderid", orderid);
		}
	}

	/**
	 * 创建修改订单功能实现
	 */
	@Override
	public void modify(Order order, String token, Map map) {
		// TODO Auto-generated method stub
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
			message = "修改订单失败，不存在该用户！";
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

			/*4.最后保存订单信息，用户信息，订单详情信息*/
			order.setClosetime(new Date());
			order.setCreatetime(new Date());
			order.setEndtime(new Date());
			order.setOrdergoods(order.getOrdergoods());
			order.setPayment(order.getPayment());
			order.setPaytype(order.getPaytype());
			order.setPaytime(new Date());
			PrimaryGenerater Serialnumber = PrimaryGenerater.getInstance();
			int randomcode = RandomCode.getRandNum();
			String liqud = Serialnumber.generaterNextNumber();
			order.setSerialnumber(liqud);
			order.setPaystatus(order.getPaystatus());
			order.setUpdatetime(new Date());
			
			ordergood.setGoods(goods);
			/*5.保存订单详情里面的orderid*/
			this.create(order);
			
			String orderid_sql = "from Order where userid= "+"'"+user.getUserid()+"'"+" and gameid="+"'"+game.getGameid()+"'"+"and serialnumber="+"'"+liqud+"'";
			Order orderdata = this.queryBysql(orderid_sql);
			
			
			if(orderdata == null)
			{
				status = "0404";
				message = "修改订单失败，没有修改成功！";
				orderid = 0;
			}
			else
			{
				status = "0200";
				message = "修改订单成功！";
				orderid = orderdata.getOrderid();
				
				ordergood.setOrder(orderdata);
				orderGoodsService.saveorupdate(ordergood);
			}
			
			map.put("status", status);
			map.put("message", message);
			map.put("orderid", orderid);
			map.put("serialnumber",liqud);
		}
	}
	
	/**
	 * 创建修改订单功能实现
	 */
	@Override
	public void delete(Order order, String token, Map map) {
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
		
		this.create(order);
		
		/*ordergood.setOrder(order);*/
		orderGoodsService.create(ordergood);
		
		map.put("payment", order.getPayment());
		map.put("ordergoods",order.getOrdergoods());
		map.put("token", token);
		map.put("status", order.getPaystatus());
	}
	
	@Override
	public void query(String orderid, Map map) {
		// TODO Auto-generated method stub
		int oid = Integer.parseInt(orderid.trim());
		
		/*String sql = "from OrderGoods where orderid="+"'"+orderid+"'";*/
		OrderGoods ordergoods = orderGoodsService.query(oid);
		if(ordergoods!=null)
		{
			Order order = this.query(oid);
			Order ordernew = new Order();
			ordernew.setOrderid(order.getOrderid());
			OrderGoods ordergoodsnew = new OrderGoods();
			int goodsid = ordergoods.getGoods().getGoodsid();
			Goods goods = goodsService.query(goodsid);
			Goods goodsnew = new Goods();
			goodsnew.setGoodsid(goodsid);
			User user = new User();
			user = goods.getUser();
			User usernew = new User();
			/*goodsnew.setUser(usernew);
			usernew.setUserid(user.getUserid());*/
			
			/*ordergoodsnew.setGoods(goodsnew);*/
			ordergoodsnew.setNumber(ordergoods.getNumber());
			ordergoodsnew.setOgid(ordergoods.getOgid());
			ordergoodsnew.setPicpath(ordergoods.getPicpath());
			ordergoodsnew.setPrice(ordergoods.getPrice());
			ordergoodsnew.setTitle(ordergoods.getTitle());
			ordergoodsnew.setTotalprice(ordergoods.getTotalprice());
			
			map.remove("order");
			map.remove("goods");
			map.put("ordergoods", ordergoodsnew);
			map.put("goodid", goodsnew.getGoodsid());
			map.put("userid", user.getUserid());
			
			status = "0200";
			message = "查询成功！";
			
			map.put("status", status);
			map.put("message", message);
			map.put("orderid", orderid);
		}
		else
		{
			status = "0404";
			message = "查询失败，不存在这笔订单！";
			map.put("status", status);
			map.put("message", message);
			map.put("orderid", 0);
		}
	}
}
