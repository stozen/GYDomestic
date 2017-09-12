package com.gy.services;

import com.gy.model.User;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户逻辑类
 * @date 2017.9.11
 */
public interface UserService {
	
	/**
	 * 实现通过id查找用户
	 * @param userid
	 * @return
	 */
	public User findById(int userid);
}
