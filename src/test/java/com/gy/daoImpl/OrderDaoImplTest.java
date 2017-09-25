package com.gy.daoImpl;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.model.Order;
import com.gy.model.User;

public class OrderDaoImplTest {

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
		OrderDaoImpl orderdao = (OrderDaoImpl) context.getBean("orderDao");
		
		Order order = new Order();
		
		order.setClosetime(new Date());
		order.setCreatetime(new Date());
		order.setEndtime(new Date());
		order.setPayment(order.getPayment());
		order.setPaytime(new Date());
		order.setPaytype(1);
		order.setSerialnumber("1235891457");
		
		System.err.println("<------------------------方法调用前,先查询用户开始------------------------>");
		orderdao.create(order);
		System.err.println(order);
		System.err.println("<------------------------方法调用前,先查询用户结束------------------------>");
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

	@Test
	public void testFlush() {
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
