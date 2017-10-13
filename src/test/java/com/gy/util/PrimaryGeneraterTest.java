package com.gy.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrimaryGeneraterTest {

	@Test
	public void testGetInstance() {
		fail("Not yet implemented");
	}

	@Test
	public void testGeneraterNextNumber() {
		PrimaryGenerater generater = new  PrimaryGenerater();
		
		String no = "1234567867786456";
	    /*for(int i=0;i<100;i++) {
	      no = generater.generaterNextNumber(no);
	      System.out.println(no);
	    }*/
		System.err.println(generater.generaterNextNumber());
	}

}
