package com.gy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gy.dao.AliPayConfigDao;
import com.gy.model.AliPayConfig;
import com.gy.model.WeixinPayConfig;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-25
 * @introduce 这是阿里支付的配置dao层接口的实现层
 */

@Repository
public class AliPayConfigDaoImpl implements AliPayConfigDao {

	/**
	 * 创建Hibernate的会话工厂类
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 创建事务管理
	 */
	private Transaction tx;
	
	/**
	 * 声明查询语句
	 */
	private Query query;
	
	/**
	 * 声明Hibernate的会话工厂引用的get方法
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 声明Hibernate的会话工厂引用的set方法
	 * @param sessionFactory
	 */
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
	 * 实现通过id来查询阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.AliPayConfigDao#queryAliPayConfigId(java.lang.String)
	 */
	@Override
	public AliPayConfig queryAliPayConfigId(String aliPayConfigId) {
		// TODO Auto-generated method stub
		Session session = getSession();
		AliPayConfig aliPayConfig = null;
		try {
			tx = session.beginTransaction();
			session.get(AliPayConfig.class, aliPayConfigId);
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
		return aliPayConfig;
	}

	/* 
	 * 实现查询所有的阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.AliPayConfigDao#queryAllAliPayConfig()
	 */
	@Override
	public List<AliPayConfig> queryAllAliPayConfig() {
		// TODO Auto-generated method stub
		List<AliPayConfig> aliPayConfig = new ArrayList<AliPayConfig>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			aliPayConfig = session.createQuery("from AliPayConfig").list();
			Hibernate.initialize(aliPayConfig);
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
		return aliPayConfig;
	}

	/* 
	 * 实现通过包名来查询阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.AliPayConfigDao#queryGamepackage(java.lang.String)
	 */
	@Override
	public AliPayConfig queryGamepackage(String gamePacakge) {
		// TODO Auto-generated method stub
		AliPayConfig aliPayConfig = null;
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			List<AliPayConfig> list = new ArrayList<AliPayConfig>();
			Query query =  session.createQuery("from AliPayConfig where GAME_PACKAGE="+"'"+gamePacakge+"'");
			list = query.list();
			if(list!=null && list.size()>0){
				aliPayConfig = (AliPayConfig)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			Hibernate.initialize(aliPayConfig);
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
		return aliPayConfig;
	}

	/* 
	 * 实现通过sql语句来查询阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.AliPayConfigDao#queryBysql(java.lang.String)
	 */
	@Override
	public AliPayConfig queryBysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		AliPayConfig aliPayConfig = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<AliPayConfig> list = new ArrayList<AliPayConfig>();
			Hibernate.initialize(aliPayConfig);
			list = query.list();
			if(list!=null && list.size()>0){
				aliPayConfig = (AliPayConfig)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
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
			if(session!=null)
			{
				session.close();
			}
		}
		return aliPayConfig;
	}

	/* 
	 * 实现添加阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.AliPayConfigDao#addAliPayConfig(com.gy.model.AliPayConfig)
	 */
	@Override
	public boolean addAliPayConfig(AliPayConfig aliPayConfig) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.save(aliPayConfig);
			Hibernate.initialize(aliPayConfig);
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

	/* 
	 * 实现更新或者保存阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.AliPayConfigDao#saveOrUpdateAliPayConfig(com.gy.model.AliPayConfig)
	 */
	@Override
	public boolean saveOrUpdateAliPayConfig(AliPayConfig aliPayConfig) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(aliPayConfig);
			Hibernate.initialize(aliPayConfig);
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

	/* 
	 * 实现通过id来删除阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.AliPayConfigDao#deleteAliPayConfigId(int)
	 */
	@Override
	public boolean deleteAliPayConfigId(String aliPayConfigId) {
		// TODO Auto-generated method stub
		AliPayConfig aliPayConfig = queryAliPayConfigId(aliPayConfigId);
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(aliPayConfig);
			Hibernate.initialize(aliPayConfig);
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

	/* 
	 * 实现更新阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.AliPayConfigDao#updateAliPayConfig(com.gy.model.AliPayConfig)
	 */
	@Override
	public boolean updateAliPayConfig(AliPayConfig aliPayConfig) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(aliPayConfig);
			Hibernate.initialize(aliPayConfig);
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

}
