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

public class GameDaoImplTest {

	@Test
	public void testQuery() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		GameDaoImpl gamedao = (GameDaoImpl) context.getBean("gameDao");
		
		System.err.println("<------------------------方法调用前,先查询用户开始------------------------>");
		Game game = gamedao.query(1);
		System.err.println("游戏包:"+game.getGamepackage()+"\t"+"游戏名:"+game.getGamename());
		System.err.println("<------------------------方法调用前,先查询用户结束------------------------>");
	}

	@Test
	public void testQueryAll() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		GameDaoImpl gamedao = (GameDaoImpl) context.getBean("gameDao");
		System.err.println("<------------------------方法调用前,先查询用户开始------------------------>");
		List<Game> games = gamedao.queryAll();
		/*for (User user : users) {
			System.err.println(user);
		}*/
		
		Iterator<Game> game = games.iterator();
		
		while (game.hasNext()) {
			Game g = (Game) game.next();
			System.err.println("游戏名:"+g.getGamename()+"\t"+"游戏包名:"+g.getGamepackage());
		}
		System.err.println("<------------------------方法调用前,先查询用户结束------------------------>");
	}

	@Test
	public void testQuerysql() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		GameDaoImpl gamedao = (GameDaoImpl) context.getBean("gameDao");
		String sql = "from Game where gamename='李小龙街霸'";
		/*String selectsql = "from User u,Account acc where u.userid=acc.userid";*/
		System.err.println("<------------------------方法调用查询开始------------------------>");
		Game game = gamedao.querysql(sql);
	
		/*System.err.println(gamedao.querysql(selectsql));*/
		System.err.println(game.getGamename()+"\t"+game.getGamepackage());
		System.err.println("<------------------------方法调用查询结束------------------------>");
	}

	@Test
	public void testQueryBysql() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		
		Game game = new Game();
		
		game.setGamename("斗地主");
		game.setGamepackage("com.tencent.doudizhu");
		game.setRemark("畅玩斗地主游戏");
		
		/*Set<Game> collgame = new HashSet<Game>();*/
		/*Game game = new Game();
		game.setGamename("王者荣耀");
		game.setGamepackage("com.tencent.wangzhe");
		game.setRemark("这是王者荣耀游戏");
		user.getGames().add(game);*/
		/*collgame.add(game);*/
		
		System.err.println("<------------------------方法调用前------------------------>");
		GameDaoImpl gamedao = (GameDaoImpl) context.getBean("gameDao");
		gamedao.save(game);
		System.err.println("<------------------------方法调用后------------------------>");
	}

	@Test
	public void testSaveorupdate() {
		fail("Not yet implemented");
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
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		GameDaoImpl gamedao = (GameDaoImpl) context.getBean("gameDao");
		
		Game game = gamedao.query(1);
		game.setGamename("李小龙街霸");
		
		System.err.println("<------------------------方法调用更新开始------------------------>");
		gamedao.update(game);
		System.err.println("<------------------------方法调用更新结束------------------------>");
	}

	@Test
	public void testUpdateAll() {
		fail("Not yet implemented");
	}

}
