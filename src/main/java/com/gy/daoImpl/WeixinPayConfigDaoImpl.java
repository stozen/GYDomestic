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

import com.gy.dao.WeixinPayConfigDao;
import com.gy.model.AliPayConfig;
import com.gy.model.User;
import com.gy.model.WeixinPayConfig;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-25
 * @introduce 
 */

@Repository
public class WeixinPayConfigDaoImpl implements WeixinPayConfigDao {

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
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
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
	 * 实现通过ID来查询微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.WeixinPayConfigDao#querWeixinPayConfigId(java.lang.String)
	 */
	@Override
	public WeixinPayConfig queryWeixinPayConfigId(String weixinPayConfigId) {
		// TODO Auto-generated method stub
		Session session = getSession();
		WeixinPayConfig weixinPayConfig = null;
		try {
			tx = session.beginTransaction();
			session.get(WeixinPayConfig.class, weixinPayConfigId);
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
		
		return weixinPayConfig;
	}

	/* 
	 * 实现查询所有的微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.WeixinPayConfigDao#queryAllWeixinPayConfig()
	 */
	@Override
	public List<WeixinPayConfig> queryAllWeixinPayConfig() {
		// TODO Auto-generated method stub
		List<WeixinPayConfig> weixinPayConfig = new ArrayList<WeixinPayConfig>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			weixinPayConfig = session.createQuery("from WeixinPayConfig").list();
			Hibernate.initialize(weixinPayConfig);
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
		
		return weixinPayConfig;
	}

	/* 
	 * 实现通过包名来查询微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.WeixinPayConfigDao#queryGamepackage(java.lang.String)
	 */
	@Override
	public WeixinPayConfig queryGamepackage(String gamePacakge) {
		// TODO Auto-generated method stub
		WeixinPayConfig weixinPayConfig = null;
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			List<WeixinPayConfig> list = new ArrayList<WeixinPayConfig>();
			Query query =  session.createQuery("from WeixinPayConfig where GAME_PACKAGE="+"'"+gamePacakge+"'");
			list = query.list();
			if(list!=null && list.size()>0){
				weixinPayConfig = (WeixinPayConfig)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			Hibernate.initialize(weixinPayConfig);
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
		return weixinPayConfig;
	}

	/* 
	 * 实现通过sql语句来查询微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.WeixinPayConfigDao#queryBysql(java.lang.String)
	 */
	@Override
	public WeixinPayConfig queryBysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		WeixinPayConfig weixinPayConfig = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<WeixinPayConfig> list = new ArrayList<WeixinPayConfig>();
			Hibernate.initialize(weixinPayConfig);
			list = query.list();
			if(list!=null && list.size()>0){
				weixinPayConfig = (WeixinPayConfig)list.get(0);
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
		return weixinPayConfig;
	}

	/* 
	 * 实现添加微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.WeixinPayConfigDao#addWeixinPayConfig(com.gy.model.WeixinPayConfig)
	 */
	@Override
	public boolean addWeixinPayConfig(WeixinPayConfig weixinPayConfig) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.save(weixinPayConfig);
			Hibernate.initialize(weixinPayConfig);
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
	 * 实现保存或者更新微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.WeixinPayConfigDao#saveOrUpdateWeixinPayConfig(com.gy.model.WeixinPayConfig)
	 */
	@Override
	public boolean saveOrUpdateWeixinPayConfig(WeixinPayConfig weixinPayConfig) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(weixinPayConfig);
			Hibernate.initialize(weixinPayConfig);
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
	 * 实现通过id来删除微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.WeixinPayConfigDao#deleteWeixinPayConfigId(int)
	 */
	@Override
	public boolean deleteWeixinPayConfigId(String weixinPayConfigId) {
		// TODO Auto-generated method stub
		WeixinPayConfig weixinPayConfig = queryWeixinPayConfigId(weixinPayConfigId);
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(weixinPayConfig);
			Hibernate.initialize(weixinPayConfig);
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
	 * 实现更新微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.dao.WeixinPayConfigDao#updateWeixinPayConfig(com.gy.model.WeixinPayConfig)
	 */
	@Override
	public boolean updateWeixinPayConfig(WeixinPayConfig weixinPayConfig) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(weixinPayConfig);
			Hibernate.initialize(weixinPayConfig);
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
