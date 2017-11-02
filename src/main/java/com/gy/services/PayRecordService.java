package com.gy.services;

import com.gy.model.PayRecord;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 20147-10-31
 * @introduce 这是支付记录服务接口
 */

public interface PayRecordService {
	
	/**
	 * 通过订单编号来查询支付记录
	 * @param out_trade_no
	 * @return
	 */
	public PayRecord get(String out_trade_no);

	/**
	 * 实现更新支付记录
	 * @param payRecord
	 */
	public void update(PayRecord payRecord);
	
	/**
	 * 实现添加支付记录
	 * @param payRecord
	 */
	public void add(PayRecord payRecord);
	
	/**
	 * 实现删除支付记录
	 * @param payRecord
	 */
	public void delete(String out_trade_no);
	
}
