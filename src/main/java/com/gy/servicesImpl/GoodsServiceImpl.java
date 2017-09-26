package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.GoodsDao;
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

	/**
	 * 创建商品数据库操作层
	 */
	@Autowired
	private GoodsDao goodsDao;
	
	/**
	 * 创建商品dao层的get方法
	 * @return
	 */
	public GoodsDao getGoodsDao() {
		return goodsDao;
	}

	/**
	 * 创建商品dao层的set方法
	 * @return
	 */
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	@Override
	public Goods query(int goodsid) {
		// TODO Auto-generated method stub
		return goodsDao.query(goodsid);
	}

	@Override
	public List<Goods> queryAll() {
		// TODO Auto-generated method stub
		return goodsDao.queryAll();
	}

	@Override
	public boolean save(Goods goods) {
		// TODO Auto-generated method stub
		return goodsDao.save(goods);
	}

	@Override
	public boolean saveAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return goodsDao.saveAll(goods);
	}

	@Override
	public boolean delete(int goodsid) {
		// TODO Auto-generated method stub
		return goodsDao.delete(goodsid);
	}

	@Override
	public boolean deleteAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return goodsDao.deleteAll(goods);
	}

	@Override
	public boolean modify(int goodsid) {
		// TODO Auto-generated method stub
		return goodsDao.modify(goodsid);
	}

	@Override
	public boolean updateAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return goodsDao.updateAll(goods);
	}
	
}
