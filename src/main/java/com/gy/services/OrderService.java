package com.gy.services;

import java.util.List;
import java.util.Map;

import com.gy.model.Order;
import com.gy.model.User;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是订单服务层接口
 * @date 2017.9.12
 */

public interface OrderService {
	
	/**
	 * 实现订单单个查询功能
	 * @return true or false
	 */
	public Order query(int orderid);
	
	/**
	 * 实现查询所有订单
	 * @return List集合
	 */
	public List<Order> queryAll();
	
	/**
	 * 实现订单添加功能
	 * @return true or false
	 */
	public boolean create(Order order);
	
	/**
	 * 增加一些订单
	 * @return true or false
	 */
	public void saveAll(Order[] order);
	
	/**
	 * 实现删除一个订单
	 * @return true or false
	 */
	public boolean delete(int orderid);
	
	/**
	 * 实现删除某些订单
	 * @return true or false
	 */
	public boolean deleteAll(Order[] order);
	
	/**
	 * 更新一个订单
	 * @return
	 */
	public boolean update(Order order);
		
	/**
	 * 更新所有订单
	 * @return true or false
	 */
	public boolean updateAll(Order[] order);
	
	/**
	 * 根据条件来查询订单数据
	 * @param sql
	 * @return
	 */
	public Order queryBysql(String sql);
	
	/**
	 * 创建订单功能实现
	 * @param order
	 * @param token
	 * @param map
	 * @throws Exception 
	 */
	public void create(Order order,String token,Map map) throws Exception;
	
	/**
	 * 取消订单功能实现
	 * @param order
	 * @param token
	 * @param map
	 */
	public void cancel(Order order,String token,Map map);
	
	/**
	 * 修改订单功能实现
	 * @param order
	 * @param token
	 * @param map
	 */
	public void modify(Order order,String token,Map map);
	
	/**
	 * 删除订单功能实现
	 * @param order
	 * @param token
	 * @param map
	 */
	public void delete(Order order,String token,Map map);
	
	/**
	 * 查询订单功能实现
	 * @param order
	 * @param token
	 * @param map
	 */
	public void query(String orderid,Map map);
}
