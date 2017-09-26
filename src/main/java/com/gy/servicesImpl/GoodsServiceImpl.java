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

	/**
	 * 创建查询商品
	 * @return
	 */
	@Override
	public Goods query(int goodsid) {
		// TODO Auto-generated method stub
		return goodsDao.query(goodsid);
	}

	/**
	 * 创建查询所有商品
	 * @return
	 */
	@Override
	public List<Goods> queryAll() {
		// TODO Auto-generated method stub
		return goodsDao.queryAll();
	}

	/**
	 * 创建保存商品
	 * @return
	 */
	@Override
	public boolean save(Goods goods) {
		// TODO Auto-generated method stub
		return goodsDao.save(goods);
	}

	/**
	 * 创建保存商品
	 * @return
	 */
	@Override
	public boolean saveAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return goodsDao.saveAll(goods);
	}

	/**
	 * 创建删除商品
	 * @return
	 */
	@Override
	public boolean delete(int goodsid) {
		// TODO Auto-generated method stub
		return goodsDao.delete(goodsid);
	}

	/**
	 * 创建删除所有商品
	 * @return
	 */
	@Override
	public boolean deleteAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return goodsDao.deleteAll(goods);
	}

	/**
	 * 创建修改商品
	 * @return
	 */
	@Override
	public boolean modify(int goodsid) {
		// TODO Auto-generated method stub
		return goodsDao.modify(goodsid);
	}

	/**
	 * 创建更新所有商品
	 * @return
	 */
	@Override
	public boolean updateAll(Goods[] goods) {
		// TODO Auto-generated method stub
		return goodsDao.updateAll(goods);
	}
	
}
