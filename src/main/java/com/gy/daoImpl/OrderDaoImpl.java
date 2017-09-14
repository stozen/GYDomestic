package com.gy.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.dao.OrderDao;
import com.gy.model.Order;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是订单数据库操作实现层
 * @date 2017.9.12
 */

@Repository
public class OrderDaoImpl implements OrderDao {

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

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

}
