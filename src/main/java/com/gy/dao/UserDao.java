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
	public User query(int userid);
	
	/**
	 * 实现查询所有用户
	 * @return List集合
	 */
	public List<User> queryAll();
	
	/**
	 * 根据条件来查询用户
	 * @param sql
	 * @return
	 */
	public User querysql(String sql);
	
	/**
	 * 实现用户添加功能
	 * @return true or false
	 */
	public boolean save(User user);
	
	/**
	 * 增加一些用户
	 * @return true or false
	 */
	public void saveAll(User[] users);
	
	/**
	 * 实现删除一个用户
	 * @return true or false
	 */
	public boolean delete(int userid);
	
	/**
	 * 实现删除某些用户
	 * @return true or false
	 */
	public boolean deleteAll(User[] User);
	
	/**
	 * 更新一个用户
	 * @return
	 */
	public void update(int userid);
		
	/**
	 * 更新所有用户
	 * @return true or false
	 */
	public boolean updateAll(User[] users);

	/**
	 * 刷新Session
	 * @return true or false
	 */
	public void flush();
}
