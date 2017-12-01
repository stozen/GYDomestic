package com.gy.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gy.dao.GameDao;
import com.gy.model.DataCount;
import com.gy.model.Game;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是游戏数据库操作实现层
 * @date 2017.9.12
 */

@Repository
public class GameDaoImpl implements GameDao {

	/**
	 * 创建Hibernate的会话工厂类
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 声明Hibernate会话工厂
	 */
	private Session session;
	
	/**
	 * 声明Hibernate的Query对象的引用
	 */
	private Query query;
	
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
	
	/**
	 * 创建获得查询游戏功能
	 * @return Game
	 */
	@Override
	public Game query(int gameid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Game game = null;
		try {
			game = (Game)session.get(Game.class, gameid);
			Hibernate.initialize(game);
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
		return game;
	}

	/**
	 * 创建获得查询所有游戏功能
	 * @return Game
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Game> queryAll() {
		// TODO Auto-generated method stub
		List<Game> games = new ArrayList<Game>();
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			games = (List<Game>) session.createSQLQuery("select gameid,gameChannels,gamename,gamepackage,remark,userid,createTime from tb_gygame group by gamepackage having(count(gamepackage)>1)").addEntity(Game.class).list();
			Hibernate.initialize(games);
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(tx!=null)
			{
				tx.rollback();
			}
			e.printStackTrace();
		} /*finally {
			if(session!=null)
			{
				session.close();
			}
		}*/
		
		return games;
	}

	/**
	 * 根据条件来查询游戏
	 * @return Game
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Game querysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Game game = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<Game> list = new ArrayList<Game>();
			Hibernate.initialize(game);
			list = query.list();
			if(list!=null && list.size()>0){
				game = (Game)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return game;
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
		return game;
	}

	/**
	 * 根据条件来查询游戏
	 * @return Game
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Game queryBysql(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Game game = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			List<Game> list = new ArrayList<Game>();
			Hibernate.initialize(game);
			list = query.list();
			if(list!=null && list.size()>0){
				game = (Game)list.get(0);
			}
			else
			{
				System.err.println("数组越界,用户输入的内容有空值！！！");
			}
			tx.commit();
			return game;
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
		return game;
	}

	/**
	 * 创建添加游戏功能
	 * @return Game
	 */
	@Override
	public boolean save(Game game) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			int i = (Integer) session.save(game);
			Hibernate.initialize(game);
			if(i>0)
			{
				flag = true;
			}
			else{
				flag = false;
			}
			session.refresh(game);
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
	 * 实现更新或者添加游戏
	 * @return Game
	 */
	@Override
	public boolean saveorupdate(Game game) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(game);
			Hibernate.initialize(game);
			session.refresh(game);
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
	 * 创建批量添加游戏功能
	 */
	@Override
	public void saveAll(Game[] games) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(games);
		Hibernate.initialize(games);
	}

	/**
	 * 创建删除游戏功能
	 * @return Game
	 */
	@Override
	public boolean delete(int gameid) {
		// TODO Auto-generated method stub
		Game game = query(gameid);
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.refresh(game);
			session.delete(game);
			Hibernate.initialize(game);
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
	 * 创建删除所有游戏功能
	 * @return Game
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean deleteAll(Game[] games) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			getSession().delete(games);
			Hibernate.initialize(games);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建更新游戏功能
	 * @return Game
	 */
	@Override
	public boolean update(Game game) {
		// TODO Auto-generated method stub
		Session session = getSession();
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.refresh(game);
			session.update(game);
			Hibernate.initialize(game);
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
	 * 创建批量更新游戏功能
	 * @return Game
	 */
	@Override
	public boolean updateAll(Game[] games) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 创建批量插入游戏功能
	 * @return Game
	 */
	@Override
	public int insert(String sql) {
		// TODO Auto-generated method stub
		Session session = getSession();
		int flag = 0;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			flag = query.executeUpdate();
			
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
	 * 实现游戏渠道分析功能
	 * (non-Javadoc)
	 * @see com.gy.dao.GameDao#queryChanel(java.lang.String, java.lang.String)
	 */
	@Override
	public List<DataCount> queryChanel(String beginTime, String endTime) {
		// TODO Auto-generated method stub
		
		List<DataCount> dataCounts = new ArrayList<DataCount>();
		
		try {
			session = getSession();
			tx = session.beginTransaction();
			query = session.createSQLQuery("SELECT channel.gameid as userid, channel.userCount,channel.gameChannels as payMoney,TIME,gameCount as count FROM (SELECT COUNT(gameChannels) AS gameCount,gameid,COUNT(userid) AS userCount,gameChannels,DATE_FORMAT(createTime,'%Y-%m-%d') AS TIME  FROM tb_gygame WHERE createTime BETWEEN "+"'"+beginTime+"'"+" AND "+"'"+endTime+"'"+" GROUP BY TIME,gameChannels) AS channel GROUP BY TIME").addEntity(DataCount.class);
			dataCounts = (List<DataCount>)query.list();
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			if (session!=null) {
				session.close();
			}
		}
		return dataCounts;
	}

}
