package com.gy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.types.resources.selectors.Date;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.gy.dao.UserDao;
import com.gy.model.DataCount;
import com.gy.model.User;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户数据实现的持久层
 * @date 2017.9.12
 */
@Repository
public class UserDaoImpl implements UserDao{	

	/**
	 * 创建Hibernate的会话工厂类
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 创建事务
	 */
	private Transaction tx;
	
	/**
	 * 
	 */
	private Query query;
	
	/**
	 * 声明Hibernate会话工厂的引用的get方法
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 声明Hibernate的会话工厂的引用的set方法
	 * @param sessionFactory
	 */
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

	/*private HibernateTemplate hibernateTemplate;*/
	
	/**
	 * 创建获得查询功能
	 * @return User
	 */
	@Override
	public User query(int userid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		User user = null;
		try {
			tx = session.beginTransaction();
			user = (User)session.get(User.class, userid);
			Hibernate.initialize(user);
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(tx!=null)
			{
				tx.rollback();
			}
		} finally {
			if(session!=null){
				session.close();
			}
		}
		return user;
	}

	/**
	 * 创建获得查询所有用户功能
	 * @return User
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryAll() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			users = session.createSQLQuery("select * from tb_gyuser").addEntity(User.class).list();
			Hibernate.initialize(users);
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
		
		return users;
	}
	
	
	//查询记录总数
    public int getAllRowCount(){
    	Session session = getSession();
    	int count = 0;
		try {
			tx = session.beginTransaction();
			query = session.createSQLQuery("select * from tb_gyuser");
			count = query.list().size();
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(tx!=null) {
				tx.rollback();
			}
		} finally {
			if(session!=null) {
				session.close();
			}
		}
        return count;  
    }

	/**
	 * 创建添加用户功能
	 * @return User
	 */
	@Override
	public boolean save(User user) {
		// TODO Auto-generated method stub
		/*System.err.println("得到的Session---->"+sessionFactory);*/
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			int i = (Integer) session.save(user);
			Hibernate.initialize(user);
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
	 * 创建批量添加用户功能
	 */
	@Override
	public void saveAll(User[] users) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(users);
	}

	/**
	 * 创建删除用户功能
	 * @return User
	 */
	@Override
	public boolean delete(int userid) {
		// TODO Auto-generated method stub
		User user = query(userid);
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(user);
			Hibernate.initialize(user);
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
	 * 创建删除所有用户功能
	 * @return User
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean deleteAll(User[] User) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().delete(User);
			Hibernate.initialize(User);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建更新用户功能
	 * @return User
	 */
	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(user);
			Hibernate.initialize(user);
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
	 * 创建批量更新用户功能
	 * @return User
	 */
	@Override
	public boolean updateAll(User[] users) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 根据条件来查询用户
	 * @return User
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User querysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		User user = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<User> list = new ArrayList<User>();
			Hibernate.initialize(user);
			list = query.list();
			if(list!=null && list.size()>0){
				user = (User)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return user;
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
		return user;
	}
	
	/**
	 * 根据条件来查询用户
	 * @return User
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User queryBysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		User user = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<User> list = new ArrayList<User>();
			Hibernate.initialize(user);
			list = query.list();
			if(list!=null && list.size()>0){
				user = (User)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return user;
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
		return user;
	}
	
	/**
	 * 实现更新或者添加用户
	 * @return User
	 */
	@Override
	public boolean saveorupdate(User user) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(user);
			Hibernate.initialize(user);
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
	 * 实现查询用户分页功能
	 * (non-Javadoc)
	 * @see com.gy.dao.UserDao#findByPage(int, int)
	 */
	@Override
	public List<User> findByPage(int offset, int length) {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			query = session.createSQLQuery("SELECT userid,email,loginStatus,logintime,mobile,modifytime,passwd,registtime,type,username FROM tb_gyuser").addEntity(User.class);
			query.setFirstResult(offset);
			query.setMaxResults(length);
			users = (List<User>)query.list();
			query.list();
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(tx!=null)
			{
				tx.rollback();
			}
		} finally {
			if(session!=null)
			{
				session.close();
			}
		}
		return users;
	}

	/* 
	 * 实现用户数据统计的功能
	 * (non-Javadoc)
	 * @see com.gy.dao.UserDao#queryTime(java.lang.String, java.lang.String, int, int)
	 */
	public List<DataCount> queryTime(String beginTime,String endTime,int offset,int length) {
		// TODO Auto-generated method stub
		Session session = getSession();
		List<DataCount> dataCounts = new ArrayList<DataCount>();
		try {
			tx = session.beginTransaction();
			query = session.createSQLQuery("SELECT userid, DATE_FORMAT(registtime,'%Y-%m-%d') AS TIME , COUNT(*) AS COUNT, username as userCount,passwd as payMoney FROM tb_gyuser WHERE registtime BETWEEN"+"'"+beginTime+"'"+ " AND "+"'"+endTime+"'"+" GROUP BY TIME").addEntity(DataCount.class);
			query.setFirstResult(offset);
			query.setMaxResults(length);
			dataCounts = (List<DataCount>)query.list();
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(tx!=null)
			{
				tx.rollback();
			}
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		
		return dataCounts;
	}

	
	/* 
	 * 实现用户活跃度的功能
	 * (non-Javadoc)
	 * @see com.gy.dao.UserDao#queryActive(java.lang.String, java.lang.String)
	 */
	public List<DataCount> queryActive(String beginTime,String endTime) {
		
		Session session = getSession();
		List<DataCount> dataCounts = new ArrayList<DataCount>();
		try {
			tx = session.beginTransaction();
			query = session.createSQLQuery("SELECT userid,COUNT(*) AS COUNT,COUNT(userid) AS userCount,COUNT(*) AS payMoney,DATE_FORMAT(logintime,'%Y-%m-%d') AS TIME FROM tb_gyuser WHERE logintime BETWEEN "+"'"+beginTime+"'"+" AND "+"'"+endTime+"'"+" GROUP BY TIME").addEntity(DataCount.class);
			dataCounts = (List<DataCount>)query.list();
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(tx!=null)
			{
				tx.rollback();
			}
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return dataCounts;
	}
	
	/* 
	 * 实现用户留存问题
	 * (non-Javadoc)
	 * @see com.gy.dao.UserDao#queryRetained(java.lang.String, java.lang.String)
	 */
	@Override
	public List<DataCount> queryRetained(String beginTime, String endTime) {
		// TODO Auto-generated method stub
		Session session = getSession();
		List<DataCount> dataCounts = new ArrayList<DataCount>();
		
		try {
			tx = session.beginTransaction();
			/*query = session.createSQLQuery("select sum(u.COUNT)/u1.nowUser as payMoney,u.count as COUNT,u.userid,COUNT(u.userid) as userCount,DATE_FORMAT(u.logintime,'%Y-%m-%d') AS TIME from (select COUNT(*)as COUNT,userid,username,logintime,DATE_FORMAT(logintime,'%Y-%m-%d') AS TIME from tb_gyuser WHERE logintime BETWEEN "+"'"+beginTime+"'"+" and "+"'"+endTime+"'"+" GROUP BY TIME ) as u,(select COUNT(*) as nowUser from tb_gyuser where logintime BETWEEN "+"'"+beginTime+"'"+" and "+"'"+endTime+"'"+") as u1 where u.logintime BETWEEN "+"'"+beginTime+"'"+" and "+"'"+endTime+"'"+" GROUP BY TIME").addEntity(DataCount.class);*/
			query = session.createSQLQuery("select sum(u.COUNT)/u1.nowUser as payMoney,u1.nowUser as nowUser,u.count as COUNT,u.userid,COUNT(u.userid) as userCount,u.username,DATE_FORMAT(u.logintime,'%Y-%m-%d') AS TIME from (select COUNT(*) as COUNT,userid,username,logintime,DATE_FORMAT(logintime,'%Y-%m-%d') AS TIME from tb_gyuser WHERE logintime BETWEEN "+"'"+beginTime+"'"+" and "+"'"+endTime+"'"+" GROUP BY TIME ) as u,(select COUNT(*) as nowUser from tb_gyuser) as u1 where u.logintime BETWEEN "+"'"+beginTime+"'"+" and "+"'"+endTime+"'"+" GROUP BY TIME;").addEntity(DataCount.class);
			dataCounts = (List<DataCount>)query.list();
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(tx!=null) 
			{
				tx.rollback();
			}
		} finally {
			if(session!=null) {
				session.close();
			}
		}
		return dataCounts;
	}
}
