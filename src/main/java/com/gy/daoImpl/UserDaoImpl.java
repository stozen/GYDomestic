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
	@SuppressWarnings("unchecked")
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
	public boolean save(User user) {
		// TODO Auto-generated method stub
		/*System.err.println("得到的Session---->"+sessionFactory);*/
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			int i = (Integer) session.save(user);
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
	@SuppressWarnings("unused")
	@Override
	public boolean deleteAll(User[] User) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().delete(User);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建更新用户功能
	 * @return User
	 */
	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(user);
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

	/**
	 * 根据条件来查询用户
	 * @return User
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User querysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		User user = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<User> list = new ArrayList<User>();
			list = query.list();
			if(list!=null && list.size()>0){
				user = (User)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			/*if(session!=null)
			{
				session.close();
			}*/
		}
		return user;
	}
	
	/**
	 * 根据条件来查询用户
	 * @return User
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User queryBysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		User user = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<User> list = new ArrayList<User>();
			list = query.list();
			if(list!=null && list.size()>0){
				user = (User)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return user;
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
		return user;
	}
	
	/**
	 * 实现更新或者添加用户
	 * @return User
	 */
	@Override
	public boolean saveorupdate(User user) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(user);
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
}
