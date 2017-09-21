package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.GameDao;
import com.gy.model.Game;
import com.gy.services.GameService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是游戏服务层接口实现类
 * @date 2017.9.12
 */

@Service
public class GameServiceImpl implements GameService {

	/**
	 * 实现自动注入gameDao
	 */
	@Autowired
	private GameDao gameDao;
	
	/**
	 * 生成对应的set和get方法
	 * @return
	 */
	public GameDao getGameDao() {
		return gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}

	/**
	 * 实现游戏单个查询功能
	 * @return true or false
	 */
	@Override
	public Game query(int gameid) {
		// TODO Auto-generated method stub
		return gameDao.query(gameid);
	}

	/**
	 * 实现查询所有游戏
	 * @return List集合
	 */
	@Override
	public List<Game> queryAll() {
		// TODO Auto-generated method stub
		return gameDao.queryAll();
	}

	/**
	 * 根据条件来查询游戏
	 * @param sql
	 * @return
	 */
	@Override
	public Game querysql(String sql) {
		// TODO Auto-generated method stub
		return gameDao.querysql(sql);
	}

	/**
	 * 根据条件来查询游戏
	 * @param sql
	 * @return
	 */
	@Override
	public Game queryBysql(String sql) {
		// TODO Auto-generated method stub
		return gameDao.queryBysql(sql);
	}

	/**
	 * 实现游戏添加功能
	 * @return true or false
	 */
	@Override
	public boolean save(Game game) {
		// TODO Auto-generated method stub
		return gameDao.save(game);
	}

	/**
	 * 实现游戏添加或者更新功能
	 * @param user
	 * @return
	 */
	@Override
	public boolean saveorupdate(Game game) {
		// TODO Auto-generated method stub
		return gameDao.saveorupdate(game);
	}

	/**
	 * 增加一些游戏
	 * @return true or false
	 */
	@Override
	public void saveAll(Game[] games) {
		// TODO Auto-generated method stub
		gameDao.saveAll(games);
	}

	/**
	 * 实现删除一个游戏
	 * @return true or false
	 */
	@Override
	public boolean delete(int gameid) {
		// TODO Auto-generated method stub
		return gameDao.delete(gameid);
	}

	/**
	 * 实现删除某些游戏
	 * @return true or false
	 */
	@Override
	public boolean deleteAll(Game[] games) {
		// TODO Auto-generated method stub
		return gameDao.deleteAll(games);
	}

	/**
	 * 更新一个游戏
	 * @return
	 */
	@Override
	public boolean update(Game game) {
		// TODO Auto-generated method stub
		return gameDao.update(game);
	}

	/**
	 * 更新所有游戏
	 * @return true or false
	 */
	@Override
	public boolean updateAll(Game[] games) {
		// TODO Auto-generated method stub
		return gameDao.updateAll(games);
	}

	/**
	 * 插入游戏
	 * @return true or false
	 */
	@Override
	public int insert(String sql) {
		// TODO Auto-generated method stub
		return gameDao.insert(sql);
	}

}
