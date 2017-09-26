package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.OrderDao;
import com.gy.model.Order;
import com.gy.services.OrderService;

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
@Service
public class OrderServiceImpl implements OrderService {

	/**
	 * 创建OrderDao
	 */
	@Autowired
	private OrderDao orderDao;
	
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
	

}
