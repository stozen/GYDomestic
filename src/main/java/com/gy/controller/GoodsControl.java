package com.gy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.model.Goods;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是商品控制器
 * @date 2017.9.13
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value="/goods")
public class GoodsControl {

	/**
	 * 这是一个添加订商品功能，一般更新采用是POST请求，添加信息
	 * @param goods
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> addGoods(@RequestBody Goods goods,BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	/**
	 * 这是一个删除订商品功能，一般更新采用是DELETE请求，删除信息
	 * @param goods
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteGoods(@RequestBody Goods goods,BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	
	/**
	 * 这是一个更新订商品功能，一般更新采用是PUT请求，更新信息
	 * @param goods
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/modify",method=RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateGoods(@RequestBody Goods goods,BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	
	/**
	 * 这是一个查询订商品功能，一般更新采用是GET请求，查询信息
	 * @param goods
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/query",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryGoods(@RequestBody Goods goods,BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
}
