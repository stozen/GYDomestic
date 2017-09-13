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
			System.err.println(u.getUsername());
		}
	}

	@Test
	public void testSave() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		User user = new User();
		user.setUsername("李四");
		user.setLogintime(new Date());
		user.setModifytime(new Date());
		user.setRegisttime(new Date());
		user.setPassword("123456");
		
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
		
		User user = userdao.query(1);
		
		System.err.println(user);
		
		System.err.println("<------------------------方法调用删除开始------------------------>");
		userdao.delete(user);
		System.err.println("<------------------------方法调用删除结束------------------------>");
	}

	@Test
	public void testDeleteAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAll() {
		fail("Not yet implemented");
	}

}
