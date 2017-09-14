package com.gy.services;

import java.util.List;

import com.gy.model.Game;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是游戏服务层接口
 * @date 2017.9.12
 */

public interface GameService {

	/**
	 * 实现游戏单个查询功能
	 * @return true or false
	 */
	public Game query(int gameid);
	
	/**
	 * 实现查询所有游戏
	 * @return List集合
	 */
	public List<Game> queryAll();
	
	/**
	 * 实现游戏添加功能
	 * @return true or false
	 */
	public boolean save(Game game);
	
	/**
	 * 增加一些游戏
	 * @return true or false
	 */
	public boolean saveAll(Game[] games);
	
	/**
	 * 实现删除一个用户
	 * @return true or false
	 */
	public boolean delete(int gameid);
	
	/**
	 * 实现删除某些用户
	 * @return true or false
	 */
	public boolean deleteAll(Game[] Game);
	
	/**
	 * 更新一个游戏
	 * @return
	 */
	public boolean update(int gameid);
		
	/**
	 * 更新所有游戏
	 * @return true or false
	 */
	public boolean updateAll(Game[] game);

	/**
	 * 刷新Session
	 * @return true or false
	 */
}
