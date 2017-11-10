package com.gy.dao;

import java.util.List;

import com.gy.model.DataCount;
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
	 * 根据条件来查询用户
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
	public boolean update(User user);
		
	/**
	 * 更新所有用户
	 * @return true or false
	 */
	public boolean updateAll(User[] users);

	/**
	 * 查询所有记录
	 * @return
	 */
	public int getAllRowCount();
	
	/**
	 * 实现分页查询功能
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<User> findByPage(int offset,int length);
	
	/**
	 * 实现查询周的新增用户
	 * @param time
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<DataCount> queryTime(String beginTime,String endTime,int offset,int length);
	
	/**
	 * 实现统计用户活跃度的功能
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<DataCount> queryActive(String beginTime,String endTime);
}
