package com.gy.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.gy.model.WeixinPayConfig;
import com.gy.services.WeixinPayConfigService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-25
 * @introduce 这是微信支付的控制器
 */

@Controller
@Scope("singleton")
@RequestMapping(value="weixinconfig")
@CrossOrigin(maxAge=3600, origins="*")
public class WeixinPayControl {

	/**
	 * 返回给客户端的状态码
	 */
	private String status;
	
	/**
	 * 返回给客户端的提示信息
	 */
	private String message;
	
	/**
	 * 声明微信支付的service层的依赖注入的引用
	 */
	@Autowired
	private WeixinPayConfigService weixinPayConfigService;

	/**
	 * 声明微信支付的service层的依赖注入的引用的get方法
	 * @return
	 */
	public WeixinPayConfigService getWeixinPayConfigService() {
		return weixinPayConfigService;
	}

	/**
	 * 声明微信支付的service层的依赖注入的引用的set方法
	 * @param weixinPayConfigService
	 */
	public void setWeixinPayConfigService(
			WeixinPayConfigService weixinPayConfigService) {
		this.weixinPayConfigService = weixinPayConfigService;
	}
	
	/**
	 * 声明添加微信支付的配置信息
	 * @param WeixinMap
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> addWeixinPayConfig(@RequestBody Map WeixinMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		String APP_ID = ((String) WeixinMap.get("APP_ID")).trim();
		String APP_SECRET = ((String) WeixinMap.get("APP_SECRET")).trim();
		String APP_KEY = ((String) WeixinMap.get("APP_KEY")).trim();
		String PARTNER_KEY = ((String) WeixinMap.get("APP_KEY")).trim();
		String MCH_ID = ((String) WeixinMap.get("MCH_ID")).trim();
		String GAME_PACKAGE = ((String) WeixinMap.get("GAME_PACKAGE")).trim();
		String NOTIFY_URL = ((String) WeixinMap.get("NOTIFY_URL")).trim();
		String RETURN_URL = ((String) WeixinMap.get("RETURN_URL")).trim();
		
		boolean flag = (GAME_PACKAGE.equals("") || "".equals(GAME_PACKAGE)) && (APP_ID.equals("") || "".equals(APP_ID)) && (APP_SECRET.equals("") || "".equals(APP_SECRET)) && (APP_KEY.equals("") || "".equals(APP_KEY)) && (MCH_ID.equals("") || "".equals(MCH_ID))&& (NOTIFY_URL.equals("") || "".equals(NOTIFY_URL));
		
		if(flag)
		{
			status = "0603";
			message = "输入的数据有空值！";
		} 
		else
		{
			WeixinPayConfig weixinPayConfigdata = new WeixinPayConfig();
			weixinPayConfigdata = weixinPayConfigService.queryGamepackage(GAME_PACKAGE);
			/*1.先判断数据库中是否已经存在这个游戏了，如果存在不添加，给出提示*/
			if(weixinPayConfigdata!=null) {
				status = "0602";
				message = "数据库中已经存在！";
			} else {
				WeixinPayConfig weixinPayConfig = new WeixinPayConfig();
				weixinPayConfig.setAPP_ID(APP_ID);
				weixinPayConfig.setAPP_KEY(APP_KEY);
				weixinPayConfig.setAPP_SECRET(APP_SECRET);
				weixinPayConfig.setGAME_PACKAGE(GAME_PACKAGE);
				weixinPayConfig.setGRANT_TYPE("client_credential");
				weixinPayConfig.setMCH_ID(MCH_ID);
				weixinPayConfig.setNOTIFY_URL(NOTIFY_URL);
				weixinPayConfig.setPARTNER_KEY(PARTNER_KEY);
				weixinPayConfig.setRETURN_URL(RETURN_URL);
				boolean result = weixinPayConfigService.addWeixinPayConfig(weixinPayConfig);
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
	 * 声明删除微信支付的配置信息
	 * @param WeixinMap
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteWeixinPayConfig(@RequestBody Map WeixinMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		String GAME_PACKAGE = ((String) WeixinMap.get("GAME_PACKAGE")).trim();
		if(GAME_PACKAGE.equals("") || "".equals(GAME_PACKAGE))
		{
			status = "0603";
			message = "输入的包名为空";
		}
		else
		{
			WeixinPayConfig weixinPayConfig = new WeixinPayConfig();
			weixinPayConfig = weixinPayConfigService.queryGamepackage(GAME_PACKAGE);
			if(weixinPayConfig==null)
			{
				status = "0604";
				message = "数据库中不存在";
			}
			else
			{
				String weixinPayConfigId = weixinPayConfig.getWeixinPayConfigId();
				boolean result = weixinPayConfigService.deleteWeixinPayConfigId(weixinPayConfigId);
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
	 * 声明查询微信支付的配置信息
	 * @param WeixinMap
	 * @return
	 */
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryWeixinPayConfig(@RequestParam String GAME_PACKAGE) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(GAME_PACKAGE.equals("") || "".equals(GAME_PACKAGE))
		{
			status = "0603";
			message = "输入的包名为空";
		}
		else
		{
			WeixinPayConfig weixinPayConfig = new WeixinPayConfig();
			weixinPayConfig = weixinPayConfigService.queryGamepackage(GAME_PACKAGE);
			if(weixinPayConfig==null)
			{
				status = "0604";
				message = "数据库中不存在";
			}
			else
			{
				status = "0200";
				message = "查询成功!";
				map.put("WeixinPayConfig", weixinPayConfig);
			}
		}
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 声明修改微信支付的配置信息
	 * @param WeixinMap
	 * @return
	 */
	@RequestMapping(value="/modify",method=RequestMethod.PUT)
	public @ResponseBody Map<String, Object> modifyWeixinPayConfig(@RequestBody Map WeixinMap) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		String APP_ID = ((String) WeixinMap.get("APP_ID")).trim();
		String APP_SECRET = ((String) WeixinMap.get("APP_SECRET")).trim();
		String APP_KEY = ((String) WeixinMap.get("APP_KEY")).trim();
		String PARTNER_KEY = ((String) WeixinMap.get("APP_KEY")).trim();
		String MCH_ID = ((String) WeixinMap.get("MCH_ID")).trim();
		String GAME_PACKAGE = ((String) WeixinMap.get("GAME_PACKAGE")).trim();
		String NOTIFY_URL = ((String) WeixinMap.get("NOTIFY_URL")).trim();
		String RETURN_URL = ((String) WeixinMap.get("RETURN_URL")).trim();

		if(GAME_PACKAGE.equals("") || "".equals(GAME_PACKAGE))
		{
			status = "0603";
			message = "输入的包名为空";
		}
		else
		{
			WeixinPayConfig weixinPayConfig = new WeixinPayConfig();
			weixinPayConfig = weixinPayConfigService.queryGamepackage(GAME_PACKAGE);
			if(weixinPayConfig==null)
			{
				status = "0602";
				message = "数据库中不存在！";
			}
			else
			{
				weixinPayConfig.setAPP_ID(APP_ID);
				weixinPayConfig.setAPP_KEY(APP_KEY);
				weixinPayConfig.setAPP_SECRET(APP_SECRET);
				weixinPayConfig.setGAME_PACKAGE(GAME_PACKAGE);
				weixinPayConfig.setGRANT_TYPE("client_credential");
				weixinPayConfig.setMCH_ID(MCH_ID);
				weixinPayConfig.setNOTIFY_URL(NOTIFY_URL);
				weixinPayConfig.setPARTNER_KEY(PARTNER_KEY);
				weixinPayConfig.setRETURN_URL(RETURN_URL);
				boolean result = weixinPayConfigService.updateWeixinPayConfig(weixinPayConfig);
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
