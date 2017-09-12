package com.gy.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.gy.dao.UserDao;
import com.gy.daoImpl.UserDaoImpl;
import com.gy.model.User;
import com.gy.services.UserService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @instroduce 这是用户逻辑实现类
 * @date 2017.9.11
 */
public class UserServiceImpl implements UserService {

	/* (non-Javadoc)
	 * @see com.gy.services.UserService#findById(int)
	 * 实现通过userid来查找用户
	 */
	@Override
	public User findById(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

}
