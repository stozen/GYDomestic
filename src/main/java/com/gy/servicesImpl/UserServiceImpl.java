package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.UserDao;
import com.gy.daoImpl.UserDaoImpl;
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
	
	@Override
	public User query(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAll(User[] users) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(User[] User) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAll(User[] users) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
