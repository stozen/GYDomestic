package com.gy.util;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.config.Constant;
import com.gy.model.Game;
import com.gy.model.User;

public class JwtUtilTest {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Test
	public void testGeneralKey() {
	
		System.err.println(RandomCode.getRandNum(0,999));
	}

	@Test
	public void testCreateJWT() throws Exception {
		User user = new User();
		
		user.setUserid(1);
		user.setUsername("zhangsan");
		user.setPassword("123456");
		
		Game game = new Game();
		game.setGamename("wangzherognyao");
		String subject = jwtUtil.generalSubject(user, game);
		
		long ttlMillis = System.currentTimeMillis();
		String token = jwtUtil.createJWT("1", subject, 2);
		System.out.println(token);
	}

	@SuppressWarnings({ "unused", "static-access" })
	@Test
	public void testParseJWT() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MDU5NTc1NjUsInN1YiI6IntcInVzZXJuYW1lXCI6XCJ6aGFvbGkyNDNcIixcInVzZXJpZFwiOjEsXCJwYXNzd29yZFwiOlwiNDU2Nzg5XCJ9IiwiZXhwIjoxNTA1OTYxMTY1fQ.ScCXOvXN_W1zYZweLEjqg5RVvdF92ZcCPFIZcUpYEh0";
		User user = new User();
		
		user.setUserid(1);
		user.setUsername("zhangsan");
		user.setPassword("123456");
		
		Game game = new Game();
		game.setGamename("wangzherognyao");
		
		String subject = jwtUtil.generalSubject(user,game);
		
		/*Claims claim = jwtUtil.parseJWT(token);
		System.err.println(claim);*/
	}

	@Test
	public void testGeneralSubject() {
		fail("Not yet implemented");
	}

}
