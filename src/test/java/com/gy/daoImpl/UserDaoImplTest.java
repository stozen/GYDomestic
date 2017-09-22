package com.gy.daoImpl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.model.Game;
import com.gy.model.User;

public class UserDaoImplTest {

	@SuppressWarnings("resource")
	@Test
	public void testQuery() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		
		System.err.println("<------------------------方法调用前,先查询用户开始------------------------>");
		User user = userdao.query(2);
		System.err.println(user);
		System.err.println("<------------------------方法调用前,先查询用户结束------------------------>");
	}

	@SuppressWarnings("resource")
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

	@SuppressWarnings("resource")
	@Test
	public void testSave() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		
		User user = new User();
		
		user.setUsername("wangwu");
		user.setEmail("1234567@qq.com");
		user.setLogintime(new Date());
		user.setModifytime(new Date());
		user.setRegisttime(new Date());
		user.setPassword("123456");
		user.setMobile("12345678914");
		user.setType("2");
		
		/*Set<Game> collgame = new HashSet<Game>();*/
		Game game = new Game();
		game.setGamename("王者荣耀");
		game.setGamepackage("com.tencent.wangzhe");
		game.setRemark("这是王者荣耀游戏");
		user.getGames().add(game);
		/*collgame.add(game);*/
		
		System.err.println("<------------------------方法调用前------------------------>");
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		userdao.save(user);
		System.err.println("<------------------------方法调用后------------------------>");
	}

	@Test
	public void testSaveAll() {

		/*还没有实现*/
		
	}

	@SuppressWarnings("resource")
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

	@SuppressWarnings("resource")
	@Test
	public void testUpdate() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		
		User user = userdao.query(3);
		user.setUsername("李龙");
		
		System.err.println("<------------------------方法调用更新开始------------------------>");
		userdao.update(user);
		System.err.println("<------------------------方法调用更新结束------------------------>");
	}

	@Test
	public void testUpdateAll() {
		fail("Not yet implemented");
	}

	@SuppressWarnings("resource")
	@Test
	public void testQuerysql() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		UserDaoImpl userdao = (UserDaoImpl) context.getBean("userDao");
		String sql = "from User where username='王五' or mobile='123456889'";
		String selectsql = "from User u,Account acc where u.userid=acc.userid";
		System.err.println("<------------------------方法调用查询开始------------------------>");
		User user = userdao.querysql(sql);
	
		System.err.println(userdao.querysql(selectsql));
		System.err.println(user);
		System.err.println("<------------------------方法调用查询结束------------------------>");
	}
	
}
