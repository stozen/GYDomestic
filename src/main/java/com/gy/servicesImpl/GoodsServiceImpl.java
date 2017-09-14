package com.gy.servicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.model.Goods;
import com.gy.services.GoodsService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是商品服务层接口实现类
 * @date 2017.9.12
 */

@Service
public class GoodsServiceImpl implements GoodsService {

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
	
}
