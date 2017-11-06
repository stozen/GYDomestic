package com.gy.controller;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.model.AliPayConfig;
import com.gy.services.AliPayConfigService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-25
 * @introduce 这是阿里支付配置的控制器
 */

@Controller
@Scope("singleton")
@RequestMapping(value="alipayconfig")
@CrossOrigin(maxAge=3600, origins="*")
public class AliPayControl {
	
	/**
	 * 声明返回给客户端的状态码
	 */
	private String status;
	
	/**
	 * 声明返回给客户端的提示信息
	 */
	private String message;
	
	/**
	 * 声明阿里支付服务层依赖注入的引用
	 */
	@Autowired
	private AliPayConfigService aliPayConfigService;

	/**
	 * @return
	 */
	public AliPayConfigService getAliPayConfigService() {
		return aliPayConfigService;
	}

	/**
	 * @param aliPayConfigService
	 */
	public void setAliPayConfigService(AliPayConfigService aliPayConfigService) {
		this.aliPayConfigService = aliPayConfigService;
	}
	
	/**
	 * 声明添加阿里支付的配置信息
	 * @param AliMap
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> addAliPayConfig(@RequestBody Map AliMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		String APP_ID = ((String) AliMap.get("APP_ID")).trim();
		String APP_PRIVATE_KEY = ((String) AliMap.get("APP_PRIVATE_KEY")).trim();
		String ALIPAY_PUBLIC_KEY = ((String) AliMap.get("ALIPAY_PUBLIC_KEY")).trim();
		String GAME_PACKAGE = ((String) AliMap.get("GAME_PACKAGE")).trim();
		String NOTIFY_URL = ((String) AliMap.get("NOTIFY_URL")).trim();
		String RETURN_URL = ((String) AliMap.get("RETURN_URL")).trim();
		
		boolean flag = (GAME_PACKAGE.equals("") || "".equals(GAME_PACKAGE)) && (APP_ID.equals("") || "".equals(APP_ID)) && (APP_PRIVATE_KEY.equals("") || "".equals(APP_PRIVATE_KEY)) && (ALIPAY_PUBLIC_KEY.equals("") || "".equals(ALIPAY_PUBLIC_KEY)) && (NOTIFY_URL.equals("") || "".equals(NOTIFY_URL));
		
		if(flag)
		{
			status = "0603";
			message = "输入的数据有空";
		} 
		else
		{
			AliPayConfig aliPayConfigdata = new AliPayConfig();
			aliPayConfigdata = aliPayConfigService.queryGamepackage(GAME_PACKAGE);
			/*1.先判断数据库中是否已经存在这个游戏了，如果存在不添加，给出提示*/
			if(aliPayConfigdata!=null) {
				status = "0602";
				message = "数据库中已经存在！";
			} else {
				AliPayConfig aliPayConfig = new AliPayConfig();
				aliPayConfig.setAPP_ID(APP_ID);
				aliPayConfig.setAPP_PRIVATE_KEY(APP_PRIVATE_KEY);
				aliPayConfig.setALIPAY_PUBLIC_KEY(ALIPAY_PUBLIC_KEY);
				aliPayConfig.setGAME_PACKAGE(GAME_PACKAGE);
				aliPayConfig.setNOTIFY_URL(NOTIFY_URL);
				aliPayConfig.setRETURN_URL(RETURN_URL);
				boolean result = aliPayConfigService.addAliPayConfig(aliPayConfig);
				if(result)
				{
					status = "0200";
					message = "添加成功！";
				}
				else
				{
					status = "0601";
					message = "添加失败！";
				}
			}
		}
		
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 声明删除阿里支付的配置信息
	 * @param AliMap
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteAliPayConfig(@RequestBody Map AliMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		String GAME_PACKAGE = ((String) AliMap.get("GAME_PACKAGE")).trim();
		if(GAME_PACKAGE.equals("") || "".equals(GAME_PACKAGE))
		{
			status = "0603";
			message = "输入的包名为空";
		}
		else
		{
			AliPayConfig aliPayConfig = new AliPayConfig();
			aliPayConfig = aliPayConfigService.queryGamepackage(GAME_PACKAGE);
			if(aliPayConfig==null)
			{
				status = "0604";
				message = "数据库中不存在";
			}
			else
			{
				String aliPayConfigId = aliPayConfig.getAliPayConfigId();
				boolean result = aliPayConfigService.deleteAliPayConfigId(aliPayConfigId);
				if(result)
				{
					status = "0200";
					message = "删除成功！";
				}
				else
				{
					status = "0601";
					message = "删除失败";
				}
			}
		}
		
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 声明查询阿里支付的配置信息
	 * @param AliMap
	 * @return
	 */
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryAliPayConfig(@RequestParam String GAME_PACKAGE) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		if(GAME_PACKAGE.equals("") || "".equals(GAME_PACKAGE))
		{
			status = "0603";
			message = "输入的包名为空";
		}
		else
		{
			AliPayConfig aliPayConfig = new AliPayConfig();
			aliPayConfig = aliPayConfigService.queryGamepackage(GAME_PACKAGE);
			if(aliPayConfig==null)
			{
				status = "0604";
				message = "数据库中不存在";
			}
			else
			{
				status = "0200";
				message = "查询成功!";
				map.put("AlipayConfig", aliPayConfig);
			}
		}
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 声明修改阿里支付的配置信息
	 * @param AliMap
	 * @return
	 */
	@RequestMapping(value="/modify",method=RequestMethod.PUT)
	public @ResponseBody Map<String, Object> modifyAliPayConfig(@RequestBody Map AliMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		String APP_ID = ((String) AliMap.get("APP_ID")).trim();
		String APP_PRIVATE_KEY = ((String) AliMap.get("APP_PRIVATE_KEY")).trim();
		String ALIPAY_PUBLIC_KEY = ((String) AliMap.get("ALIPAY_PUBLIC_KEY")).trim();
		String GAME_PACKAGE = ((String) AliMap.get("GAME_PACKAGE")).trim();
		String NOTIFY_URL = ((String) AliMap.get("NOTIFY_URL")).trim();
		String RETURN_URL = ((String) AliMap.get("RETURN_URL")).trim();
		
		if(GAME_PACKAGE.equals("") || "".equals(GAME_PACKAGE))
		{
			status = "0603";
			message = "输入的包名为空";
		}
		else
		{
			AliPayConfig aliPayConfig = new AliPayConfig();
			aliPayConfig = aliPayConfigService.queryGamepackage(GAME_PACKAGE);
			/*1.先判断数据库中是否已经存在这个游戏了，如果存在不添加，给出提示*/
			if(aliPayConfig==null) {
				status = "0602";
				message = "数据库中不存在！";
			} else {
				aliPayConfig.setAPP_ID(APP_ID);
				aliPayConfig.setAPP_PRIVATE_KEY(APP_PRIVATE_KEY);
				aliPayConfig.setALIPAY_PUBLIC_KEY(ALIPAY_PUBLIC_KEY);
				aliPayConfig.setGAME_PACKAGE(GAME_PACKAGE);
				aliPayConfig.setNOTIFY_URL(NOTIFY_URL);
				aliPayConfig.setRETURN_URL(RETURN_URL);
				boolean result = aliPayConfigService.updateAliPayConfig(aliPayConfig);
				if(result)
				{
					status = "0200";
					message = "更改成功！";
				}
				else
				{
					status = "0601";
					message = "更改失败！";
				}
			}
		}
		
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
}
