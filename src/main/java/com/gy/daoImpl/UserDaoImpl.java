package com.gy.daoImpl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.gy.dao.UserDao;
import com.gy.model.User;
import com.gy.util.HibernateUtil;

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
		return (User)getSession().get(User.class, userid);
	}

	/**
	 * 创建获得查询所有用户功能
	 * @return User
	 */
	@Override
	public List<User> queryAll() {
		// TODO Auto-generated method stub	
		return getSession().createQuery("from User").list();
	}

	/**
	 * 创建添加用户功能
	 * @return User
	 */
	@Override
	public int save(User user) {
		// TODO Auto-generated method stub
		/*System.err.println("得到的Session---->"+sessionFactory);*/
		return (Integer) getSession().save(user);
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
	public void delete(User user) {
		// TODO Auto-generated method stub
		getSession().delete(user);
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
		getSession().update(user);
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
		getSession().flush();
	}

}
