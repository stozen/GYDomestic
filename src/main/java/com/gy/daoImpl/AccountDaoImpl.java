package com.gy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gy.dao.AccountDao;
import com.gy.model.Account;

/**
 * @author Chencongye
 * @date 2017.9.15
 * @version 0.0.1
 * @introduce 这是账户持久层实现层
 */

@Repository
public class AccountDaoImpl implements AccountDao {

	/**
	 * 创建sessionFactory变量注入到这个DAOImpl中
	 */
	@Autowired
	private static SessionFactory sessionFactory;
	
	/**
	 * 创建事务
	 */
	private static Transaction tx;
	
	/**
	 * 创建查询语句
	 */
	@SuppressWarnings("unused")
	private static Query query;
	
	/**
	 * 创建sessionFactory的get方法
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 创建sessionFactory的set方法
	 * @return
	 */
	public static void setSessionFactory(SessionFactory sessionFactory) {
		AccountDaoImpl.sessionFactory = sessionFactory;
	}

	/**
	 * 创建获得Session对象
	 * @return
	 */
	@SuppressWarnings("static-access")
	private Session getSession(){
		return this.getSessionFactory().openSession();
	}
	
	/**
	 * 实现账户单个查询功能
	 * @return true or false
	 */
	@Override
	public Account query(int accountid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Account account = null;
		try {
			account = (Account)session.get(Account.class, accountid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
			/*if(sessionFactory!=null){
				sessionFactory.close();
			}*/
		}
		return account;
	}

	/**
	 * 实现查询所有账户
	 * @return List集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Account> queryAll() {
		// TODO Auto-generated method stub
		List<Account> accounts = new ArrayList<Account>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			accounts = session.createQuery("from Account").list();
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if(session!=null)
			{
				session.close();
			}
		}
		
		return accounts;
	}

	/**
	 * 根据条件来查询账户
	 * @param sql
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public Account querysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Account account = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<Account> list = new ArrayList<Account>();
			list = query.list();
			if(list!=null && list.size()>0){
				account = (Account)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return account;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			/*if(session!=null)
			{
				session.close();
			}*/
		}
		return account;
	}

	/**
	 * 实现账户添加功能
	 * @return true or false
	 */
	@Override
	public boolean save(Account account) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			int i = (Integer) session.save(account);
			if(i>0)
			{
				flag = true;
			}
			else{
				flag = false;
			}
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return flag;
	}

	/**
	 * 增加一些账户
	 * @return true or false
	 */
	@Override
	public void saveAll(Account[] accounts) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(accounts);
	}

	/**
	 * 实现删除一个账户
	 * @return true or false
	 */
	@Override
	public boolean delete(int accountid) {
		// TODO Auto-generated method stub
		Account account = query(accountid);
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(account);
			flag = true;
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return flag;
	}

	/**
	 * 实现删除某些账户
	 * @return true or false
	 */
	@Override
	public boolean deleteAll(Account[] Account) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		boolean flag = false;
		try {
			getSession().delete(Account);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 更新一个账户
	 * @return
	 */
	@Override
	public boolean update(Account account) {
		// TODO Auto-generated method stub
		/*Account account = query(accountid);*/
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(account);
			flag = true;
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (tx!=null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return flag;
	}

	/**
	 * 更新所有账户
	 * @return true or false
	 */
	@Override
	public boolean updateAll(Account[] accounts) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
