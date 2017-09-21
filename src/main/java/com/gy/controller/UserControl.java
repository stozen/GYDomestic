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

@Controller
@RequestMapping(value="/user")
public class UserControl {
	
	/**
	 * 创建一个返回给客户端的状态量
	 */
	private String status;
	
	/**
	 * 创建一个返回给客户端的状态信息提示
	 */
	private String message;
	
	/**
	 * 创建一个返回给客户端的userid
	 */
	private int userid;
	
	/**
	 * 创建一个标签用来表示检索的条件真与假
	 */
	private boolean flag = false;
	
	/**
	 * 创建一个短信验证码
	 */
	private String validatecode = "123456";
	
	/**
	 * 创建Token的User对象
	 */
	private String subject = "";
	
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
	 * 自动注入账户的服务层
	 * @return
	 */
	@Autowired
	private AccountService accountService;
	
	/**
	 * 生成对应的set和get方法
	 * @return
	 */
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * 实现自动装配
	 */
	@Autowired
	private GameService gameService;
	
	/**
	 * 生成对应的set和get方法
	 * @return
	 */
	public GameService getGameService() {
		return gameService;
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	/*@RequestMapping(value = "/hello", produces = "text/json;charset=UTF-8")
	public @ResponseBody String hello() {
		
		return "你好！hello";
	}*/
	
	@Autowired
	private JwtUtil jwt;
	
	/**
	 * 这是打印log的信息
	 */
	private static final Logger logger = LogManager.getLogger(UserControl.class);
	
	/**
	 * 这是用户登录模块的使用
	 * @return
	 */
	/*@RequestMapping(value = "/login",headers={"Accept="+MediaType.APPLICATION_JSON_VALUE},method=RequestMethod.GET)*/
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(@RequestBody User user,BindingResult bindingResult,@RequestHeader String token) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		/*1.先判断用户以什么样的方式登录*/
		String type = user.getType().trim();
		
		/*2.一般登录的检索用户所用的账户类型*/
		String sql = "from User u where u.username=" +  "'" + user.getUsername() + "'"+"or u.mobile="+"'"+user.getMobile()+"'"+" or u.email="+"'"+user.getEmail()+"'"+" and u.password="+"'"+user.getPassword()+"'";
		User userdata = null;
		
		switch (type) {
			/*1以用户名加密码的方式登录*/
			case "1":
				
				if(!(user.getUsername().equals("") || "".equals(user.getUsername())) && !(user.getPassword().equals("") || "".equals(user.getPassword())))
				{
					userdata = userService.querysql(sql);
					try {
						userdata = userService.querysql(sql);
						
						/*4.先判断用户输入的数据是否为空*/
						
						if(userdata!=null)
						{
							boolean flag = (userdata.getUsername().equals(user.getUsername())) && (userdata.getPassword().equals(user.getPassword()));
							if(flag){
								status = "0200";
								message = "普通用户名模式登录成功！";
								userid = userdata.getUserid();
								
								Set<Game> games = new HashSet<Game>();
								games = user.getGames();
								if(games.size()<=0)
								{
									System.err.println("登录时不保存游戏，游戏数据为空！");
								}
								else
								{
									System.err.println("登录保存游戏数据！");
									
									Iterator<Game> game = games.iterator();
									
									Game gamedata = new Game();
									
									while(game.hasNext())
									{
										gamedata = game.next();
									}
									gamedata.setUser(userdata);
									
									gameService.saveorupdate(gamedata);
								}
							}
							else
							{
								status = "0403";
								message = "登录失败,用户名或者密码可能输入错误！";
								userid = userdata.getUserid();
							}
						}
						else
						{
							status = "0404";
							message = "不存在该用户！";
							userid = 0;
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					status = "0403";
					message = "用户名或者密码输入值为空！";
					userid = 0;
				}	
				break;
			/*2以邮箱加密码的方式登录*/
			case "2":
				if(!(user.getEmail().equals("") || "".equals(user.getEmail())) && !(user.getPassword().equals("") || "".equals(user.getPassword())))
				{
					userdata = userService.querysql(sql);
					try {
						userdata = userService.querysql(sql);
						
						/*4.先判断用户输入的数据是否为空*/
						
						if(userdata!=null)
						{
							boolean flag = (userdata.getEmail().equals(user.getEmail())) && (userdata.getPassword().equals(user.getPassword()));
							if(flag){
								status = "0200";
								message = "普通邮箱模式登录成功！";
								userid = userdata.getUserid();
								
								Set<Game> games = new HashSet<Game>();
								games = user.getGames();
								if(games.size()<=0)
								{
									System.err.println("登录时不保存游戏，游戏数据为空！");
								}
								else
								{
									System.err.println("登录保存游戏数据！");
									
									Iterator<Game> game = games.iterator();
									
									Game gamedata = new Game();
									
									while(game.hasNext())
									{
										gamedata = game.next();
									}
									gamedata.setUser(userdata);
									
									gameService.saveorupdate(gamedata);
								}
							}
							else
							{
								status = "0403";
								message = "登录失败,邮箱或者密码可能输入错误！";
								userid = userdata.getUserid();
							}
						}
						else
						{
							status = "0404";
							message = "不存在该用户！";
							userid = 0;
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					status = "0403";
					message = "邮箱或者密码输入值为空！";
					userid = 0;
				}	
				break;
			case "3":
				if(!(user.getMobile().equals("") || "".equals(user.getMobile())) && !(user.getPassword().equals("") || "".equals(user.getPassword())))
				{
					userdata = userService.querysql(sql);
					try {
						userdata = userService.querysql(sql);
						
						/*4.先判断用户输入的数据是否为空*/
						
						if(userdata!=null)
						{
							boolean flag = (userdata.getMobile().equals(user.getMobile())) && (userdata.getPassword().equals(user.getPassword()));
							if(flag){
								status = "0200";
								message = "普通手机模式登录成功！";
								userid = userdata.getUserid();
								
								Set<Game> games = new HashSet<Game>();
								games = user.getGames();
								if(games.size()<=0)
								{
									System.err.println("登录时不保存游戏，游戏数据为空！");
								}
								else
								{
									System.err.println("登录保存游戏数据！");
									
									Iterator<Game> game = games.iterator();
									
									Game gamedata = new Game();
									
									while(game.hasNext())
									{
										gamedata = game.next();
									}
									gamedata.setUser(userdata);
									
									gameService.saveorupdate(gamedata);
								}
							}
							else
							{
								status = "0403";
								message = "登录失败,手机或者密码可能输入错误！";
								userid = userdata.getUserid();
							}
						}
						else
						{
							status = "0404";
							message = "不存在该用户！";
							userid = 0;
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					status = "0403";
					message = "手机或者密码输入值为空！";
					userid = 0;
				}	
				break;
			case "4":
				/*这是facebook方式登录*/
				System.err.println("这是facebook方式登录");
				/*message = "这是facebook登录！";*/
				/*1.如果是第一次登录那么进行，写入数据库*/
				Account account = null;
				Set<Account> accounts = new HashSet<Account>();

				accounts = user.getAccounts();
				if(accounts.size()>0)
				{
					Iterator acc = accounts.iterator(); 
					while(acc.hasNext())
					{
						account = (Account) acc.next();
						/*System.err.println(account.getAccountname());*/
						/*System.err.println(account);*/
					}
					if(account.getAccountname().equals("") || "".equals(account.getAccountname()))
					{
						status = "0403";
						message = "用户输入的账号为空!";
						userid = 0;
					}
					else
					{
						String accsql = "from Account where accountname="+"'"+account.getAccountname().trim()+"'"+"and accounttype="+"'"+user.getType()+"'";
						Account accountdata = accountService.querysql(accsql);
						System.err.println(accountdata);
						if(accountdata==null)
						{
							status = "0200";
							message = "第一次登录数据库不存在重新生成账户！！";
							String username = account.getAccountname();
							/*userid = 0;*/
							account.setAccounttype(user.getType());
							user.setRegisttime(new Date());
							user.setAccounts(user.getAccounts());
							user.setEmail("");
							user.setMobile("");
							user.setUsername(account.getAccountname());
							user.setPassword("");
							user.setType(type);
							
							System.err.println("保存数据");
							userService.save(user);
							
							String idsql = "from Account where accountname="+"'"+account.getAccountname().trim()+"'"+"and accounttype="+"'"+user.getType()+"'";
							Account accountdatanew = accountService.querysql(idsql);
							System.err.println(accountdatanew);
							if(accountdatanew==null)
							{
								status = "0404";
								message = "类型参数错误，空指针";
								userid = 0;
							}
							else
							{
								userid = accountdatanew.getUser().getUserid();
							}
							Set<Game> games = new HashSet<Game>();
							games = user.getGames();
							
							if(games.size()<=0)
							{
								System.err.println("登录时不保存游戏，游戏数据为空！");
							}
							else
							{
								System.err.println("登录保存游戏数据！");
								Iterator<Game> game = games.iterator();
								
								Game gamedata = new Game();
								
								while(game.hasNext())
								{
									gamedata = game.next();
								}
								gamedata.setUser(user);
								
								gameService.saveorupdate(gamedata);
							}
							
						}
						else
						{
							if(accountdata.getAccounttype().equals(user.getType()) && accountdata.getAccountname().equals(account.getAccountname()))
							{
								status = "0200";
								message = "第三方Facebook登录成功！";
								userid = accountdata.getUser().getUserid();
								/*1.如果用这个账户去登录别的游戏*/
								Set<Game> games = new HashSet<Game>();
								games = user.getGames();
								
								if(games.size()<=0)
								{
									System.err.println("登录时不保存游戏，游戏数据为空！");
								}
								else
								{
									System.err.println("登录时保存游戏数据！");
									Iterator<Game> game = games.iterator();
									
									Game gamedata = new Game();
									
									while(game.hasNext())
									{
										gamedata = game.next();
									}
									gamedata.setUser(accountdata.getUser());
									gameService.saveorupdate(gamedata);
								}
							}
						}
					}
				}
				else
				{
					status = "0404";
					message = "用户没有填写第三方登录信息！！！";
					userid = 0;
				}
				
				break;
			case "5":
				/*这是twitter方式登录*/
				System.err.println("这是twitter方式登录");
				message = "这是twitter方式登录！";
				/*1.如果是第一次登录那么进行，写入数据库*/
				Account account1 = null;
				Set<Account> accounts1 = new HashSet<Account>();
				accounts1 = user.getAccounts();
				if(accounts1.size()>0)
				{
					Iterator acc = accounts1.iterator(); 
					while(acc.hasNext())
					{
						account1 = (Account) acc.next();
						System.err.println(account1.getAccountname());
						System.err.println(account1);
					}
					if(account1.getAccountname().equals("") || "".equals(account1.getAccountname()))
					{
						status = "0403";
						message = "用户输入的账号为空!";
						userid = 0;
					}
					else
					{
						String accsql = "from Account where accountname="+"'"+account1.getAccountname().trim()+"'"+"and accounttype="+"'"+user.getType()+"'";
						Account accountdata1 = accountService.querysql(accsql);
						System.err.println(accountdata1);
						if(accountdata1==null)
						{
							status = "0200";
							message = "第一次登录数据库不存在重新生成账户！！";
							String username = account1.getAccountname();
							account1.setAccounttype(user.getType());
							user.setRegisttime(new Date());
							user.setAccounts(user.getAccounts());
							user.setEmail("");
							user.setMobile("");
							user.setUsername(account1.getAccountname());
							user.setPassword("");
							user.setType(type);
							System.err.println("保存数据");
							userService.save(user);
							String idsql = "from Account where accountname="+"'"+account1.getAccountname().trim()+"'"+"and accounttype="+"'"+user.getType()+"'";
							Account accountdatanew1 = accountService.querysql(idsql);
							if(accountdatanew1==null)
							{
								status = "0404";
								message = "类型参数错误，空指针";
								userid = 0;
							}
							else
							{
								userid = accountdatanew1.getUser().getUserid();
							}
							Set<Game> games = new HashSet<Game>();
							games = user.getGames();
							
							if(games.size()<=0)
							{
								System.err.println("登录时不保存游戏，游戏数据为空！");
							}
							else
							{
								System.err.println("登录时保存游戏数据！");
								Iterator<Game> game = games.iterator();
								
								Game gamedata = new Game();
								
								while(game.hasNext())
								{
									gamedata = game.next();
								}
								gamedata.setUser(user);
								
								gameService.saveorupdate(gamedata);
							}
							
						}
						else
						{
							if(accountdata1.getAccounttype().equals(user.getType()) && accountdata1.getAccountname().trim().equals(account1.getAccountname()))
							{
								status = "0200";
								message = "第三方Twitter登录成功！";
								userid = accountdata1.getUser().getUserid();
								
								Set<Game> games = new HashSet<Game>();
								games = user.getGames();
								
								if(games.size()<=0)
								{
									System.err.println("登录时不保存游戏，游戏数据为空！");
								}
								else
								{
									System.err.println("登录时保存游戏数据！");
									Iterator<Game> game = games.iterator();
									
									Game gamedata = new Game();
									
									while(game.hasNext())
									{
										gamedata = game.next();
									}
									gamedata.setUser(accountdata1.getUser());
									gameService.saveorupdate(gamedata);
								}
							}
						}
					}
				}
				else
				{
					status = "0404";
					message = "用户没有填写第三方登录信息！！！";
					userid = 0;
				}
				break;
			case "6":
				/*这是googleplay方式登录*/
				System.err.println("这是googleplay方式登录");
				message = "这是googleplay方式登录！";
				/*1.如果是第一次登录那么进行，写入数据库*/
				Account account2 = null;
				Set<Account> accounts2 = new HashSet<Account>();
				accounts2 = user.getAccounts();
				if(accounts2.size()>0)
				{
					Iterator acc = accounts2.iterator(); 
					while(acc.hasNext())
					{
						account2 = (Account) acc.next();
						System.err.println(account2.getAccountname());
						System.err.println(account2);
					}
					if(account2.getAccountname().equals("") || "".equals(account2.getAccountname()))
					{
						status = "0403";
						message = "用户输入的账号为空!";
						userid = 0;
					}
					else
					{
						String accsql = "from Account where accountname="+"'"+account2.getAccountname().trim()+"'"+"and accounttype="+"'"+user.getType()+"'";
						Account accountdata2 = accountService.querysql(accsql);
						System.err.println(accountdata2);
						if(accountdata2==null)
						{
							status = "0200";
							message = "第一次登录数据库不存在重新生成账户！！";
							account2.setAccounttype(user.getType());
							String username = account2.getAccountname();
							user.setRegisttime(new Date());
							user.setAccounts(user.getAccounts());
							user.setEmail("");
							user.setMobile("");
							user.setUsername(account2.getAccountname());
							user.setPassword("");
							user.setType(type);
							/*System.err.println("保存数据");*/
							userService.save(user);
							String idsql = "from Account where accountname="+"'"+account2.getAccountname().trim()+"'"+"and accounttype="+"'"+user.getType()+"'";
							Account accountdatanew2 = accountService.querysql(idsql);
							if(accountdatanew2==null)
							{
								status = "0404";
								message = "类型参数错误，空指针";
								userid = 0;
							}
							else
							{
								userid = accountdatanew2.getUser().getUserid();
							}
							Set<Game> games = new HashSet<Game>();
							games = user.getGames();
							if(games.size()<=0)
							{
								System.err.println("登录时不保存游戏，游戏数据为空！");
							}
							else
							{
								System.err.println("登录时保存游戏数据！");
								Iterator<Game> game = games.iterator();
								
								Game gamedata = new Game();
								
								while(game.hasNext())
								{
									gamedata = game.next();
								}
								gamedata.setUser(user);
								
								gameService.saveorupdate(gamedata);
							}
						}
						else
						{
							if(accountdata2.getAccounttype().equals(user.getType()) && accountdata2.getAccountname().equals(account2.getAccountname()))
							{
								status = "0200";
								message = "第三方GooglePlay登录成功！";
								userid = accountdata2.getUser().getUserid();
								Set<Game> games = new HashSet<Game>();
								games = user.getGames();
								
								if(games.size()<=0)
								{
									System.err.println("登录时不保存游戏，游戏数据为空！");
								}
								else
								{
									System.err.println("登录时保存游戏数据！");
									Iterator<Game> game = games.iterator();
									
									Game gamedata = new Game();
									
									while(game.hasNext())
									{
										gamedata = game.next();
									}
									gamedata.setUser(accountdata2.getUser());
									gameService.saveorupdate(gamedata);
								}
							}
						}
					}
				}
				else
				{
					status = "0404";
					message = "用户没有填写第三方登录信息！！！";
					userid = 0;
				}
				break;
				
		}
		
		/*System.err.println(userdata);*/
		
		if (bindingResult.hasErrors()) {
			map.put("errorCode", "40001");
			map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
		}
		
		/*map.put("username", user.getUsername());*/
		/*map.put("status", new String[]{"战狼","中国"});*/
		map.put("status", status);
		map.put("message", message);
		map.put("userid", userid);
		/*map.put("token",token);
		map.put("refreshtoken",refreshtoken);*/
		
		return map;
	}
	
	/**
	 * 这是用户注册模块，采用的是POST方法，POST一般用于插入数据
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> register(@RequestBody Map map,BindingResult bindingResult) throws Exception {
		/*log.debug("register a new user");*/
		
		/*创建返回给客户端的json数据
		Map<String, Object> map = new HashMap<String, Object>();*/
		
		
		String password = ((String)map.get("password")).trim();
		String type = ((String)map.get("type")).trim();
		String valicode = ((String)map.get("valicode")).trim();
		
		if(valicode.equals("") || "".equals(valicode))
		{
			status = "0403";
			message = "验证码输入为空值！";
			userid = 0;
		}
		else
		{
			if(valicode.equals("123456"))
			{
				/*2.先判断数据库中是否存在这个用户*/
				/*String sql = "from User u where u.username=" + "'" + username + "'"+"or u.mobile="+"'"+mobile+"'"+"or u.email="+"'"+email+"'";
				User userdata = userService.querysql(sql);*/
				
				switch (type) {
				case "1":
					String username = ((String)map.get("username")).trim();
					if(!(username.equals("") || "".equals(username)) && !(password.equals("") || "".equals(password)))
					{
						String namesql = "from User u where u.username=" + "'" + username+"'"+"and u.password="+"'"+password+"'";
						User userdata = userService.querysql(namesql);
						if(userdata!=null)
						{
							status = "0202";
							message = "该用户已经注册了，请重新填写！";
							userid = userdata.getUserid();
						}
						else
						{
							/*4.如果数据库中不存在那么进行注册*/
							User user = new User();
							/*user.setEmail(email);*/
							user.setRegisttime(new Date());
							user.setType(type);
							user.setEmail("");
							user.setMobile("");
							user.setUsername(username);
							user.setPassword(password);
							if(userService.save(user)){
								status = "0200";
								message = "用户名注册成功!";
								User user1 = (User)userService.querysql("from User where username="+"'"+user.getUsername()+"'");
								userid = user1.getUserid();
								/*2.生成Token给客户端*/
								/*subject = JwtUtil.generalSubject(user1);
								token = jwt.createJWT(Constant.JWT_ID, subject, Constant.JWT_TTL);
								2.注册成功需要把相应的token值存储到token表中
								map.put("token", token);*/
							}
						}
					}
					else 
					{
						status = "0403";
						message = "用户输入的用户名或者密码为空！";
						userid = 0;
					}
					break;
				case "2":
					String email = ((String)map.get("email")).trim();
					if(!(email.equals("") || "".equals(email)) && !(password.equals("") || "".equals(password)))
					{
						String namesql = "from User u where u.email=" + "'" +email+"'"+"and u.password="+"'"+password+"'";
						User userdata = userService.querysql(namesql);
						if(userdata!=null)
						{
							status = "0202";
							message = "该用户已经注册了，请重新填写！";
							userid = userdata.getUserid();
						}
						else
						{
							/*4.如果数据库中不存在那么进行注册*/
							User user = new User();
							/*user.setEmail(email);*/
							user.setRegisttime(new Date());
							user.setType(type);
							user.setEmail(email);
							user.setUsername("");
							user.setMobile("");
							user.setPassword(password);
							if(userService.save(user)){
								status = "0200";
								message = "邮箱注册成功!";
								User user1 = (User)userService.querysql("from User where username="+"'"+user.getUsername()+"'");
								userid = user1.getUserid();
							}
						}
					}
					else 
					{
						status = "0403";
						message = "用户输入的邮箱或者密码为空！";
						userid = 0;
					}
					break;
				case "3":
					String mobile = ((String)map.get("mobile")).trim();
					if(!(mobile.equals("") || "".equals(mobile)) && !(password.equals("") || "".equals(password)))
					{
						String namesql = "from User u where u.mobile=" + "'"+mobile+"'"+"and u.password="+"'"+password+"'";
						User userdata = userService.querysql(namesql);
						if(userdata!=null)
						{
							status = "0202";
							message = "该用户已经注册了，请重新填写！";
							userid = userdata.getUserid();
						}
						else
						{
							/*4.如果数据库中不存在那么进行注册*/
							User user = new User();
							/*user.setEmail(email);*/
							user.setRegisttime(new Date());
							user.setType(type);
							user.setMobile(mobile);
							user.setEmail("");
							user.setUsername("");
							user.setPassword(password);
							if(userService.save(user)){
								status = "0200";
								message = "手机注册成功!";
								User user1 = (User)userService.querysql("from User where username="+"'"+user.getUsername()+"'");
								userid = user1.getUserid();
							}
						}
					}
					else 
					{
						status = "0403";
						message = "用户输入的电话或者密码为空！";
						userid = 0;
					}
					break;
				}
			}
			else
			{
				status = "0402";
				message = "验证码填写错误！";
				userid = 0;
			}
		}
		
		/*这一段用来确定用户输入的参数是否有误*/
		if (bindingResult.hasErrors()) {
			map.put("errorCode", "40001");
			map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
		}
		
		map.remove("username");
		map.remove("password");
		map.remove("valicode");
		map.remove("mobile");
		map.remove("email");
		map.remove("type");
		/*map.put("username", username);*/
		map.put("status", status);
		map.put("message", message);
		map.put("userid", userid);
		return map;
		
		}
	
	/**
	 * 这是用户忘记密码模块，采用的方法是PUT方法，PUT方法一般用于更新数据
	 * @return
	 */
	@RequestMapping(value="forgetpassone",method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> forgetPassOne(@RequestBody Map map, BindingResult bindingResult){
	        
	        /*创建返回给客户端的json数据*/
			/*Map<String, Object> map = new HashMap<String, Object>();*/
			
			String mobile = ((String)map.get("mobile")).trim();
			String valicode = ((String)map.get("valicode")).trim();
			
			/*1.先根据用户提供的手机号，查询数据库中是否存在这个用户如果存在则返回为真*/
			/*1.1 先判断用户输入的内容不能为空 */
			String sql = "from User u where u.mobile="+"'"+mobile+"'";
			User userdata = null;
			if(valicode.equals("") || "".equals(valicode))
			{
				status = "0404";
				message = "用户输入的验证码有空！";
				userid = 0;
			}
			else
			{
				if(valicode.equals("123456"))
				{
					boolean judge = (mobile.equals("") || "".equals(mobile));
					if(judge)
					{
						status = "0404";
						message = "用户输入的手机号为空！";
						userid = 0;
					}
					else
					{
						userdata = userService.querysql(sql);
						if(userdata!=null)
						{
							status = "0200";
							message = "成功，进行下一步！";
							userid = userdata.getUserid();
						}
						else
						{
							status = "0404";
							message = "登录不成功，数据库中不存在这个账户！";
							userid = 0;
						}
					}
				}
				else
				{
					status = "0404";
					message = "验证码不对";
					userid = 0;
				}
				
			}
			
	        map.remove("mobile");
	        map.remove("valicode");
	        map.put("status", status);
	        map.put("message", message);
	        map.put("userid", userid);
	        return map;
	    }
	
	/**
	 * 这是用户忘记密码模块，采用的方法是PUT方法，PUT方法一般用于更新数据
	 * @return
	 */
	@RequestMapping(value="forgetpasstwo",method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> forgetPassTwo(@RequestBody Map map,BindingResult bindingResult){
		/*创建返回给客户端的json数据*/
		/*Map<String, Object> map = new HashMap<String, Object>(); */   
		
		String mobile = ((String)map.get("mobile")).trim();
		String password = ((String)map.get("password")).trim();
		String confirmpass = ((String)map.get("confirmpass")).trim();
		
		/*1.先在数据库中查找这个用户*/
		String sql = "from User u where u.mobile="+"'"+mobile.trim()+"'";
		validatecode = "123456";
		User userdata = null;
		boolean judge = (mobile.equals("") || "".equals(mobile));
		if(judge)
		{
			status = "0404";
			message = "手机号为空！";
			userid = 0;
		}
		else
		{
			/*2.更新这个用户的密码*/
			userdata = userService.queryBysql(sql);
			if(userdata != null)
			{
				if((password.equals("")||"".equals(password)) || (confirmpass.equals("")||"".equals(confirmpass)))
				{
					status = "0404";
					message = "用户输入的密码或者确认密码有空值！";
					userid = userdata.getUserid();
				}
				else
				{
					if(confirmpass.equals(password))
					{
						String passworddata = userdata.getPassword();
						
						if(confirmpass.equals(passworddata))
						{
							status = "0403";
							message = "不能使用之前的密码！！！";
							userid = userdata.getUserid();
						}
						else
						{
							userdata.setPassword(confirmpass);
							
							userService.update(userdata);
							
							status = "0200";
							message = "更新密码成功！！！";
							userid = userdata.getUserid();
						}
					}
					else
					{
						status = "0403";
						message = "两次输入的密码不一致！";
						userid = userdata.getUserid();
					}
				}
			}
		}
		
		map.remove("mobile");
		map.remove("password");
		map.remove("confirmpass");
		map.put("status", status);
		map.put("message", message);
		map.put("userid", userid);
        return map;
	}
	
}
