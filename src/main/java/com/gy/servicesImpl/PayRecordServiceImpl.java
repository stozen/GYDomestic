package com.gy.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gy.dao.PayRecordDao;
import com.gy.model.DataCount;
import com.gy.model.PayRecord;
import com.gy.services.PayRecordService;
import com.gy.util.Page;

public class PayRecordServiceImpl implements PayRecordService {
	
	/**
	 * 声明支付记录的引用
	 */
	@Autowired
	private PayRecordDao payRecordDao;
	
	/**
	 * 实现支付记录的get方法
	 * @return
	 */
	public PayRecordDao getPayRecordDao() {
		return payRecordDao;
	}

	/**
	 * 实现支付记录的set方法
	 * @param payRecordDao
	 */
	public void setPayRecordDao(PayRecordDao payRecordDao) {
		this.payRecordDao = payRecordDao;
	}

	/* 
	 * 实现查询支付记录的方法
	 * (non-Javadoc)
	 * @see com.gy.services.PayRecordService#get(java.lang.String)
	 */
	@Override
	public PayRecord get(String out_trade_no) {
		// TODO Auto-generated method stub
		return payRecordDao.get(out_trade_no);
	}

	/* 
	 * 实现更新支付记录的方法
	 * (non-Javadoc)
	 * @see com.gy.services.PayRecordService#update(com.gy.model.PayRecord)
	 */
	@Override
	public void update(PayRecord payRecord) {
		// TODO Auto-generated method stub
		payRecordDao.update(payRecord);
	}

	/* 
	 * 实现添加支付记录的方法
	 * (non-Javadoc)
	 * @see com.gy.services.PayRecordService#add(com.gy.model.PayRecord)
	 */
	@Override
	public void add(PayRecord payRecord) {
		// TODO Auto-generated method stub
		payRecordDao.add(payRecord);
	}

	/* 
	 * 实现删除支付记录的方法
	 * (non-Javadoc)
	 * @see com.gy.services.PayRecordService#delete(java.lang.String)
	 */
	@Override
	public void delete(String out_trade_no) {
		// TODO Auto-generated method stub
		payRecordDao.delete(out_trade_no);
	}

	/* 
	 * 实现查询所有支付记录
	 * (non-Javadoc)
	 * @see com.gy.services.PayRecordService#queryAllPay()
	 */
	@Override
	public List<DataCount> queryAllPay(String beginTime,String endTime) {
		// TODO Auto-generated method stub
        return payRecordDao.queryAllPay(beginTime, endTime);
	}
	
	/* 
	 * 实现查询支付记录的所有记录数
	 * (non-Javadoc)
	 * @see com.gy.services.PayRecordService#getAllRowCount()
	 */
	@Override
	public int getAllRowCount() {
		// TODO Auto-generated method stub
		return payRecordDao.getAllRowCount();
	}
}
