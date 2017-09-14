package com.gy.servicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

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

}
