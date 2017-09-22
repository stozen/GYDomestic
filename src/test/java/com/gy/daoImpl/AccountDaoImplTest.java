package com.gy.daoImpl;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.model.Account;

public class AccountDaoImplTest {

	@SuppressWarnings("resource")
	@Test
	public void testQuery() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		AccountDaoImpl accountDao = (AccountDaoImpl) context.getBean("accountDao");
		
		System.err.println("<------------------------方法调用前,先查询用户开始------------------------>");
		String accsql = "from Account where accountname= 'facebook' and accounttype='4'";
		Account account = accountDao.querysql(accsql);
		System.err.println(account);
		System.err.println("<------------------------方法调用前,先查询用户结束------------------------>");
	}

	@SuppressWarnings("resource")
	@Test
	public void testQueryAll() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		AccountDaoImpl accountDao = (AccountDaoImpl) context.getBean("accountDao");
		List<Account> accounts = accountDao.queryAll();
		/*for (User user : users) {
			System.err.println(user);
		}*/
		
		Iterator<Account> account = accounts.iterator();
		
		while (account.hasNext()) {
			Account acc = (Account) account.next();
			System.err.println("姓名:"+acc.getAccountname()+"\n"+"账户类型:"+acc.getAccounttype());
		}
	}

	@Test
	public void testQuerysql() {
		fail("Not yet implemented");
	}

	@SuppressWarnings("resource")
	@Test
	public void testSave() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		
		Account account = new Account();
		
		account.setAccountname("twitter");
		account.setAccounttype("3");
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

	@SuppressWarnings("resource")
	@Test
	public void testUpdate() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		AccountDaoImpl accountDao = (AccountDaoImpl) context.getBean("accountDao");
		
		System.err.println("<------------------------方法调用查询该用户开始------------------------>");
		Account account = accountDao.query(1);
		account.setAccountname("twitterid");
		System.err.println("<------------------------方法调用查询该用户结束------------------------>");
		
		System.err.println("<------------------------方法调用更新开始------------------------>");
		accountDao.update(account);
		System.err.println("<------------------------方法调用更新结束------------------------>");
	}

	@Test
	public void testUpdateAll() {
		fail("Not yet implemented");
	}

}
