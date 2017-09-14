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
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public @ResponseBody Map login(HttpServletRequest request,HttpServletResponse response) {
		
		/*User user = new User();
		user.setUsername("zhangsan");*/
		String username = request.getParameter("username");
		
		Map map = new HashMap();
		map.put("username", username);
		map.put("status", new String[]{"战狼","中国"});
		map.put("userid", "0001");
		
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
		log.debug("register a new user");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (bindingResult.hasErrors()) {
			map.put("errorCode", "40001");
			map.put("errorMsg", bindingResult.getFieldError().getDefaultMessage());
		}
		
		user.setUsername(user.getUsername());
		user.setPassword(user.getPassword());
		user.setRegisttime(new Date());
		user.setMobile(user.getMobile());
		
		userService.save(user);
		map.put("username", user.getUsername());
		map.put("status", new String[]{"战狼","中国"});
		map.put("password", user.getPassword());
		map.put("userid", "0001");
		return map;
		
		}
	
	/**
	 * 这是用户忘记密码模块，采用的方法是PUT方法，PUT方法一般用于更新数据
	 * @return
	 */
	@RequestMapping(value="forgetpasswd",method = RequestMethod.PUT)
	public ModelAndView listFormField(String funcId, int fmtId){
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

	@RequestMapping(value="/test",method=RequestMethod.POST)
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
	
}
