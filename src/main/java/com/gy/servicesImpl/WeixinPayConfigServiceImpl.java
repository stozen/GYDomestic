package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.WeixinPayConfigDao;
import com.gy.model.WeixinPayConfig;
import com.gy.services.WeixinPayConfigService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-25
 * @introduce 这是微信支付的service层的实现类
 */

@Service
public class WeixinPayConfigServiceImpl implements WeixinPayConfigService {

	/**
	 * 声明微信支付配置的dao层的依赖注入
	 */
	@Autowired
	private WeixinPayConfigDao weixinPayConfigDao;
	
	/**
	 * @return
	 */
	public WeixinPayConfigDao getWeixinPayConfigDao() {
		return weixinPayConfigDao;
	}

	/**
	 * @param weixinPayConfigDao
	 */
	public void setWeixinPayConfigDao(WeixinPayConfigDao weixinPayConfigDao) {
		this.weixinPayConfigDao = weixinPayConfigDao;
	}

	/* 
	 * 通过id来查询微信支付的信息
	 * (non-Javadoc)
	 * @see com.gy.services.WeixinPayConfigService#querWeixinPayConfigId(java.lang.String)
	 */
	@Override
	public WeixinPayConfig queryWeixinPayConfigId(String weixinPayConfigId) {
		// TODO Auto-generated method stub
		return weixinPayConfigDao.queryWeixinPayConfigId(weixinPayConfigId);
	}

	/* 
	 * 实现查询所有微信支付配置的信息
	 * (non-Javadoc)
	 * @see com.gy.services.WeixinPayConfigService#queryAllWeixinPayConfig()
	 */
	@Override
	public List<WeixinPayConfig> queryAllWeixinPayConfig() {
		// TODO Auto-generated method stub
		return weixinPayConfigDao.queryAllWeixinPayConfig();
	}

	/* 
	 * 实现通过包名来查询微信支付的信息
	 * (non-Javadoc)
	 * @see com.gy.services.WeixinPayConfigService#queryGamepackage(java.lang.String)
	 */
	@Override
	public WeixinPayConfig queryGamepackage(String gamePacakge) {
		// TODO Auto-generated method stub
		return weixinPayConfigDao.queryGamepackage(gamePacakge);
	}

	/* 
	 * 实现通过sql语句来查询微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.WeixinPayConfigService#queryBysql(java.lang.String)
	 */
	@Override
	public WeixinPayConfig queryBysql(String sql) {
		// TODO Auto-generated method stub
		return weixinPayConfigDao.queryBysql(sql);
	}

	/* 
	 * 实现添加微信支付配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.WeixinPayConfigService#addWeixinPayConfig(com.gy.model.WeixinPayConfig)
	 */
	@Override
	public boolean addWeixinPayConfig(WeixinPayConfig weixinPayConfig) {
		// TODO Auto-generated method stub
		return weixinPayConfigDao.addWeixinPayConfig(weixinPayConfig);
	}

	/* 
	 * 实现添加或者更新微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.WeixinPayConfigService#saveOrUpdateWeixinPayConfig(com.gy.model.WeixinPayConfig)
	 */
	@Override
	public boolean saveOrUpdateWeixinPayConfig(WeixinPayConfig weixinPayConfig) {
		// TODO Auto-generated method stub
		return weixinPayConfigDao.saveOrUpdateWeixinPayConfig(weixinPayConfig);
	}

	/* 
	 * 实现通过id来删除微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.WeixinPayConfigService#deleteWeixinPayConfigId(int)
	 */
	@Override
	public boolean deleteWeixinPayConfigId(String weixinPayConfigId) {
		// TODO Auto-generated method stub
		return weixinPayConfigDao.deleteWeixinPayConfigId(weixinPayConfigId);
	}

	/* 
	 * 实现修改微信支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.WeixinPayConfigService#updateWeixinPayConfig(com.gy.model.WeixinPayConfig)
	 */
	@Override
	public boolean updateWeixinPayConfig(WeixinPayConfig weixinPayConfig) {
		// TODO Auto-generated method stub
		return weixinPayConfigDao.updateWeixinPayConfig(weixinPayConfig);
	}

}
