package com.gy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gy.dao.OrderGoodsDao;
import com.gy.model.Order;
import com.gy.model.OrderGoods;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-9-22
 * @introduce 这是订单商品详情的实现类
 */

@Repository
public class OrderGoodsDaoImpl implements OrderGoodsDao {

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
	
	@Override
	public OrderGoods query(int orderid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		OrderGoods ordergoods = null;
		try {
			ordergoods = (OrderGoods)session.get(OrderGoods.class, orderid);
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
		return ordergoods;
	}

	@Override
	public List<OrderGoods> queryAll() {
		// TODO Auto-generated method stub
		List<OrderGoods> ordergoods = new ArrayList<OrderGoods>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			ordergoods = session.createQuery("from OrderGoods").list();
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
		
		return ordergoods;
	}

	@Override
	public boolean create(OrderGoods ordergoods) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try { 
			tx = session.beginTransaction();
			int i = (Integer) session.save(ordergoods);
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

	@Override
	public void saveAll(OrderGoods[] ordergoods) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(ordergoods);
	}

	@Override
	public boolean delete(int orderid) {
		// TODO Auto-generated method stub
		OrderGoods ordergoods = query(orderid);
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(ordergoods);
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

	@Override
	public boolean deleteAll(OrderGoods[] ordergoods) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().delete(ordergoods);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modify(OrderGoods ordergoods) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(ordergoods);
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

	@Override
	public boolean modifyAll(OrderGoods[] ordergoods) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OrderGoods queryBySql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		OrderGoods ordergoods = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<OrderGoods> list = new ArrayList<OrderGoods>();
			list = query.list();
			if(list!=null && list.size()>0){
				ordergoods = (OrderGoods)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return ordergoods;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
	
		}
		return ordergoods;
	}

	@Override
	public boolean cancel(int orderid) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
