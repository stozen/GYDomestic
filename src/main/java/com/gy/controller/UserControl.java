package com.gy.controller;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView; 

import com.google.gson.Gson;
import com.gy.model.Game;
import com.gy.model.User;
import com.gy.services.UserService;

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
	private static Logger log = Logger.getLogger(UserControl.class);
	
	/*@RequestMapping(value = "/hello", produces = "text/json;charset=UTF-8")
	public @ResponseBody String hello() {
		
		return "你好！hello";
	}*/
	
	/**
	 * 这是用户登录模块的使用
	 * @return
	 */
	/*@RequestMapping(value = "/login",headers={"Accept="+MediaType.APPLICATION_JSON_VALUE},method=RequestMethod.GET)*/
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(@RequestBody User user,BindingResult bindingResult) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		/*一般登录的检索用户所用的账户类型*/
		String sql = "from User u where u.username=" +  "'" + user.getUsername() + "'"+"or u.mobile="+"'"+user.getMobile()+"'"+"'"+user.getEmail()+"'";
		User userdata = null;
		/*如果查询出来说明数据库中存在这个用户*/
		try {
			userdata = userService.querysql(sql);
			boolean judgenull = user.getUsername().equals("") || "".equals(user.getUsername())|| user.getPassword().equals("") || "".equals(user.getPassword()) || user.getMobile().equals("") || "".equals(user.getMobile()) || user.getEmail().equals("") || "".equals(user.getEmail());
			/*1.先判断用户输入的数据是否为空*/
			if(judgenull)
			{
				status = "0404";
				message = "用户输入的内容有空值！";
				userid = 0;
			}
			else
			{
				if(userdata!=null)
				{
					status = "0201";
					message = "该用户存在！";
					boolean flag = (userdata.getUsername().equals(user.getUsername()) || userdata.getMobile().equals(user.getMobile())) && (userdata.getPassword().equals(user.getPassword()));
					if(flag){
						status = "0200";
						message = "登录成功！";
						userid = userdata.getUserid();
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
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*2.检察用户用什么样的方式登录*/
		
		/*System.err.println(userdata);*/
		
		if (bindingResult.hasErrors()) {
			map.put("errorCode", "40001");
			map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
		}
		
		map.put("username", user.getUsername());
		/*map.put("status", new String[]{"战狼","中国"});*/
		map.put("status", status);
		map.put("message", message);
		map.put("userid", userid);
		
		return map;
	}
	
	/**
	 * 这是用户测试模块
	 * @return
	 */
	@RequestMapping(value = "/hello",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> hello(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("status", "0200");
		return map;
	}
	
	/**
	 * 这是用户注册模块，采用的是POST方法，POST一般用于插入数据
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> register(@RequestBody User user,BindingResult bindingResult) {
		/*log.debug("register a new user");*/
		
		/*创建返回给客户端的json数据*/
		Map<String, Object> map = new HashMap<String, Object>();
		
		/*1.首先判断短信验证码输入的是否有误*/
		if(validatecode.equals("123456"))
		{
			/*2.先判断数据库中是否存在这个用户*/
			String sql = "from User u where u.username=" +  "'" + user.getUsername() + "'"+"or u.mobile="+"'"+user.getMobile()+"'"+"'"+user.getEmail()+"'";
			User userdata = null;
			try {
				userdata = userService.querysql(sql);
				boolean judgenull = user.getUsername().equals("") || "".equals(user.getUsername())|| user.getPassword().equals("") || "".equals(user.getPassword()) || user.getMobile().equals("") || "".equals(user.getMobile()) || user.getEmail().equals("") || "".equals(user.getEmail());
				if(judgenull)
				{
					status = "0404";
					message = "用户输入的内容有空值！";
					userid = 0;
				}
				else
				{
					if(userdata!=null){
						/*3.如果用户输入的内容在数据库中存在，那么代表已经注册过了，无须再注册*/
						status = "0403";
						message = "该用户已经注册了，请重新填写！";
						userid = userdata.getUserid();
					}
					else
					{
						/*4.如果数据库中不存在那么进行注册*/
						/*Set<Game> collgame = new HashSet<Game>();
						Game game = new Game();
						game.setGamename("王者荣耀");
						game.setGamepackage("com.tencent.wangzhe");
						game.setRemark("这是王者荣耀游戏");
						collgame.add(game);
						
						user.setGames(collgame);*/
						user.setEmail(user.getEmail());
						user.setRegisttime(new Date());
						user.setUsername(user.getUsername());
						user.setPassword(user.getPassword());
						user.setMobile(user.getMobile());
						if(userService.save(user)){
							status = "0200";
							message = "注册成功!";
							User user1 = (User)userService.querysql("from User where username="+"'"+user.getUsername()+"'");
							userid = user1.getUserid();
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			status = "0404";
			message = "用户输入的短信验证码错误！";
		}
		
		/*这一段用来确定用户输入的参数是否有误*/
		if (bindingResult.hasErrors()) {
			map.put("errorCode", "40001");
			map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
		}
		
		map.put("username", user.getUsername());
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
	public ModelAndView forgetPassOne(String funcId, int fmtId){
	        ModelAndView mav = new ModelAndView();
	        List<User> fmcs = new ArrayList<User>();
	        
	        User user = new User();
	        user.setRegisttime(new Date());
	        user.setUsername("lixiaolong");
	        
	        fmcs.add(user);
	        Gson gson=new Gson();
	        String json_txt = gson.toJson(fmcs);
	        mav.addObject("json_data", json_txt);
	        return mav;
	    }
	
	/**
	 * 这是用户忘记密码模块，采用的方法是PUT方法，PUT方法一般用于更新数据
	 * @return
	 */
	@RequestMapping(value="forgetpasstwo",method = RequestMethod.PUT)
	public ModelAndView forgetPasstwo(String funcId, int fmtId){
	        ModelAndView mav = new ModelAndView();
	        List<User> fmcs = new ArrayList<User>();
	        
	        User user = new User();
	        user.setRegisttime(new Date());
	        user.setUsername("lixiaolong");
	        
	        fmcs.add(user);
	        Gson gson=new Gson();
	        String json_txt = gson.toJson(fmcs);
	        mav.addObject("json_data", json_txt);
	        return mav;
	    }
	
	/**
	 * 这是用户退出登录模块，采用的方法是PUT方法，PUT方法一般用于更新数据
	 * @return
	 */
	@RequestMapping(value="exit",method=RequestMethod.POST)
	public @ResponseBody String exit(){
		System.exit(0);
		return "退出系统";
	}
	
	/*这是测试模块，不用管*/
	/*@RequestMapping(value="/test",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> testPostJson(
			@RequestBody  User user,
			BindingResult bindingResult) {
			
		Map<String, Object> map = new HashMap<String, Object>();
		if (bindingResult.hasErrors()) {
			map.put("errorCode", "40001");
			map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
		}
		
		map.put("user", user);
		return map;
	}
	*/
}
