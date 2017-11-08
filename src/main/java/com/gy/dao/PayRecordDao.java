package com.gy.dao;


import java.util.List;

import com.gy.model.DataCount;
import com.gy.model.PayRecord;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-28
 * @introduce 这是支付记录类接口
 */

public interface PayRecordDao {
	
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
	
	/**
	 * 查询所有支付记录
	 * @return
	 */
	public List<DataCount> queryAllPay(String beginTime,String endTime);
	
    /**
     * 声明支付记录总数
     * @return
     */
    public int getAllRowCount();
}
