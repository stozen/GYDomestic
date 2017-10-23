package com.gy.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.dao.VerificationCodeDao;
import com.gy.model.VerificationCode;
import com.gy.services.VerificationCodeService;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

	@Autowired
	private VerificationCodeDao verificationCodeDao;
	
	public VerificationCodeDao getVerificationCodeDao() {
		return verificationCodeDao;
	}

	public void setVerificationCodeDao(VerificationCodeDao verificationCodeDao) {
		this.verificationCodeDao = verificationCodeDao;
	}

	@Override
	public VerificationCode querysql(String sql) {
		// TODO Auto-generated method stub
		return verificationCodeDao.querysql(sql);
	}

	@Override
	public boolean saveOrUpdate(VerificationCode verificationCode) {
		// TODO Auto-generated method stub
		return verificationCodeDao.saveOrUpdate(verificationCode);
	}

	@Override
	public boolean update(VerificationCode verificationCode) {
		// TODO Auto-generated method stub
		return verificationCodeDao.update(verificationCode);
	}

	@Override
	public boolean delete(int verificationCodeId) {
		// TODO Auto-generated method stub
		return verificationCodeDao.delete(verificationCodeId);
	}

	@Override
	public boolean save(VerificationCode verificationCode) {
		// TODO Auto-generated method stub
		return verificationCodeDao.save(verificationCode);
	}
	
	
}
