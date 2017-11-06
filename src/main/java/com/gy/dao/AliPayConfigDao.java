package com.gy.dao;

import java.util.List;

import com.gy.model.AliPayConfig;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-25
 * @introduce 这是阿里支付的配置类的Dao层接口
 */

public interface AliPayConfigDao {
	
	/**
	 * 实现阿里支付用户单个查询功能
	 * @return true or false
	 */
	public AliPayConfig queryAliPayConfigId(String aliPayConfigId);
	
	/**
	 * 实现查询所有阿里支付
	 * @return List集合
	 */
	public List<AliPayConfig> queryAllAliPayConfig();
	
	/**
	 * 根据条件来查询阿里支付
	 * @param sql
	 * @return
	 */
	public AliPayConfig queryGamepackage(String gamePacakge);
	
	/**
	 * 根据条件来查询阿里支付
	 * @param sql
	 * @return
	 */
	public AliPayConfig queryBysql(String sql);
	
	/**
	 * 实现阿里支付用户添加功能
	 * @return true or false
	 */
	public boolean addAliPayConfig(AliPayConfig aliPayConfig);
	
	/**
	 * 实现阿里支付添加或者更新功能
	 * @param user
	 * @return
	 */
	public boolean saveOrUpdateAliPayConfig(AliPayConfig aliPayConfig);
	
	/**
	 * 实现删除一个阿里支付
	 * @return true or false
	 */
	public boolean deleteAliPayConfigId(String aliPayConfigId);
	
	/**
	 * 更新一个阿里支付
	 * @return
	 */
	public boolean updateAliPayConfig(AliPayConfig aliPayConfig);
}
