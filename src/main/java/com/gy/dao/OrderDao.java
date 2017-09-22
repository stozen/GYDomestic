package com.gy.dao;

import java.util.List;

import com.gy.model.Order;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是订单数据库操作层
 * @date 2017.9.12
 */

public interface OrderDao {
	
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
	public boolean save(Order order);
	
	/**
	 * 增加一些订单
	 * @return true or false
	 */
	public boolean saveAll(Order[] order);
	
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
	public boolean update(int orderid);
		
	/**
	 * 更新所有订单
	 * @return true or false
	 */
	public boolean updateAll(Order[] order);

	/**
	 * 刷新Session
	 * @return true or false
	 */
	public void flush();
}
