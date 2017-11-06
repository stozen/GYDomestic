package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.AliPayConfigDao;
import com.gy.model.AliPayConfig;
import com.gy.services.AliPayConfigService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-25
 * @introduce 这是阿里支付的service层接口的实现类
 */

@Service
public class AliPayConfigServiceImpl implements AliPayConfigService {

	/**
	 * 声明阿里支付的配置dao层依赖注入引用
	 */
	@Autowired
	private AliPayConfigDao aliPayConfigDao;
	
	/**
	 * 声明阿里支付的配置dao层的依赖注入的get方法
	 * @return
	 */
	public AliPayConfigDao getAliPayConfigDao() {
		return aliPayConfigDao;
	}

	/**
	 * 声明阿里支付的配置dao层的依赖注入的set方法
	 * @param aliPayConfigDao
	 */
	public void setAliPayConfigDao(AliPayConfigDao aliPayConfigDao) {
		this.aliPayConfigDao = aliPayConfigDao;
	}

	/* 
	 * 实现通过id来查询阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.AliPayConfigService#queryAliPayConfigId(java.lang.String)
	 */
	@Override
	public AliPayConfig queryAliPayConfigId(String aliPayConfigId) {
		// TODO Auto-generated method stub
		return aliPayConfigDao.queryAliPayConfigId(aliPayConfigId);
	}

	/* 
	 * 实现查询所有的阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.AliPayConfigService#queryAllAliPayConfig()
	 */
	@Override
	public List<AliPayConfig> queryAllAliPayConfig() {
		// TODO Auto-generated method stub
		return aliPayConfigDao.queryAllAliPayConfig();
	}

	/* 
	 * 实现通过游戏包名来阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.AliPayConfigService#queryGamepackage(java.lang.String)
	 */
	@Override
	public AliPayConfig queryGamepackage(String gamePacakge) {
		// TODO Auto-generated method stub
		return aliPayConfigDao.queryGamepackage(gamePacakge);
	}

	/* 
	 * 通过sql语句来查询阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.AliPayConfigService#queryBysql(java.lang.String)
	 */
	@Override
	public AliPayConfig queryBysql(String sql) {
		// TODO Auto-generated method stub
		return aliPayConfigDao.queryBysql(sql);
	}

	/* 
	 * 实现添加阿里支付配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.AliPayConfigService#addAliPayConfig(com.gy.model.AliPayConfig)
	 */
	@Override
	public boolean addAliPayConfig(AliPayConfig aliPayConfig) {
		// TODO Auto-generated method stub
		return aliPayConfigDao.addAliPayConfig(aliPayConfig);
	}

	/* 
	 * 实现保存或者更新阿里支付配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.AliPayConfigService#saveOrUpdateAliPayConfig(com.gy.model.AliPayConfig)
	 */
	@Override
	public boolean saveOrUpdateAliPayConfig(AliPayConfig aliPayConfig) {
		// TODO Auto-generated method stub
		return aliPayConfigDao.saveOrUpdateAliPayConfig(aliPayConfig);
	}

	/* 
	 * 实现通过id来删除阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.AliPayConfigService#deleteAliPayConfigId(int)
	 */
	@Override
	public boolean deleteAliPayConfigId(String aliPayConfigId) {
		// TODO Auto-generated method stub
		return aliPayConfigDao.deleteAliPayConfigId(aliPayConfigId);
	}

	/* 
	 * 实现更新阿里支付的配置信息
	 * (non-Javadoc)
	 * @see com.gy.services.AliPayConfigService#updateAliPayConfig(com.gy.model.AliPayConfig)
	 */
	@Override
	public boolean updateAliPayConfig(AliPayConfig aliPayConfig) {
		// TODO Auto-generated method stub
		return aliPayConfigDao.updateAliPayConfig(aliPayConfig);
	}

}
