package com.gy.util;

import static org.junit.Assert.*;
import io.jsonwebtoken.Claims;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

	@Test
	public void testParseJWT() throws Exception {
		Claims claim = jwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MDU5NTY2NDMsInN1YiI6IntcInVzZXJuYW1lXCI6XCJcIixcInVzZXJpZFwiOjAsXCJwYXNzd29yZFwiOlwiMTI1NjMzNTRcIn0iLCJleHAiOjE1MDU5NjAyNDN9.wKF0f-zZQyDMoRq5g22MS_Jo9eyErdDJP14pu9NuQwQ");
		System.err.println(claim.get(claim));
	}

	@Test
	public void testGeneralSubject() {
		fail("Not yet implemented");
	}

}
