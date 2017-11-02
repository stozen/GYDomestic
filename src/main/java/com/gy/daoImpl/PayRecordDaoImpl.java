package com.gy.daoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gy.dao.PayRecordDao;
import com.gy.model.PayRecord;
import com.gy.model.User;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-27
 * @introduce 这是支付记录的接口的实现类
 */

@Repository
public class PayRecordDaoImpl implements PayRecordDao {
	
	/**
	 * 创建Hibernate的会话工厂类
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 声明Hibernate的会话
	 */
	private Session session;
	
	/**
	 * 声明查询语句
	 */
	private Query query;
	
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

	/* 
	 * 通过订单编号获取支付记录
	 * (non-Javadoc)
	 * @see com.gy.dao.PayRecordDao#get(java.lang.String)
	 */
	@Override
	public PayRecord get(String out_trade_no) {
		// TODO Auto-generated method stub
		
		Session session = getSession();
		PayRecord payRecord = null;
		try {
			tx = session.beginTransaction();
			query = session.createQuery("from PayRecord where outTradeNUmber="+"'"+out_trade_no+"'");
			List<PayRecord> list = new ArrayList<PayRecord>();
			list = query.list();
			Iterator<PayRecord> itor = list.iterator();
			while(itor.hasNext())
			{
			    payRecord = (PayRecord)itor.next();
			}
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(tx!=null)
			{
				tx.rollback();
			}
		} finally {
			if(session!=null){
				session.close();
			}
		}
		return payRecord;
	}

	/* 
	 * 实现更新支付记录的功能
	 * (non-Javadoc)
	 * @see com.gy.dao.PayRecordDao#update(com.gy.model.PayRecord)
	 */
	@Override
	public void update(PayRecord payRecord) {
		// TODO Auto-generated method stub
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			session.update(payRecord);
			Hibernate.initialize(payRecord);
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
	}

	/* 
	 * 添加支付记录
	 * (non-Javadoc)
	 * @see com.gy.dao.PayRecordDao#add(com.gy.model.PayRecord)
	 */
	@Override
	public void add(PayRecord payRecord) {
		// TODO Auto-generated method stub
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			session.save(payRecord);
			Hibernate.initialize(payRecord);
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
	}

	/* 
	 * 删除支付记录
	 * (non-Javadoc)
	 * @see com.gy.dao.PayRecordDao#delete(com.gy.model.PayRecord)
	 */
	@Override
	public void delete(String out_trade_no) {
		// TODO Auto-generated method stub
		PayRecord payRecord = this.get(out_trade_no);
		try {
			session = getSession();
			tx = session.beginTransaction();
			session.delete(payRecord);
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(tx!=null)
			{
				tx.rollback();
			}
		} finally {
			if(session!=null) {
				session.close();
			}
		}
	}
}
