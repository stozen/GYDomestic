package com.gy.servicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

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

	@Override
	public Order query(int orderid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAll(Order[] order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int orderid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(Order[] order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int orderid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAll(Order[] order) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 创建一个订单 
	 */
	@Override
	public Order createOrder() {
		// TODO Auto-generated method stub
		
		
		
		return null;
	}

}
