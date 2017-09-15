package com.gy.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户生成的订单类
 */

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_gyorder",catalog="db_gyforeign")
public class Order implements Serializable {
	
	/**
	 * 创建订单id 
	 */
	private int orderid;
	
	/**
	 * 创建订单详情
	 */
	private String orderdetail;
	
	/**
	 * 创建订单时间
	 */
	private Date ordertime;
	
	/**
	 * 创建订单用户 
	 */
	private User user;
	
	/**
	 * 创建对应的关联商品的一对多关系
	 */
	private Set<Goods> goods;
	
	/**
	 * 创建默认构造方法
	 */
	public Order() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建带有参数的构造方法
	 * @param orderid
	 * @param orderdetail
	 * @param ordertime
	 * @param user
	 * @param goods
	 */
	public Order(int orderid, String orderdetail, Date ordertime, User user,
			Set<Goods> goods) {
		super();
		this.orderid = orderid;
		this.orderdetail = orderdetail;
		this.ordertime = ordertime;
		this.user = user;
		this.goods = goods;
	}
	
	/**
	 * 创建主键的生成策略
	 * @return
	 */
	@Id
	@Column(name="orderid",length=12,nullable=false)
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	/**
	 * 创建生成订单详情
	 * @return
	 */
	@Column(name="orderdetail",length=50,nullable=true)
	public String getOrderdetail() {
		return orderdetail;
	}

	public void setOrderdetail(String orderdetail) {
		this.orderdetail = orderdetail;
	}

	/**
	 * 创建生成订单时间
	 * @return
	 */
	@Column(name="ordertime",length=6,nullable=true)
	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	/**
	 * 创建用户的id作为关联外键
	 * @return
	 */
	/*@ManyToOne(cascade=CascadeType.ALL,optional=false)
	@JoinColumn(name="userid",referencedColumnName="userid")*/
	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="orderid")
	public Set<Goods> getGoods() {
		return goods;
	}

	public void setGoods(Set<Goods> goods) {
		this.goods = goods;
	}
	
}
