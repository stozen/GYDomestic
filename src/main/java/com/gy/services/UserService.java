package com.gy.services;

import java.util.List;
import java.util.Map;

import com.gy.model.DataCount;
import com.gy.model.Order;
import com.gy.model.User;
import com.gy.util.Page;

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
	
	/**
	 * 实现CP服务器验证用户登录情况
	 */
	public void checkUser(Map map);
	
	/**
	 * 实现用户注销事件
	 */
	public void logout(Map map);
	
	/**
	 * 实现查询所有用户
	 * @return
	 */
	public int getAllRowCount();
	
	/**
	 * 实现分页查询功能
	 * @param offset
	 * @param length
	 * @return
	 */
	public Page findByPage(int currentPage, int pageSize);
	
	/**
	 * 声明时间查询结果方法
	 * @param beginTime
	 * @param endTime
	 * @param offset
	 * @param length
	 * @return
	 */
	public Page queryTime(String beginTime,String endTime,int currentPage, int pageSize);
	
	/**
	 * 实现用户活跃度统计的功能
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<DataCount> queryActive(String beginTime,String endTime);
	
	/**
	 * 实现用户留存的显示功能
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<DataCount> queryRetained(String beginTime,String endTime);
}
