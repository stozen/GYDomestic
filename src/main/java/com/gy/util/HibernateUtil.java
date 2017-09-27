package com.gy.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是Hibernate帮助类
 */
@SuppressWarnings("deprecation")
public class HibernateUtil {
	
	/**
	 * 创建会话工厂类
	 */
	private static SessionFactory sessionFactory = null; 
	
	/**
	 * 创建一个静态类用来获取Hibernate的配置
	 */
	static{
		Configuration cfg = new Configuration().configure("config/spring-hibernate.xml");
		sessionFactory = cfg.buildSessionFactory();
	}
	
	/**
	 * 获取会话工厂类
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * 获取会话类
	 */
	public static Session getSession(){
		return sessionFactory.openSession();
	}
	
	/**
	 * 获取会话工厂类
	 */
	public static Transaction getTransaction(){
		return getSession().beginTransaction();
	}
	
	/**
	 * 关闭响应的资源
	 * @param session
	 */
	public static void closeSession(Session session){
		try {
			if(session!=null)
			{
				session.close();
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭响应的资源
	 * @param sessionFactory
	 */
	public static void closeSessionFactory(SessionFactory sessionFactory){
		try {
			if(sessionFactory!=null)
			{
				sessionFactory.close();
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 提交失败进行事务回滚
	 * @param tx
	 */
	public static void transactionRollback(Transaction tx){
		if(tx!=null)
		{
			tx.rollback();
		}
	}
}
