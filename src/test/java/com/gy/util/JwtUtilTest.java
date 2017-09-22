package com.gy.util;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.config.Constant;
import com.gy.model.User;

public class JwtUtilTest {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Test
	public void testGeneralKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateJWT() {
		fail("Not yet implemented");
	}

	@SuppressWarnings({ "unused", "static-access" })
	@Test
	public void testParseJWT() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MDU5NTc1NjUsInN1YiI6IntcInVzZXJuYW1lXCI6XCJ6aGFvbGkyNDNcIixcInVzZXJpZFwiOjEsXCJwYXNzd29yZFwiOlwiNDU2Nzg5XCJ9IiwiZXhwIjoxNTA1OTYxMTY1fQ.ScCXOvXN_W1zYZweLEjqg5RVvdF92ZcCPFIZcUpYEh0";
		User user = new User();
		
		user.setUserid(1);
		user.setUsername("zhangsan");
		user.setPassword("123456");
		
		String subject = jwtUtil.generalSubject(user);
		jwtUtil.createJWT(Constant.JWT_ID, subject, Constant.JWT_TTL);
		
		/*Claims claim = jwtUtil.parseJWT(token);
		System.err.println(claim);*/
	}

	@Test
	public void testGeneralSubject() {
		fail("Not yet implemented");
	}

}
