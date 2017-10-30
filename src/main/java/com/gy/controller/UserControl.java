package com.gy.controller;

import io.jsonwebtoken.Claims;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView; 

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.gy.config.Constant;
import com.gy.model.Account;
import com.gy.model.Game;
import com.gy.model.Token;
import com.gy.model.User;
import com.gy.services.AccountService;
import com.gy.services.GameService;
import com.gy.services.UserService;
import com.gy.servicesImpl.UserServiceImpl;
import com.gy.util.CompareDate;
import com.gy.util.JwtUtil;
import com.gy.util.ResponseUtil;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户控制器
 * @date 2017.9.11
 */

@SuppressWarnings("unused")
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value="/user")
public class UserControl {
	
	/**
	 * 创建一个短信验证码
	 */
	private String validatecode = "123456";
	
	/**
	 * 生成返回给客户端的Token
	 */
	private String token = "";
	
	/**
	 * 刷新Token返回给客户端
	 */
	private String refreshtoken = "";
	
	/**
	 * 自动注入用户服务层
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 生成对应的set和get方法
	 * @return
	 */
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 这是打印log的信息
	 */
	private static final Logger logger = LogManager.getLogger(UserControl.class);
	
	/**
	 * 这是用户登录模块的使用
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	/*@RequestMapping(value = "/login",headers={"Accept="+MediaType.APPLICATION_JSON_VALUE},method=RequestMethod.GET)*/
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(@RequestBody User user,BindingResult bindingResult) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		/*1.先判断用户以什么样的方式登录*/
		String type = user.getType().trim();
		
		System.err.println("获取到的用户名:"+user.getUsername());
		userService.login(user, map, type);
		
		return map;
	}
	
	/**
	 * 这是用户注册模块，采用的是POST方法，POST一般用于插入数据
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> register(@RequestBody Map map,BindingResult bindingResult) throws Exception {
		/*log.debug("register a new user");*/
		
		/*创建返回给客户端的json数据
		Map<String, Object> map = new HashMap<String, Object>();*/
		userService.register(map);
		return map;
		
		}
	
	/**
	 * 这是用户忘记密码模块，采用的方法是PUT方法，PUT方法一般用于更新数据
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="forgetpassone",method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> forgetPassOne(@RequestBody Map map, BindingResult bindingResult){
	        
	        /*创建返回给客户端的json数据*/
			/*Map<String, Object> map = new HashMap<String, Object>();*/
			userService.forgetpassone(map);
	        return map;
	    }
	
	/**
	 * 这是用户忘记密码模块，采用的方法是PUT方法，PUT方法一般用于更新数据
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="forgetpasstwo",method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> forgetPassTwo(@RequestBody Map map,BindingResult bindingResult){
		/*创建返回给客户端的json数据*/
		/*Map<String, Object> map = new HashMap<String, Object>(); */   
		userService.forgetpasstwo(map);
        return map;
	}
}
