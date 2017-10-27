package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.gy.dao.OrderGoodsDao;
import com.gy.daoImpl.OrderGoodsDaoImpl;
import com.gy.model.OrderGoods;
import com.gy.services.OrderGoodsService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-9-22
 * @introduce 这是订单商品的服务的接口实现类
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@Service
public class OrderGoodsServiceImpl implements OrderGoodsService {

	/**
	 * 创建订单详情的数据库层操作类
	 */
	@Autowired
	private OrderGoodsDao orderGoodsDao;
	
	/**
	 * 创建订单详情的数据库的get方法
	 * @return
	 */
	public OrderGoodsDao getOrderGoodsDao() {
		return orderGoodsDao;
	}

	/**
	 * 创建订单详情的数据库的set方法
	 * @return
	 */
	public void setOrderGoodsDao(OrderGoodsDao orderGoodsDao) {
		this.orderGoodsDao = orderGoodsDao;
	}

	@Override
	public OrderGoods query(int orderid) {
		// TODO Auto-generated method stub
		return orderGoodsDao.query(orderid);
	}

	@Override
	public List<OrderGoods> queryAll() {
		// TODO Auto-generated method stub
		return orderGoodsDao.queryAll();
	}

	@Override
	public boolean create(OrderGoods ordergoods) {
		// TODO Auto-generated method stub
		System.err.println("数据库操作层orderGoodsDao："+orderGoodsDao);
		return orderGoodsDao.create(ordergoods);
	}

	@Override
	public void saveAll(OrderGoods[] ordergoods) {
		// TODO Auto-generated method stub
		orderGoodsDao.saveAll(ordergoods);
	}

	@Override
	public boolean delete(int orderid) {
		// TODO Auto-generated method stub
		return orderGoodsDao.delete(orderid);
	}

	@Override
	public boolean deleteAll(OrderGoods[] ordergoods) {
		// TODO Auto-generated method stub
		return orderGoodsDao.deleteAll(ordergoods);
	}

	@Override
	public boolean modify(OrderGoods ordergoods) {
		// TODO Auto-generated method stub
		return orderGoodsDao.modify(ordergoods);
	}

	@Override
	public boolean modifyAll(OrderGoods[] ordergoods) {
		// TODO Auto-generated method stub
		return orderGoodsDao.modifyAll(ordergoods);
	}

	@Override
	public OrderGoods queryBySql(String sql) {
		// TODO Auto-generated method stub
		return orderGoodsDao.queryBySql(sql);
	}

	@Override
	public boolean cancel(int orderid) {
		// TODO Auto-generated method stub
		return orderGoodsDao.cancel(orderid);
	}

	@Override
	public boolean saveorupdate(OrderGoods ordergoods) {
		// TODO Auto-generated method stub
		return orderGoodsDao.saveorupdate(ordergoods);
	}
}
