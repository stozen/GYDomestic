package com.gy.daoImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.dao.UserDao;
import com.gy.model.User;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户数据实现的持久层
 * @date 2017.9.12
 */
@Repository
public class UserDaoImpl implements UserDao{	
	
	private static final String Integer = null;

	/**
	 * 创建Hibernate的会话工厂类
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
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

	
	/*private HibernateTemplate hibernateTemplate;*/
	
	/**
	 * 创建获得查询功能
	 * @return User
	 */
	@Override
	public User query(int userid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		User user = null;
		try {
			user = (User)session.get(User.class, userid);
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
		return user;
	}

	/**
	 * 创建获得查询所有用户功能
	 * @return User
	 */
	@Override
	public List<User> queryAll() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			users = session.createQuery("from User").list();
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
		
		return users;
	}

	/**
	 * 创建添加用户功能
	 * @return User
	 */
	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		/*System.err.println("得到的Session---->"+sessionFactory);*/
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			session.save(user);
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

	/**
	 * 创建批量添加用户功能
	 */
	@Override
	public void saveAll(User[] users) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(users);
	}

	/**
	 * 创建删除用户功能
	 * @return User
	 */
	@Override
	public boolean delete(int userid) {
		// TODO Auto-generated method stub
		User user = query(userid);
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(user);
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
	 * 创建删除所有用户功能
	 * @return User
	 */
	@Override
	public boolean deleteAll(User[] User) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建更新用户功能
	 * @return User
	 */
	@Override
	public void update(int userid) {
		// TODO Auto-generated method stub
		User user = query(userid);
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			session.update(user);
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

	/**
	 * 创建批量更新用户功能
	 * @return User
	 */
	@Override
	public boolean updateAll(User[] users) {
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

}
