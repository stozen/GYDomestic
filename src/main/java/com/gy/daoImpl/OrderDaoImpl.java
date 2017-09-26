package com.gy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gy.dao.OrderDao;
import com.gy.model.Order;
import com.gy.model.User;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是订单数据库操作实现层
 * @date 2017.9.12
 */

@Repository(value="OrderDaoImpl")
public class OrderDaoImpl implements OrderDao {

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
	 * 创建查询订单功能
	 */
	@Override
	public Order query(int orderid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Order order = null;
		try {
			order = (Order)session.get(Order.class, orderid);
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
		return order;
	}
	
	/**
	 * 创建查询所有订单功能
	 */
	@Override
	public List<Order> queryAll() {
		// TODO Auto-generated method stub
		List<Order> orders = new ArrayList<Order>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			orders = session.createQuery("from Order").list();
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
		
		return orders;
	}

	/**
	 * 创建保存订单功能
	 */
	@Override
	public boolean create(Order order) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try { 
			tx = session.beginTransaction();
			int i = (Integer) session.save(order);
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
	 * 创建保存所有订单功能
	 */
	@Override
	public void saveAll(Order[] order) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(order);
	}

	/**
	 * 创建删除订单功能
	 */
	@Override
	public boolean delete(int orderid) {
		// TODO Auto-generated method stub
		Order order = query(orderid);
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(order);
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
	 * 创建删除所有订单功能
	 */
	@Override
	public boolean deleteAll(Order[] order) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().delete(order);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建更新订单功能
	 */
	@Override
	public boolean update(Order order) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(order);
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
	 * 创建更新所有订单功能
	 */
	@Override
	public boolean updateAll(Order[] order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			session.flush();
			tx.commit();
		} catch (HibernateException e) {
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
	}

	/**
	 * 根据条件查询用户
	 */
	@Override
	public Order queryBySql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Order order = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<Order> list = new ArrayList<Order>();
			list = query.list();
			if(list!=null && list.size()>0){
				order = (Order)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return order;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
	
		}
		return order;
	}
	
	/**
	 * 取消订单
	 */
	@Override
	public boolean cancel(int orderid) {
		// TODO Auto-generated method stub
		return false;
	}
}
