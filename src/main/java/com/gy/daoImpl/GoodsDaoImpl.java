package com.gy.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.dao.GoodsDao;
import com.gy.model.Goods;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是商品数据库操作实现层
 * @date 2017.9.12
 */

@Repository
public class GoodsDaoImpl implements GoodsDao {

	@Override
	public Goods query(int goodsid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Goods> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Goods goods) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int goodsid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int goodsid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

}
