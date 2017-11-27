package com.gy.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 * @introduce 这是用户生成的订单类
 */

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tb_gyorder",catalog="db_gydomestic")
public class Order implements Serializable {
	
	/**
	 * 创建订单id 
	 */
	private int orderid;
	
	/**
	 * 创建实付金额
	 */
	private BigDecimal payment;
	
	/**
	 * 创建支付类型
	 */
	private int paytype;
	
	/**
	 * 支付状态
	 */
	private int paystatus;
	
	/**
	 * 订单创建时间
	 */
	private Date createtime;
	
	/**
	 * 订单更新时间
	 */
	private Date updatetime;
	
	/**
	 * 订单付款时间
	 */
	private Date paytime;
	
	/**
	 * 交易完成时间
	 */
	private Date endtime;
	
	/**
	 * 交易关闭时间
	 */
	private Date closetime;
	
	/**
	 * 交易流水号
	 */
	private String serialnumber;
	
	/**
	 * 创建易捷的订单号
	 */
	private String otherOrderID;
	
	/**
	 * 创建是否已经推送
	 */
	private String isPushed;

	/**
	 * 创建订单用户 
	 */
	private User user;
	
	/**
	 * 创建订单和游戏之间的关联关系 
	 */
	private Game games;
	
	/**
	 * 创建对应的订单详情的一对多关系
	 */
	private Set<OrderGoods> ordergoods;
	
	/**
	 * 创建默认构造方法
	 */
	public Order() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 创建带有参数的构造方法
	 * @param orderid
	 * @param payment
	 * @param paytype
	 * @param status
	 * @param createtime
	 * @param updatetime
	 * @param paytime
	 * @param endtime
	 * @param closetime
	 * @param serialnumber
	 * @param user
	 * @param ordergoods
	 * @param game
	 */
	public Order(int orderid, BigDecimal payment, int paytype, int paystatus,
			Date createtime, Date updatetime, Date paytime, Date endtime,
			Date closetime, String serialnumber, User user,
			Set<OrderGoods> ordergoods, Game games) {
		super();
		this.orderid = orderid;
		this.payment = payment;
		this.paytype = paytype;
		this.paystatus = paystatus;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.paytime = paytime;
		this.endtime = endtime;
		this.closetime = closetime;
		this.serialnumber = serialnumber;
		this.user = user;
		this.ordergoods = ordergoods;
		this.games = games;
	}
	
	/**
	 * 创建主键的生成策略
	 * @return
	 */
	@Id
	@Column(name="orderid",length=50,nullable=false)
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	/**
	 * 创建实付金额
	 */
	@Column(name="payment",precision=18,scale=4,nullable=true,insertable=true,updatable=true)
	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	/**
	 * 创建付款类型
	 */
	@Column(name="paytype",length=8,nullable=true,insertable=true,updatable=true)
	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

	/**
	 * 创建支付状态
	 */
	@Column(name="paystatus",length=12,nullable=true,insertable=true,updatable=true)
	public int getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(int paystatus) {
		this.paystatus = paystatus;
	}

	/**
	 * 创建交易开始时间
	 */
	@Column(name="createtime",length=6,nullable=true,insertable=true,updatable=true)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 创建交易更新时间
	 */
	@Column(name="updatetime",length=6,nullable=true,insertable=true,updatable=true)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * 创建支付时间
	 */
	@Column(name="paytime",length=6,nullable=true,insertable=true,updatable=true)
	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	/**
	 * 创建支付结束时间
	 */
	@Column(name="endtime",length=6,nullable=true,insertable=true,updatable=true)
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/**
	 * 交易关闭时间
	 */
	@Column(name="closetime",length=6,nullable=true,insertable=true,updatable=true)
	public Date getClosetime() {
		return closetime;
	}

	public void setClosetime(Date closetime) {
		this.closetime = closetime;
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

	/**
	 * 创建游戏和订单之间的关联关系
	 * @return
	 */
	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name="gameid")
	public Game getGames() {
		return games;
	}

	public void setGames(Game games) {
		this.games = games;
	}
	
	/**
	 * 创建对账用的流水账号
	 * @return
	 */
	@Column(name="serialnumber",length=30,nullable=true,insertable=true,updatable=true)
	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	
	/*
	@ManyToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinTable(
			name="tb_gyorderitem",
			joinColumns={@JoinColumn(name="orderid")},
			inverseJoinColumns={@JoinColumn(name="goodsid")}
			)
	*/
	@OneToMany(mappedBy="order")
	@Cascade(CascadeType.ALL)
	/*@JoinColumn(name="orderid")*/
	public Set<OrderGoods> getOrdergoods() {
		return ordergoods;
	}


	public void setOrdergoods(Set<OrderGoods> ordergoods) {
		this.ordergoods = ordergoods;
	}

	/**
	 * 创建易捷的订单编号的get方法
	 * @return
	 */
	@Column(name="otherOrderID",length=200,nullable=true,insertable=true,updatable=true)
	public String getOtherOrderID() {
		return otherOrderID;
	}

	/**
	 * 创建易捷的订单编号的set方法
	 * @param otherOrderID
	 */
	public void setOtherOrderID(String otherOrderID) {
		this.otherOrderID = otherOrderID;
	}

	/**
	 * 创建订单是否已经推送的get方法
	 * @return
	 */
	@Column(name="isPushed",length=80,nullable=true,insertable=true,updatable=true)
	public String getIsPushed() {
		return isPushed;
	}

	/**
	 * 创建订单是否推送的set方法
	 * @param isPushed
	 */
	public void setIsPushed(String isPushed) {
		this.isPushed = isPushed;
	}
	
}
