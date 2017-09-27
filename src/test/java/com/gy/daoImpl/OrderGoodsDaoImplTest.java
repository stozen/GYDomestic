package com.gy.daoImpl;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.model.OrderGoods;

public class OrderGoodsDaoImplTest {

	@Test
	public void testGetSessionFactory() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSessionFactory() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreate() {
		ApplicationContext context= new ClassPathXmlApplicationContext("classpath:/config/spring-hibernate.xml");
		OrderGoodsDaoImpl orderGoodsdao = (OrderGoodsDaoImpl) context.getBean("orderGoodsDao");
		
		OrderGoods og = new OrderGoods();
		og.setNumber(12);
		og.setPicpath("http://www.baidu.com");
		BigDecimal price = new BigDecimal(88.58);
		og.setPrice(price);
		og.setTitle("IPhone 7Plus手机");
		
		orderGoodsdao.create(og);
		System.err.println(orderGoodsdao);
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
	public void testModify() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifyAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryBySql() {
		fail("Not yet implemented");
	}

	@Test
	public void testCancel() {
		fail("Not yet implemented");
	}

}
