package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.AccountDao;
import com.gy.model.Account;
import com.gy.services.AccountService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-9-18
 * @introduce 这是关于账户实现服务层的接口的实体类
 */

@Service
public class AccountServiceImpl implements AccountService {

	/**
	 * 实现自动注入
	 */
	@Autowired
	private AccountDao accountDao;
	
	/**
	 * 实现AccountDao的get方法
	 * @return
	 */
	public AccountDao getAccountDao() {
		return accountDao;
	}

	/**
	 * 实现AccountDao的set方法
	 * @param accountDao
	 */
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	/**
	 * 实现账户单个查询功能
	 * @return true or false
	 */
	@Override
	public Account query(int accountid) {
		// TODO Auto-generated method stub
		return accountDao.query(accountid);
	}

	/**
	 * 实现查询所有账户
	 * @return List集合
	 */
	@Override
	public List<Account> queryAll() {
		// TODO Auto-generated method stub
		return accountDao.queryAll();
	}

	/**
	 * 根据条件来查询账户
	 * @param sql
	 * @return
	 */
	@Override
	public Account querysql(String sql) {
		// TODO Auto-generated method stub
		return accountDao.querysql(sql);
	}

	/**
	 * 实现账户添加功能
	 * @return true or false
	 */
	@Override
	public boolean save(Account account) {
		// TODO Auto-generated method stub
		return accountDao.save(account);
	}
	
	/**
	 * 增加一些账户
	 * @return true or false
	 */
	@Override
	public void saveAll(Account[] accounts) {
		// TODO Auto-generated method stub
		accountDao.saveAll(accounts);
	}

	/**
	 * 实现删除一个账户
	 * @return true or false
	 */
	@Override
	public boolean delete(int accountid) {
		// TODO Auto-generated method stub
		return accountDao.delete(accountid);
	}

	/**
	 * 实现删除某些账户
	 * @return true or false
	 */
	@Override
	public boolean deleteAll(Account[] Account) {
		// TODO Auto-generated method stub
		return accountDao.deleteAll(Account);
	}

	/**
	 * 更新一个账户
	 * @return
	 */
	@Override
	public boolean update(Account account) {
		// TODO Auto-generated method stub
		return accountDao.update(account);
	}
	
	/**
	 * 更新所有账户
	 * @return true or false
	 */
	@Override
	public boolean updateAll(Account[] accounts) {
		// TODO Auto-generated method stub
		return accountDao.updateAll(accounts);
	}

	@Override
	public boolean saveorupdate(Account account) {
		// TODO Auto-generated method stub
		return accountDao.saveorupdate(account);
	}

	
}
