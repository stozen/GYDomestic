package com.gy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.gy.model.Game;
import com.gy.services.GameService;
import com.gy.util.JSONTool;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是游戏控制器
 * @date 2017.9.11
 */

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value="game")
@Scope("singleton")
public class GameControl {
	
	/**
	 * 声明返回给客户端的状态码
	 */
	private String status;
	
	/**
	 * 声明返回给客户端的信息
	 */
	private String message;
	
	@Autowired
	private GameService gameService;
	
	/**
	 * 声明游戏服务接口类的依赖注入的get方法
	 * @return
	 */
	public GameService getGameService() {
		return gameService;
	}

	/**
	 * 声明游戏服务接口的依赖注入的set方法
	 * @param gameService
	 */
	public void setGameSeJrvice(GameService gameService) {
		this.gameService = gameService;
	}

	/**
	 * 这是一个添加游戏功能，一般更新采用是POST请求，添加信息
	 * @param game
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> addGame(@RequestBody Game game,BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		if(game==null)
		{
			status = "0604";
			message = "添加数据不符合格式要求！";
		}
		else
		{
			String gameChannels = game.getGameChannels();
			String gameName = game.getGamename();
			String gamePackage = game.getGamepackage();
			String remark = game.getRemark();
			
			boolean flag = (gameChannels.equals("") || "".equals(gameChannels)) && (gameName.equals("") || "".equals(gameName)) && (gamePackage.equals("") || "".equals(gamePackage));
			
			if(flag)
			{
				status = "0603";
				message = "输入的数据有空值!";
			}
			else
			{
				boolean result = gameService.save(game);
				if(result)
				{
					status = "0200";
					message = "添加游戏数据成功!";
				}
				else
				{
					status = "0601";
					message = "添加游戏数据失败！";
				}
			}
		}
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 这是一个删除游戏功能，一般更新采用是DELETE请求，删除信息
	 * @param game
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteGame(@RequestBody Game game,BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(game==null)
		{
			status = "0604";
			message = "添加数据不符合格式要求！";
		}
		else
		{
			String gamePackage = game.getGamepackage();
			if(gamePackage.equals("") || "".equals(gamePackage))
			{
				status = "0603";
				message = "输入的游戏包名有空值!";
			}
			else
			{
				String query_package = "from Game where gamepackage="+"'"+gamePackage+"'";
				Game gamedata = gameService.queryBysql(query_package);
				if(gamedata==null)
				{
					status = "0602";
					message = "数据库中不存在！";
				}
				else
				{
					boolean result = gameService.delete(gamedata.getGameid());
					if(result)
					{
						status = "0200";
						message = "删除成功!";
					}
					else
					{
						status = "0601";
						message = "删除失败";
					}
				}
			}
		}
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	
	/**
	 * 这是一个更新游戏功能，一般更新采用是PUT请求，更新信息
	 * @param game
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateGame(@RequestBody Game game,BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		if(game==null)
		{
			status = "0604";
			message = "添加数据不符合格式要求！";
		}
		else
		{
			String gamePackage = game.getGamepackage();
			if(gamePackage.equals("") || "".equals(gamePackage))
			{
				status = "0603";
				message = "输入的游戏包名有空值!";
			}
			else
			{
				String query_package = "from Game where gamepackage="+"'"+gamePackage+"'";
				Game gamedata = gameService.queryBysql(query_package);
				if(gamedata==null)
				{
					status = "0602";
					message = "数据库中不存在！";
				}
				else
				{
					boolean result = gameService.update(gamedata);
					if(result)
					{
						status = "0200";
						message = "更改成功!";
					}
					else
					{
						status = "0601";
						message = "更改失败";
					}
				}
			}
		}
		
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	
	/**
	 * 这是一个查询游戏功能，一般更新采用是GET请求，查询信息
	 * @param game
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryGame(){
		 Map<String, Object> map = new HashMap<String, Object>();
		 
		 List<Game> gamedata = gameService.queryAll();
		 if(gamedata.size()==0)
		 {
			 status = "0603";
			 message = "数据库中不存在游戏数据!";
		 }
		 else
		 {
			 status = "0200";
			 message = "查询成功";
			 net.sf.json.JSONArray json = new net.sf.json.JSONArray();
			 for (Game game : gamedata) {
				 JSONObject jo = new JSONObject();
			     jo.put("gameid", game.getGameid());
			     jo.put("gameChannels", game.getGameChannels());
			     jo.put("gameName", game.getGamename());
			     jo.put("gamePackage", game.getGamepackage());
			     json.add(jo);
			 }
			 map.put("game", json);
		 }
		
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	/*1.实现渠道分析功能*/
	@RequestMapping(value="querychanel",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryChanel(String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(beginTime.equals("") || "".equals(beginTime)) {
			status = "0602";
			message = "输入的起始时间为空!";
		}
		else if(endTime.equals("") || "".equals(endTime)) {
			status = "0603";
			message = "输入的截止时间为空!";
		} 
		else {
			List<Game> gamedata = gameService.queryChanel(beginTime, endTime);
			status = "0200";
			message = "查询成功!";
			map.put("gameChanel", gamedata);
		}
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
}
