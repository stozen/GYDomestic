package com.gy.daoImpl;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.model.Account;
import com.gy.model.Game;
import com.gy.model.User;

public class AccountDaoImplTest {

	@Test
	public void testQuery() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		AccountDaoImpl accountDao = (AccountDaoImpl) context.getBean("accountDao");
		
		System.err.println("<------------------------方法调用前,先查询用户开始------------------------>");
		Account account = accountDao.query(1);
		System.err.println(account);
		System.err.println("<------------------------方法调用前,先查询用户结束------------------------>");
	}

	@Test
	public void testQueryAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerysql() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		
		Account account = new Account();
		
		account.setAccountname("facebook");
		account.setAccounttype("2");
		account.setAccoutpasswd("123456");
		
		/*Set<Game> collgame = new HashSet<Game>();
		Game game = new Game();
		game.setGamename("王者荣耀");
		game.setGamepackage("com.tencent.wangzhe");
		game.setRemark("这是王者荣耀游戏");
		user.getGames().add(game);
		collgame.add(game);*/
		
		System.err.println("<------------------------方法调用前------------------------>");
		AccountDaoImpl accountDao = (AccountDaoImpl) context.getBean("accountDao");
		accountDao.save(account);
		System.err.println("<------------------------方法调用后------------------------>");
	}

	@Test
	public void testSaveAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
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
