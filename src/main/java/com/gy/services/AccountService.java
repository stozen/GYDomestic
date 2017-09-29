package com.gy.services;

import java.util.List;

import com.gy.model.Account;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-9-18
 * @introduce 这是关于账户服务层的接口
 */

public interface AccountService {
	
	/**
	 * 实现账户单个查询功能
	 * @return true or false
	 */
	public Account query(int accountid);
	
	/**
	 * 实现查询所有账户
	 * @return List集合
	 */
	public List<Account> queryAll();
	
	/**
	 * 根据条件来查询账户
	 * @param sql
	 * @return
	 */
	public Account querysql(String sql);
	
	/**
	 * 实现账户添加功能
	 * @return true or false
	 */
	public boolean save(Account account);
	
	/**
	 * 实现用户添加或者更新功能
	 * @param account
	 * @return
	 */
	public boolean saveorupdate(Account account);
	
	/**
	 * 增加一些账户
	 * @return true or false
	 */
	public void saveAll(Account[] accounts);
	
	/**
	 * 实现删除一个账户
	 * @return true or false
	 */
	public boolean delete(int accountid);
	
	/**
	 * 实现删除某些账户
	 * @return true or false
	 */
	public boolean deleteAll(Account[] Account);
	
	/**
	 * 更新一个账户
	 * @return
	 */
	public boolean update(Account account);
		
	/**
	 * 更新所有账户
	 * @return true or false
	 */
	public boolean updateAll(Account[] accounts);
}
