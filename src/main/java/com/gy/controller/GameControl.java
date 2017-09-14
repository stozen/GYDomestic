package com.gy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.model.Game;
import com.gy.model.Goods;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是游戏控制器
 * @date 2017.9.11
 */

@Controller
@RequestMapping(value="/game")
public class GameControl {
	
	/**
	 * 这是一个添加游戏功能，一般更新采用是POST请求，添加信息
	 * @param game
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> addGame(@RequestBody Game game,BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		
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
		
		return map;
	}
	
	
	/**
	 * 这是一个查询游戏功能，一般更新采用是GET请求，查询信息
	 * @param game
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryGame(@RequestBody Game game,BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
}
