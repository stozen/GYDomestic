package com.gy.model;

import java.util.Date;

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
import org.hibernate.type.TrueFalseType;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017.9.13
 * @introduce 这是商品实体类
 */

@Entity
@Table(name="tb_gygoods",catalog="db_gyforeign")
public class Goods {

	/**
	 * 创建商品主键id
	 */
	private int goodsid;
	
	/**
	 * 创建商品数量
	 */
	private int goodsnumber;
	
	/**
	 * 创建商品价格
	 */
	private double goodsprice;
	
	/**
	 * 创建商品生产地址
	 */
	private String goodsaddress;
	
	/**
	 * 创建商品重量
	 */
	private double goodsweight;
	
	/**
	 * 创建商品生产日期
	 */
	private Date goodsmakedate;
	
	/**
	 * 创建商品有效时期
	 */
	private Date goodsvalidate;
	
	/**
	 * 创建商品描述
	 */
	private String goodsremark;
	
	/**
	 * 创建商品和订单之间的管理管理，建立的是一对多关系
	 */
	private Order order;
	
	/**
	 * 创建商品和用户之间的关联关系，建立的是一对多关系
	 */
	private User user;
	
	/**
	 * 创建默认构造函数
	 */
	public Goods() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建有参构造函数
	 * @param goodsid
	 * @param goodsnumber
	 * @param goodsprice
	 * @param goodsaddress
	 * @param goodsweight
	 * @param goodsmakedate
	 * @param goodsvalidate
	 * @param goodsremark
	 * @param order
	 * @param user
	 */
	public Goods(int goodsid, int goodsnumber, double goodsprice,
			String goodsaddress, double goodsweight, Date goodsmakedate,
			Date goodsvalidate, String goodsremark, Order order, User user) {
		super();
		this.goodsid = goodsid;
		this.goodsnumber = goodsnumber;
		this.goodsprice = goodsprice;
		this.goodsaddress = goodsaddress;
		this.goodsweight = goodsweight;
		this.goodsmakedate = goodsmakedate;
		this.goodsvalidate = goodsvalidate;
		this.goodsremark = goodsremark;
		this.order = order;
		this.user = user;
	}

	/**
	 * 生成属性对应的set和get方法
	 */
	
	/**
	 * 创建主键的生成策略
	 * @return
	 */
	@Id
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	@Column(name="goodsid",length=15,nullable=false,unique=true)
	public int getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}

	/**
	 * 创建生成商品的数量
	 * @return
	 */
	@Column(name="goodsnumber",length=18,nullable=true,insertable=true,updatable=true)
	public int getGoodsnumber() {
		return goodsnumber;
	}

	public void setGoodsnumber(int goodsnumber) {
		this.goodsnumber = goodsnumber;
	}

	/**
	 * 创建生成商品的价格
	 * @return
	 */
	/*scale:代表小数点右边有几位，precision:代表有效数字是多少位*/
	@Column(name="goodsprice",precision=16,scale=2,nullable=true,insertable=true,updatable=true)
	public double getGoodsprice() {
		return goodsprice;
	}

	public void setGoodsprice(double goodsprice) {
		this.goodsprice = goodsprice;
	}

	/**
	 * 创建生成商品的生产场地
	 * @return
	 */
	@Column(name="goodsaddress",length=60,nullable=true,insertable=true,updatable=true)
	public String getGoodsaddress() {
		return goodsaddress;
	}

	public void setGoodsaddress(String goodsaddress) {
		this.goodsaddress = goodsaddress;
	}

	/**
	 * 创建生成商品的重量
	 * @return
	 */
	@Column(name="goodsweight",precision=12,scale=2,nullable=true,insertable=true,updatable=true)
	public double getGoodsweight() {
		return goodsweight;
	}

	public void setGoodsweight(double goodsweight) {
		this.goodsweight = goodsweight;
	}

	/**
	 * 创建生成商品的生产日期
	 * @return
	 */
	@Column(name="goodsmakedate",length=6,nullable=true,insertable=true,updatable=true)
	public Date getGoodsmakedate() {
		return goodsmakedate;
	}

	public void setGoodsmakedate(Date goodsmakedate) {
		this.goodsmakedate = goodsmakedate;
	}

	/**
	 * 创建生成商品的有效日期
	 * @return
	 */
	@Column(name="goodsvalidate",length=6,nullable=true,insertable=true,updatable=true)
	public Date getGoodsvalidate() {
		return goodsvalidate;
	}

	public void setGoodsvalidate(Date goodsvalidate) {
		this.goodsvalidate = goodsvalidate;
	}

	/**
	 * 创建生成商品的备注描述
	 * @return
	 */
	@Column(name="goodsremark",length=60,nullable=true,insertable=true,updatable=true)
	public String getGoodsremark() {
		return goodsremark;
	}

	public void setGoodsremark(String goodsremark) {
		this.goodsremark = goodsremark;
	}

	/**
	 * 创建生成商品订单
	 * @return
	 */
	/*@OneToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="orderid")*/
	@ManyToOne
	@JoinColumn(name="orderid")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 创建
	 * @return
	 */
	/*@OneToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="userid")*/
	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Goods [goodsid=" + goodsid + ", goodsnumber=" + goodsnumber
				+ ", goodsprice=" + goodsprice + ", goodsaddress="
				+ goodsaddress + ", goodsweight=" + goodsweight
				+ ", goodsmakedate=" + goodsmakedate + ", goodsvalidate="
				+ goodsvalidate + ", goodsremark=" + goodsremark + ", order="
				+ order + ", user=" + user + "]";
	}
	
}
