package com.gy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gy.model.DataCount;
import com.gy.model.PayRecord;
import com.gy.services.PayRecordService;
import com.gy.util.Page;


/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-11-7
 * @introduce 这是支付记录查询的控制器
 */
@SuppressWarnings("unused")
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value="/payrecord")
public class PayRecordControl {
	
	/**
	 * 声明返回给客户端的状态码
	 */
	private String status;
	
	/**
	 * 声明返回给客户端的信息
	 */
	private String message;
	
	@Autowired
	private PayRecordService payRecordService;
	
	/**
	 * 声明支付记录的get方法
	 * @return
	 */
	public PayRecordService getPayRecordService() {
		return payRecordService;
	}

	/**
	 * 声明支付记录的get方法
	 * @param payRecordService
	 */
	public void setPayRecordService(PayRecordService payRecordService) {
		this.payRecordService = payRecordService;
	}

	/**
	 * 声明支付记录查询接口，要实现分页查询效果
	 */
	@RequestMapping(value="querypay", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryPay(@RequestParam String beginTime,String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if((beginTime.equals("") || "".equals(beginTime)) && (endTime.equals("") || "".equals(endTime)))
		{
			status = "0602";
			message = "输入的日期数据为空！";
		}
		else
		{
			//查询所有用户个数
			List<DataCount> userdata = payRecordService.queryAllPay(beginTime, endTime);
			
			net.sf.json.JSONArray json = new net.sf.json.JSONArray();
			for (DataCount datacount : userdata) {
				JSONObject jo = new JSONObject();
				jo.put("dataId", datacount.getDataCountId());
				jo.put("dataTime", datacount.getTime());
				jo.put("payCount", datacount.getCount());
				jo.put("payMoney", datacount.getPayMoney());
				jo.put("userCount", datacount.getUserCount());
				json.add(jo);
			}
			
			map.put("data", userdata);
			status = "0200";
			message = "查询成功!";
		}
		
		map.put("status", status);
		map.put("message", message);
		return map;
	}
}
