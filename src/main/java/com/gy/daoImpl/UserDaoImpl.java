package com.gy.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
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
	private Session getCurrentSession(){
		return this.sessionFactory.openSession();
	}

	/**
	 * 创建获得查询功能
	 * @return User
	 */
	@Override
	public User query(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 创建获得查询所有用户功能
	 * @return User
	 */
	@Override
	public List<User> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 创建添加用户功能
	 * @return User
	 */
	@Override
	public boolean save(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建批量添加用户功能
	 * @return User
	 */
	@Override
	public boolean saveAll(User[] users) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建删除用户功能
	 * @return User
	 */
	@Override
	public boolean delete(int userid) {
		// TODO Auto-generated method stub
		return false;
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
	public boolean update(int userid) {
		// TODO Auto-generated method stub
		return false;
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

}
