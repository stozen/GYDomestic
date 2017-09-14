package com.gy.daoImpl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.dao.UserDao;
import com.gy.model.User;

public class UserDaoImplTest {

	@Test
	public void testQuery() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		
		System.err.println("<------------------------方法调用前,先查询用户开始------------------------>");
		User user = userdao.query(2);
		System.err.println(user);
		System.err.println("<------------------------方法调用前,先查询用户结束------------------------>");
	}

	@Test
	public void testQueryAll() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		List<User> users = userdao.queryAll();
		/*for (User user : users) {
			System.err.println(user);
		}*/
		
		Iterator<User> user = users.iterator();
		
		while (user.hasNext()) {
			User u = (User) user.next();
			System.err.println("姓名:"+u.getUsername()+"\n"+"电话号码:"+u.getMobile());
		}
	}

	@Test
	public void testSave() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		User user = new User();
		user.setUsername("lisi");
		user.setLogintime(new Date());
		user.setModifytime(new Date());
		user.setRegisttime(new Date());
		user.setPassword("123456");
		user.setMobile("12345678913");
		
		System.err.println("<------------------------方法调用前------------------------>");
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		userdao.save(user);
		System.err.println("<------------------------方法调用后------------------------>");
	}

	@Test
	public void testSaveAll() {

		/*还没有实现*/
		
	}

	@Test
	public void testDelete() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		/*System.err.println("<------------------------方法调用前,先查询用户开始------------------------>");
		User user = userdao.query(1);
		System.err.println(user.getLogintime()+user.getUsername());
		System.err.println("<------------------------方法调用前,先查询用户结束------------------------>");*/
		
		System.err.println("<------------------------方法调用删除开始------------------------>");
		userdao.delete(6);
		System.err.println("<------------------------方法调用删除结束------------------------>");
	}

	@Test
	public void testDeleteAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		User user = userdao.query(2);
		user.setUsername("李小龙");
		
		System.err.println("<------------------------方法调用更新开始------------------------>");
		userdao.update(2);
		System.err.println("<------------------------方法调用更新结束------------------------>");
	}

	@Test
	public void testUpdateAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerysql() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		String sql = "from User where username='王五' or mobile='123456889'";
		System.err.println("<------------------------方法调用查询开始------------------------>");
		User user = userdao.querysql(sql);
	
		System.err.println(user);
		System.err.println("<------------------------方法调用查询结束------------------------>");
	}
	
}
