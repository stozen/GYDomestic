package com.gy.controller;

import io.jsonwebtoken.Claims;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import net.sf.json.JSONArray;

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
import com.gy.model.DataCount;
import com.gy.model.Game;
import com.gy.model.Token;
import com.gy.model.User;
import com.gy.services.AccountService;
import com.gy.services.GameService;
import com.gy.services.UserService;
import com.gy.servicesImpl.UserServiceImpl;
import com.gy.util.CompareDate;
import com.gy.util.JwtUtil;
import com.gy.util.Page;
import com.gy.util.ResponseUtil;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

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
	 * 声明返回给客户端的状态码
	 */
	private String status;
	
	/**
	 * 返回给客户端的信息
	 */
	private String message;
	
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
	
	/**
	 * 验证易捷服务器的Token
	 */
	@RequestMapping(value="checkLogin",method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkUser(@RequestBody Map map) {
		// TODO Auto-generated method stub
		userService.checkUser(map);
		return map;
	}
	
	@RequestMapping(value="logout",method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> logout(@RequestBody Map map) {
		// TODO Auto-generated method stub
		userService.logout(map);
		return map;
	}
	
	@RequestMapping(value="queryall",method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryAllUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<User> userdata = userService.queryAll();
		 if(userdata.size()==0)
		 {
			 status = "0603";
			 message = "数据库中不存在游戏数据!";
		 }
		 else
		 {
			 status = "0200";
			 message = "查询成功";
			 net.sf.json.JSONArray json = new net.sf.json.JSONArray();
			 for (User user : userdata) {
				 JSONObject jo = new JSONObject();
				 jo.put("userId", user.getUserid());
			     jo.put("userName", user.getUsername());
			     jo.put("registeredTime", user.getRegisttime());
			     jo.put("userMobile", user.getMobile());
			     jo.put("userLogintime", user.getLogintime());
			     jo.put("userModifyTime", user.getModifytime());
			     jo.put("userLoginStatus", user.getLoginStatus());
			     jo.put("userEmail", user.getEmail());
			     
			     json.add(jo);
			 }
			 map.put("user", json);
		 }
		map.put("status", status);
		map.put("message",message);
		return map;
	}
	
	/**
	 * 查询单个用户信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="queryone",method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryUser(@RequestParam String username) {
		/*String username = (String) map.get("username");*/
		Map<String, Object> map = new HashMap<String, Object>();
		if(username.equals("") || "".equals(username))
		{
			status = "0603";
			message = "输入的数据为空!";
		}
		else
		{
			User user = userService.queryBysql("from User where username="+"'"+username+"'");
			if(user == null)
			{
				status = "0604";
				message = "查询的用户不存在";
			}
			else 
			{
				status = "0200";
				message = "查询成功！";
				map.put("userId", user.getUserid());
				map.put("userName", user.getUsername());
				map.put("userRegisteredTime", user.getRegisttime());
				map.put("userModifyTime", user.getModifytime());
				map.put("userMobile", user.getMobile());
				map.put("userLoginTime", user.getLogintime());
				map.put("userEmail", user.getEmail());
				map.put("userLoginStatus", user.getLoginStatus());
			}
		}
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 实现用户修改个人密码功能
	 * @param map
	 * @return
	 */
	@RequestMapping(value="modify",method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> modifyUser(@RequestBody Map map) {
		/*Map<String, Object> map = new HashMap<String,Object>();*/
		
		String username = ((String) map.get("username")).trim();
		
		if(username.equals("") || "".equals(username))
		{
			status = "0603";
			message = "输入用户名的数据为空!";
		}
		else
		{
			User user = userService.queryBysql("from User where username="+"'"+username+"'");
			if(user==null)
			{
				status = "0604";
				message = "查询的用户不存在";
			}
			else
			{
				Date userModifyTime = new Date();
				String userPassword = ((String) map.get("password")).trim();
				
				if(userPassword.equals("") || "".equals(userPassword))
				{
					status = "0602";
					message = "输入的密码为空值!";
				}
				else
				{
					status = "0200";
					message = "更改成功！";
					user.setUsername(username);
					user.setPassword(userPassword);
					user.setModifytime(userModifyTime);
					userService.update(user);
				}
			}
		}
		map.remove("password");
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	@RequestMapping(value="querypage", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryForPage(@RequestParam String pageNo,String pageSize) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		if((pageNo.equals("") || "".equals("")) && (pageSize.equals("") || "".equals(pageSize)))
		{
			status = "0602";
			message = "查询的当前页码为空";
		}
		else
		{
			//查询所有用户个数
			int count = userService.getAllRowCount();
			System.err.println("用户总数:"+count);
			if(pageNo.equals("") || pageNo.equals("1"))
			{
				pageNo = "0";
			}
			Page page = userService.findByPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
			
			List<User> userdata = (List<User>) page.getList();
			
			net.sf.json.JSONArray json = new net.sf.json.JSONArray();
			for (User user : userdata) {
				JSONObject jo = new JSONObject();
				jo.put("userId", user.getUserid());
				jo.put("userName", user.getUsername());
				jo.put("userRegistTime", user.getRegisttime());
				jo.put("userPassword", user.getPassword());
				jo.put("userModifyTime", user.getModifytime());
				jo.put("userLoginTime", user.getLogintime());
				jo.put("userMobile", user.getMobile());
				jo.put("userEmail", user.getEmail());
				json.add(jo);
			}
			
			map.put("user", userdata);
			status = "0200";
			message = "查询成功!";
		}
		
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	@RequestMapping(value="querytime",method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryWeek(@RequestParam String beginTime,String endTime,String pageNo,String pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		if((pageNo.equals("") || "".equals("")) && (pageSize.equals("") || "".equals(pageSize)))
		{
			status = "0602";
			message = "查询的当前页码为空";
		}
		else
		{
			//查询所有用户个数
			int count = userService.getAllRowCount();
			System.err.println("用户总数:"+count);
			if(pageNo.equals("") || pageNo.equals("1"))
			{
				pageNo = "0";
			}
			Page page = userService.queryTime(beginTime, endTime, Integer.valueOf(pageNo), Integer.valueOf(pageSize));
			List<DataCount> userdata = (List<DataCount>) page.getList();
			
			
			net.sf.json.JSONArray json = new net.sf.json.JSONArray();
			for (DataCount datacount : userdata) {
				JSONObject jo = new JSONObject();
				jo.put("dataId", datacount.getDataCountId());
				jo.put("dataTime", datacount.getTime());
				jo.put("dataCount", datacount.getCount());
				json.add(jo);
			}
			
			map.put("data", userdata);
			status = "0200";
			message = "查询成功!";
		}
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date beginDate = sdf.parse(beginTime);
			Date endDate = sdf.parse(endTime);
			System.err.println("开始时间："+beginDate+"\t结束时间："+endDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return map;
	}

	/**
	 * 查询用户活跃度
	 */
	@RequestMapping(value="queryactive",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryActive(@RequestParam String beginTime,String endTime) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		if((beginTime.equals("") || "".equals(beginTime)) && (endTime.equals("") || "".equals(endTime)))
		{
			status = "0603";
			message = "输入的起始时间和截止时间为空";
		}
		else
		{
			//查询所有用户个数
			List<DataCount> userdata = userService.queryActive(beginTime, endTime);
			
			JSONArray json = new JSONArray();
			for (DataCount datacount : userdata) {
				JSONObject jo = new JSONObject();
				jo.put("userId", datacount.getDataCountId());
				jo.put("userTime", datacount.getTime());
				jo.put("userCount", datacount.getCount());
				json.add(jo);
			}
			
			map.put("userActive", json);
			status = "0200";
			message = "查询成功!";
		}
		
		return map;
	}
}
