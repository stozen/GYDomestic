package com.gy.dao;

import com.gy.model.User;
import com.gy.model.VerificationCode;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-19
 * @introduce 
 */
public interface VerificationCodeDao {
	
	/**
	 * 根据条件来查询验证码
	 * @param sql
	 * @return
	 */
	public VerificationCode querysql(String sql);
	
	/**
	 * 实现验证码添加或者更新功能
	 * @param verificationCode
	 * @return
	 */
	public boolean saveOrUpdate(VerificationCode verificationCode);
	
	/**
	 * 更新一个验证码
	 * @return
	 */
	public boolean update(VerificationCode verificationCode);
	
	/**
	 * 实现删除一个验证码
	 * @return true or false
	 */
	public boolean delete(int verificationCodeId);
	
	/**
	 * @param verificationCode
	 * @return
	 */
	public boolean save(VerificationCode verificationCode);
}
