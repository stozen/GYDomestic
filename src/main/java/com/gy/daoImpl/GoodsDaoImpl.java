package com.gy.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gy.dao.GoodsDao;
import com.gy.model.Goods;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是商品数据库操作实现层
 * @date 2017.9.12
 */

@Repository
public class GoodsDaoImpl implements GoodsDao {

	/**
	 * 创建Hibernate的会话工厂类
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 创建事务
	 */
	private Transaction tx;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 创建获得Session对象
	 * @return
	 */
	private Session getSession(){
		return this.getSessionFactory().openSession();
	}
	
	/**
	 * 创建查询商品的功能
	 */
	@Override
	public Goods query(int goodsid) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 创建查询所有商品的功能
	 */
	@Override
	public List<Goods> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 创建商品的功能
	 */
	@Override
	public boolean save(Goods goods) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建保存所有商品的功能
	 */
	@Override
	public boolean saveAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建删除商品的功能
	 */
	@Override
	public boolean delete(int goodsid) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建删除所有商品的功能
	 */
	@Override
	public boolean deleteAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建更新或者修改商品的功能
	 */
	@Override
	public boolean modify(int goodsid) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建更新所有商品的功能
	 */
	@Override
	public boolean updateAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return false;
	}
}
