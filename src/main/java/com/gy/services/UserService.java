package com.gy.services;

import java.util.List;

import com.gy.model.User;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户服务层接口
 * @date 2017.9.12
 */

public interface UserService {
	
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
	 * 实现用户添加功能
	 * @return true or false
	 */
	public boolean save(User user);
	
	/**
	 * 增加一些用户
	 * @return true or false
	 */
	public boolean saveAll(User[] users);
	
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
	public boolean update(int userid);
		
	/**
	 * 更新所有用户
	 * @return true or false
	 */
	public boolean updateAll(User[] users);
}
