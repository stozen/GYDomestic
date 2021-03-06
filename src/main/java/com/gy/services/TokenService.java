package com.gy.services;

import com.gy.model.Token;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-23
 * @introduce 这是Token的服务类接口
 */

public interface TokenService {
	
	/**
	 * 根据条件来查询token
	 * @param sql
	 * @return
	 */
	public Token querysql(String sql);
	
	/**
	 * 实现token添加或者更新功能
	 * @param token
	 * @return
	 */
	public boolean saveOrUpdate(Token token);
	
	/**
	 * 更新一个token
	 * @return
	 */
	public boolean update(Token token);
	
	/**
	 * 实现删除一个token
	 * @return true or false
	 */
	public boolean delete(int tokenid);
	
	/**
	 * 实现保存一个token
	 * @param token
	 * @return
	 */
	public boolean save(Token token);
	
}
