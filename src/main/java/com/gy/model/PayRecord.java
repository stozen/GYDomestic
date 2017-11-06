package com.gy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-30
 * @introduce 这是交易记录
 */

@Entity
@Table(name="tb_payrecord",catalog="db_gydomestic")
public class PayRecord {

	/**
	 * 声明支付Id
	 */
	private String payRecordId;
	
	/**
	 * 声明支付金额
	 */
	private String payMoney;
	
	/**
	 * 声明支付类型
	 */
	private String payStyle;
	
	/**
	 * 声明支付状态
	 */
	private String payStatus;
	
	/**
	 * 声明交易编号
	 */
	private String outTradeNumber;
	
	/**
	 * 声明订单号
	 */
	private String orderid;
	
	/**
	 * 声明游戏包名
	 */
	private String gamePackage;
	
	/**
	 * 声明游戏渠道
	 */
	private String gameChanel;
	
	/**
	 * 声明支付的电话 
	 */
	private String phone;
	
	/**
	 * 声明支付时间
	 */
	private Date payTime;

	/**
	 * 声明无参构造函数s
	 */
	public PayRecord() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 声明支付记录的id的get方法
	 * @return
	 */
	@Id
	@GenericGenerator(name="generator",strategy="uuid")
	@GeneratedValue(generator="generator")
	@Column(name="payRecordId",length=300,nullable=false,unique=true)
	public String getPayRecordId() {
		return payRecordId;
	}

	/**
	 * 声明支付记录的set方法
	 * @param payRecordId
	 */
	public void setPayRecordId(String payRecordId) {
		this.payRecordId = payRecordId;
	}

	/**
	 * 声明支付记录的金额的get方法
	 * @return
	 */
	@Column(name="payMoney",length=100,nullable=false,insertable=true,updatable=true)
	public String getPayMoney() {
		return payMoney;
	}

	/**
	 * 声明支付记录的金额的set方法
	 * @param payMoney
	 */
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	/**
	 * 声明支付记录的类型的get方法
	 * @return
	 */
	@Column(name="payStyle",length=500,nullable=false,insertable=true,updatable=true)
	public String getPayStyle() {
		return payStyle;
	}

	/**
	 * 声明支付记录的类型的set方法
	 * @param payStyle
	 */
	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	/**
	 * 声明支付记录状态的get方法
	 * @return
	 */
	@Column(name="payStatus",length=100,nullable=false,insertable=true,updatable=true)
	public String getPayStatus() {
		return payStatus;
	}

	/**
	 * 声明支付记录状态的set方法
	 * @param payStatus
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * 声明支付记录状态的get方法
	 * @return
	 */
	@Column(name="payTime",length=6,nullable=false,insertable=true,updatable=true)
	public Date getPayTime() {
		return payTime;
	}

	/**
	 * 声明支付记录的set方法
	 * @param payTime
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	/**
	 * 声明交易订单号的get方法
	 * @return
	 */
	@Column(name="outTradeNumber",length=500,nullable=false,insertable=true,updatable=true)
	public String getOutTradeNumber() {
		return outTradeNumber;
	}

	/**
	 * 声明交易订单号的set方法
	 * @param outTradeNumber
	 */
	public void setOutTradeNumber(String outTradeNumber) {
		this.outTradeNumber = outTradeNumber;
	}

	/**
	 * 声明交易记录订单的id
	 * @return
	 */
	@Column(name="orderid",length=500,nullable=false,insertable=true,updatable=true)
	public String getOrderid() {
		return orderid;
	}

	/**
	 * 声明交易记录订单的id
	 * @param orderid
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	/**
	 * 声明交易记录订单的游戏名的get方法
	 * @return
	 */
	@Column(name="gamePackage",length=500,nullable=false,insertable=true,updatable=true)
	public String getGamePackage() {
		return gamePackage;
	}

	/**
	 * 声明交易记录订单的游戏名的set方法
	 * @param gamePackage
	 */
	public void setGamePackage(String gamePackage) {
		this.gamePackage = gamePackage;
	}

	/**
	 * 声明交易记录的游戏渠道的get方法
	 * @return
	 */
	@Column(name="gameChanel",length=500,nullable=false,insertable=true,updatable=true)
	public String getGameChanel() {
		return gameChanel;
	}

	/**
	 * 声明交易记录的游戏渠道的set方法
	 * @param gameChanel
	 */
	public void setGameChanel(String gameChanel) {
		this.gameChanel = gameChanel;
	}

	/**
	 * 声明支付的手机号码
	 * @return
	 */
	@Column(name="phone",length=80,nullable=false,insertable=true,updatable=true)
	public String getPhone() {
		return phone;
	}

	/**
	 * 声明支付的手机号码
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
