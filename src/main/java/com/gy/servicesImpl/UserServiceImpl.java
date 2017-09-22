package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.UserDao;
import com.gy.model.User;
import com.gy.services.UserService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户服务层接口实现类
 * @date 2017.9.12
 */

@Service
public class UserServiceImpl implements UserService {
	
	/**
	 * 自动装配
	 */
	@Autowired
	private UserDao userDao;
	
	/**
	 * 在自动注入的时候，需要生成set和get方法
	 * @return
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * 根据Id查询用户
	 */
	@Override
	public User query(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询所有用户
	 */
	@Override
	public List<User> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 保存用户
	 */
	@Override
	public boolean save(User user) {
		return userDao.save(user);
	}

	/**
	 * 保存所有用户
	 */
	@Override
	public boolean saveAll(User[] users) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 根据Id删除用户
	 */
	@Override
	public boolean delete(int userid) {
		// TODO Auto-generated method stub
		return userDao.delete(userid);
	}

	/**
	 * 删除所有用户
	 */
	@Override
	public boolean deleteAll(User[] User) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 更新用户
	 */
	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		return userDao.update(user);
	}

	/**
	 * 跟新所有用户
	 */
	@Override
	public boolean updateAll(User[] users) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 根据SQL语句来查询所有用户
	 */
	@Override
	public User querysql(String sql) {
		// TODO Auto-generated method stub
		return userDao.querysql(sql);
	}

	/**
	 * 根据SQL语句查询用户
	 */
	@Override
	public User queryBysql(String sql) {
		// TODO Auto-generated method stub
		return userDao.queryBysql(sql);
	}

	/**
	 * 保存或者更新用户
	 */
	@Override
	public boolean saveorupdate(User user) {
		// TODO Auto-generated method stub
		return userDao.saveorupdate(user);
	}
	
}
