package com.gy.daoImpl;

import java.util.List;

import com.gy.dao.UserDao;
import com.gy.model.User;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户数据实现的持久层
 * @date 2017.9.11
 */
public class UserDaoImpl implements UserDao{

	
	/* (non-Javadoc)
	 * @see com.gy.dao.UserDao#findById(int)
	 * 根据id查询一个用户
	 */
	@Override
	public User findById(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.gy.dao.UserDao#findByAll()
	 * 查询所有用户
	 */
	@Override
	public List<User> findByAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.gy.dao.UserDao#add(com.gy.model.User)
	 * 添加一个用户
	 */
	@Override
	public boolean add(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.gy.dao.UserDao#addSome()
	 * 添加一些用户
	 */
	@Override
	public boolean addSome() {
		// TODO Auto-generated method stub
		return false;
	}

	
	/* (non-Javadoc)
	 * @see com.gy.dao.UserDao#deleteById(int)
	 * 根据id删除一个用户
	 */
	@Override
	public boolean deleteById(int userid) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.gy.dao.UserDao#deleteBySome(java.lang.Object[])
	 * 删除一些用户
	 */
	@Override
	public boolean deleteBySome(Object[] User) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteByAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateById(int userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateSome() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

}
