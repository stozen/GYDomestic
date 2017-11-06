package com.gy.services;

import java.util.List;

import com.gy.model.WeixinPayConfig;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-25
 * @introduce 这是微信支付的配置类的Service层接口
 */

public interface WeixinPayConfigService {

	/**
	 * 实现微信支付用户单个查询功能
	 * @return true or false
	 */
	public WeixinPayConfig queryWeixinPayConfigId(String weixinPayConfigId);
	
	/**
	 * 实现查询所有微信支付
	 * @return List集合
	 */
	public List<WeixinPayConfig> queryAllWeixinPayConfig();
	
	/**
	 * 根据条件来查询微信支付
	 * @param sql
	 * @return
	 */
	public WeixinPayConfig queryGamepackage(String gamePacakge);
	
	/**
	 * 根据条件来查询微信支付
	 * @param sql
	 * @return
	 */
	public WeixinPayConfig queryBysql(String sql);
	
	/**
	 * 实现微信支付用户添加功能
	 * @return true or false
	 */
	public boolean addWeixinPayConfig(WeixinPayConfig weixinPayConfig);
	
	/**
	 * 实现微信支付添加或者更新功能
	 * @param user
	 * @return
	 */
	public boolean saveOrUpdateWeixinPayConfig(WeixinPayConfig weixinPayConfig);
	
	/**
	 * 实现删除一个微信支付
	 * @return true or false
	 */
	public boolean deleteWeixinPayConfigId(String weixinPayConfigId);
	
	/**
	 * 更新一个微信支付
	 * @return
	 */
	public boolean updateWeixinPayConfig(WeixinPayConfig weixinPayConfig);
	
}
