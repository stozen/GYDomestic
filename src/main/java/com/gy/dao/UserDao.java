package com.gy.dao;

import java.util.List;

import com.gy.model.User;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户数据库操作层
 * @date 2017.9.11
 */
public interface UserDao {
	
	/**
	 * 实现用户单个查询功能
	 * @return true or false
	 */
	public User findById(int userid);
	
	/**
	 * 实现查询所有用户
	 * @return List集合
	 */
	public List<User> findByAll();
	
	/**
	 * 实现用户添加功能
	 * @return true or false
	 */
	public boolean add(User user);
	
	/**
	 * 增加一些用户
	 * @return true or false
	 */
	public boolean addSome();
	
	/**
	 * 实现删除一个用户
	 * @return true or false
	 */
	public boolean deleteById(int userid);
	
	/**
	 * 实现删除某些用户
	 * @return true or false
	 */
	public boolean deleteBySome(Object[] User);
	
	/**
	 * 实现删除所有用户
	 * @return
	 */
	public boolean deleteByAll();
	
	/**
	 * 更新一个用户
	 * @return
	 */
	public boolean updateById(int userid);
	
	/**
	 * 更新某些用户
	 * @return
	 */
	public boolean updateSome();
	
	/**
	 * 更新所有用户
	 * @return true or false
	 */
	public boolean updateAll();
}
