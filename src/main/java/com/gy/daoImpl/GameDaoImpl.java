package com.gy.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.dao.GameDao;
import com.gy.model.Game;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是游戏数据库操作实现层
 * @date 2017.9.12
 */

@Repository
public class GameDaoImpl implements GameDao {

	@Override
	public Game query(int gameid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAll(Game[] games) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int gameid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(Game[] Game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int gameid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAll(Game[] game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
	
}
