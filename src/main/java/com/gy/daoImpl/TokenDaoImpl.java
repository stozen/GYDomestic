package com.gy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gy.dao.TokenDao;
import com.gy.model.Token;
import com.gy.model.VerificationCode;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-23
 * @introduce 这是关于Token的dao层实现类
 */

@Repository
public class TokenDaoImpl implements TokenDao {

	/**
	 * 创建Hibernate的会话工厂类
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 创建事务
	 */
	private Transaction tx;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 创建获得Session对象
	 * @return
	 */
	private Session getSession(){
		return this.getSessionFactory().openSession();
	}
	
	/* 
	 * 通过sql语句查询Token
	 * (non-Javadoc)
	 * @see com.gy.dao.TokenDao#querysql(java.lang.String)
	 */
	@Override
	public Token querysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Token token = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<Token> list = new ArrayList<Token>();
			list = query.list();
			if(list!=null && list.size()>0){
				token = (Token)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return token;
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
		return token;
	}

	/* 
	 * 实现保存或者更新一个Token
	 * (non-Javadoc)
	 * @see com.gy.dao.TokenDao#saveOrUpdate(com.gy.model.Token)
	 */
	@Override
	public boolean saveOrUpdate(Token token) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(token);
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

	/* 
	 * 实现更新一个Token
	 * (non-Javadoc)
	 * @see com.gy.dao.TokenDao#update(com.gy.model.Token)
	 */
	@Override
	public boolean update(Token token) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(token);
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

	/* 
	 * 实现删除一个Token
	 * (non-Javadoc)
	 * @see com.gy.dao.TokenDao#delete(int)
	 */
	@Override
	public boolean delete(int tokenid) {
		// TODO Auto-generated method stub
		Token token = querysql("from Token where tokenid="+"'"+tokenid+"'");
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(token);
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

	/* 
	 * 实现保存一个Token
	 * (non-Javadoc)
	 * @see com.gy.dao.TokenDao#save(com.gy.model.Token)
	 */
	@Override
	public boolean save(Token token) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			int i = (Integer) session.save(token);
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
}
