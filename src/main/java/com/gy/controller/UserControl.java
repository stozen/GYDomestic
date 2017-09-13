package com.gy.controller;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView; 

import com.google.gson.Gson;
import com.gy.model.User;
import com.gy.services.UserService;
import com.gy.util.GetRequstBody;

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
	
	@RequestMapping(value = "/login",headers={"Accept="+MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody User login() {
		
		User user = new User();
		user.setUsername("zhangsan");
		
		return user;
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody Map register(HttpServletRequest request, HttpServletResponse response) {
		log.debug("register a new user");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = new User();
		
		Map map = new HashMap();
		map.put("username", username);
		map.put("status", new String[]{"战狼","中国"});
		map.put("userid", "0001");
		return map;
		
		}
	
	@RequestMapping(value="forgetpasswd",method = RequestMethod.GET)
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

}
