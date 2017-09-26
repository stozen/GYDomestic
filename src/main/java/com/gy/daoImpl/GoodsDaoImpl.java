package com.gy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gy.dao.GoodsDao;
import com.gy.model.Goods;
import com.gy.model.Order;
import com.gy.model.OrderGoods;

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
		Session session = getSession();
		Goods goods = null;
		try {
			goods = (Goods)session.get(Goods.class, goodsid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
			/*if(sessionFactory!=null){
				sessionFactory.close();
			}*/
		}
		return goods;
	}

	/**
	 * 创建查询所有商品的功能
	 */
	@Override
	public List<Goods> queryAll() {
		// TODO Auto-generated method stub
		List<Goods> goods = new ArrayList<Goods>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			goods = session.createQuery("from Goods").list();
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if(session!=null)
			{
				session.close();
			}
		}
		
		return goods;
	}

	/**
	 * 创建商品的功能
	 */
	@Override
	public boolean save(Goods goods) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try { 
			tx = session.beginTransaction();
			int i = (Integer) session.save(goods);
			if(i>0)
			{
				flag = true;
			}
			else{
				flag = false;
			}
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return flag;
	}

	/**
	 * 创建保存所有商品的功能
	 */
	@Override
	public boolean saveAll(Goods[] goods) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(goods);
		return false;
	}

	/**
	 * 创建删除商品的功能
	 */
	@Override
	public boolean delete(int goodsid) {
		// TODO Auto-generated method stub
		Goods goods = query(goodsid);
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(goods);
			flag = true;
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if(session!=null) {
				session.close(); 
			}
		}
		return flag;
	}

	/**
	 * 创建删除所有商品的功能
	 */
	@Override
	public boolean deleteAll(Goods[] goods) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().delete(goods);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建更新或者修改商品的功能
	 */
	@Override
	public boolean modify(int goodsid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			Goods goods = this.query(goodsid);
			session.update(goods);
			flag = true;
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (tx!=null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return flag;
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
