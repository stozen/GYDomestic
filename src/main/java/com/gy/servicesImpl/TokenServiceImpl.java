package com.gy.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.TokenDao;
import com.gy.model.Token;
import com.gy.services.TokenService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-23
 * @introduce 这是关于Token的service层实现类
 */

@Service
public class TokenServiceImpl implements TokenService {

	/**
	 * 声明token的dao层引用
	 */
	@Autowired
	private TokenDao tokenDao;
	
	/**
	 * 使用set和get方法实现依赖注入
	 * @return
	 */
	public TokenDao getTokenDao() {
		return tokenDao;
	}
	
	/**
	 * 使用set和get方法实现依赖注入
	 * @return
	 */
	public void setTokenDao(TokenDao tokenDao) {
		this.tokenDao = tokenDao;
	}

	/* 
	 * 实现通过sql语句来查询Token
	 * (non-Javadoc)
	 * @see com.gy.services.TokenService#querysql(java.lang.String)
	 */
	@Override
	public Token querysql(String sql) {
		// TODO Auto-generated method stub
		return tokenDao.querysql(sql);
	}

	/* 
	 * 实现保存或者更新Token
	 * (non-Javadoc)
	 * @see com.gy.services.TokenService#saveOrUpdate(com.gy.model.Token)
	 */
	@Override
	public boolean saveOrUpdate(Token token) {
		// TODO Auto-generated method stub
		return tokenDao.saveOrUpdate(token);
	}

	/* 
	 * 实现更新Token
	 * (non-Javadoc)
	 * @see com.gy.services.TokenService#update(com.gy.model.Token)
	 */
	@Override
	public boolean update(Token token) {
		// TODO Auto-generated method stub
		return tokenDao.update(token);
	}

	/* 
	 * 实现删除Token
	 * (non-Javadoc)
	 * @see com.gy.services.TokenService#delete(int)
	 */
	@Override
	public boolean delete(int tokenid) {
		// TODO Auto-generated method stub
		return tokenDao.delete(tokenid);
	}

	/* 
	 * 实现保存Token
	 * (non-Javadoc)
	 * @see com.gy.services.TokenService#save(com.gy.model.Token)
	 */
	@Override
	public boolean save(Token token) {
		// TODO Auto-generated method stub
		return tokenDao.save(token);
	}

}
