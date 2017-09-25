package com.gy.services;

import java.util.List;
import java.util.Map;

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
	 * 根据条件来查询用户数据
	 * @param sql
	 * @return
	 */
	public User querysql(String sql);
	
	/**
	 * 根据条件来查询用户数据
	 * @param sql
	 * @return
	 */
	public User queryBysql(String sql);
	
	/**
	 * 实现用户添加功能
	 * @return true or false
	 */
	public boolean save(User user);
	
	/**
	 * 实现用户添加或者更新功能
	 * @param user
	 * @return
	 */
	public boolean saveorupdate(User user);
	
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
	public boolean update(User user);
		
	/**
	 * 更新所有用户
	 * @return true or false
	 */
	public boolean updateAll(User[] users);
	
	/**
	 * 实现用户登录功能
	 * @param user
	 * @param map
	 */
	public void login(User user,Map map,String type);
	
	/**
	 * 实现用户注册功能
	 */
	public void register(Map map);
	
	/**
	 * 实现忘记密码一
	 * @param map
	 */
	public void forgetpassone(Map map);
	
	/**
	 * 实现忘记密码二
	 * @param map
	 */
	public void forgetpasstwo(Map map);
	
}
