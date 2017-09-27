package com.gy.model;

import java.math.BigDecimal;
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
 * @date 2017-9-22
 * @introduce 这是订单详情表实体类
 */
@Entity
@Table(name="tb_gyordergoods",catalog="db_gyforeign")
public class OrderGoods {
	
	/**
	 * 创建订单详情主键
	 */
	private int ogid;
	
	/**
	 * 创建关联的订单
	 */
	private Order order;
	
	/**
	 * 创建关联的商品
	 */
	private Goods goods;
	
	/**
	 * 创建购买商品的数量
	 */
	private int number;
	
	/**
	 * 创建商品的标题
	 */
	private String title;
	
	/**
	 * 创建商品的单价
	 */
	private BigDecimal price;
	
	/**
	 * 创建购买商品的总价
	 */
	private BigDecimal totalprice;
	
	/**
	 * 创建商品的图片路径
	 */
	private String picpath;
	
	/**
	 * 创建无参构造函数
	 */
	public OrderGoods() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建有参构造函数
	 * @param ogid
	 * @param order
	 * @param goods
	 * @param number
	 * @param title
	 * @param price
	 * @param totalprice
	 * @param picpath
	 */
	public OrderGoods(int ogid, Order order, Goods goods, int number,
			String title, BigDecimal price, BigDecimal totalprice, String picpath) {
		super();
		this.ogid = ogid;
		this.order = order;
		this.goods = goods;
		this.number = number;
		this.title = title;
		this.price = price;
		this.totalprice = totalprice;
		this.picpath = picpath;
	}

	/**
	 * 创建主键生成策略
	 * @return
	 */
	@Id
	@GenericGenerator(name="native",strategy="native")
	@GeneratedValue(generator="native")
	@Column(name="ogid",length=12,nullable=false,unique=true)
	public int getOgid() {
		return ogid;
	}

	public void setOgid(int ogid) {
		this.ogid = ogid;
	}

	/**
	 * 创建订单
	 * @return
	 */
	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name="orderid",unique=true)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 创建商品
	 * @return
	 */
	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name="goodsid",unique=true)
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	/**
	 * 创建购买数量
	 * @return
	 */
	@Column(name="number",length=28,nullable=true,insertable=true,updatable=true)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * 创建商品标题
	 * @return
	 */
	@Column(name="title",length=60,nullable=true,insertable=true,updatable=true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 创建商品单价
	 * @return
	 */
	@Column(name="price",precision=18,scale=4,nullable=true,insertable=true,updatable=true)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 创建商品总价
	 * @return
	 */
	@Column(name="totalprice",precision=18,scale=4,nullable=true,insertable=true,updatable=true)
	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	/**
	 * 创建商品图片路径
	 * @return
	 */
	@Column(name="picpath",length=240,nullable=true,insertable=true,updatable=true)
	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	/**
	 * 创建toString方法
	 * @return
	 */
	@Override
	public String toString() {
		return "OG [ogid=" + ogid + ", order=" + order + ", goods="
				+ goods + ", number=" + number + ", title=" + title
				+ ", price=" + price + ", totalprice=" + totalprice
				+ ", picpath=" + picpath + "]";
	}
	
}
