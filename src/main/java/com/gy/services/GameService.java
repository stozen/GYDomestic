package com.gy.services;

import java.util.List;

import com.gy.model.DataCount;
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
	 * 根据条件来查询游戏
	 * @param sql
	 * @return
	 */
	public Game querysql(String sql);
	
	/**
	 * 根据条件来查询游戏
	 * @param sql
	 * @return
	 */
	public Game queryBysql(String sql);
	
	/**
	 * 实现游戏添加功能
	 * @return true or false
	 */
	public boolean save(Game game);
	
	/**
	 * 实现游戏添加或者更新功能
	 * @param user
	 * @return
	 */
	public boolean saveorupdate(Game game);
	
	/**
	 * 增加一些游戏
	 * @return true or false
	 */
	public void saveAll(Game[] games);
	
	/**
	 * 实现删除一个游戏
	 * @return true or false
	 */
	public boolean delete(int gameid);
	
	/**
	 * 实现删除某些游戏
	 * @return true or false
	 */
	public boolean deleteAll(Game[] games);
	
	/**
	 * 更新一个游戏
	 * @return
	 */
	public boolean update(Game game);
		
	/**
	 * 更新所有游戏
	 * @return true or false
	 */
	public boolean updateAll(Game[] games);
	
	/**
	 * 实现插入游戏的功能
	 * @param sql
	 * @return
	 */
	public int insert(String sql);
	
	/**
	 * 实现游戏渠道的
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<DataCount> queryChanel(String beginTime,String endTime);
}
