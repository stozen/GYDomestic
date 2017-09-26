package com.gy.services;

import java.util.List;

import com.gy.model.OrderGoods;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-9-22
 * @introduce 这是订单商品的服务的接口
 */
public interface OrderGoodsService {
	
	/**
	 * 实现单个订单详情查询功能
	 * @return true or false
	 */
	public OrderGoods query(int orderid);
	
	/**
	 * 实现查询所有订单详情
	 * @return List集合
	 */
	public List<OrderGoods> queryAll();
	
	/**
	 * 实现订单详情添加功能
	 * @return true or false
	 */
	public boolean create(OrderGoods ordergoods);
	
	/**
	 * 增加一些订单详情
	 * @return true or false
	 */
	public void saveAll(OrderGoods[] ordergoods);
	
	/**
	 * 实现删除一个订单详情
	 * @return true or false
	 */
	public boolean delete(int orderid);
	
	/**
	 * 实现删除某些订单详情
	 * @return true or false
	 */
	public boolean deleteAll(OrderGoods[] ordergoods);
	
	/**
	 * 修改一个订单详情
	 * @return
	 */
	public boolean modify(OrderGoods ordergoods);
		
	/**
	 * 修改所有订单详情
	 * @return true or false
	 */
	public boolean modifyAll(OrderGoods[] ordergoods);
	
	/**
	 * 通过查询条件查询订单详情
	 * @param sql
	 * @return
	 */
	public OrderGoods queryBySql(String sql);
	
	/**
	 * 取消订单详情
	 * @param orderid
	 * @return
	 */
	public boolean cancel(int orderid);
	
}
