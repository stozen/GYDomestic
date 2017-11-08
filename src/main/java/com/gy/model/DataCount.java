package com.gy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-11-08
 * @introduce 这是数据统计类
 */

@Entity
@Table(name="tb_gydatacount",catalog="db_gydomestic")
public class DataCount {

	/**
	 * 声明用户统计的主键
	 */
	private String dataCountId;
	
	/**
	 * 按照时间来统计
	 */
	private String time;
	
	/**
	 * 统计出来的数量
	 */
	private String count;
	
	/**
	 * 声明多少用户支付
	 */
	private String userCount;
	
	/**
	 * 声明总共支付多少金额
	 */
	private String payMoney;
	
	/**
	 * 实现默认无参构造函数
	 */
	public DataCount() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 声明主键的get方法
	 * @return
	 */
	@Id
	@GenericGenerator(name="generator",strategy="uuid")
	@GeneratedValue(generator="generator")
	@Column(name="userid",length=300,nullable=false,unique=true)
	public String getDataCountId() {
		return dataCountId;
	}

	/**
	 * 声明主键的set方法
	 * @param dataCountId
	 */
	public void setDataCountId(String dataCountId) {
		this.dataCountId = dataCountId;
	}

	/**
	 * 实现时间获取get方法
	 * @return
	 */
	@Column(name="time",length=180,nullable=false,insertable=true,updatable=true)
	public String getTime() {
		return time;
	}

	/**
	 * 实现时间获取的set方法
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 实现返回多少条数据的get方法
	 * @return
	 */
	@Column(name="count",length=100,nullable=false,insertable=true,updatable=true)
	public String getCount() {
		return count;
	}

	/**
	 * 实现返回多少条数据的set方法
	 * @param count
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * 声明多少用户支付了的get方法
	 * @return
	 */
	@Column(name="userCount",length=100,nullable=false,insertable=true,updatable=true)
	public String getUserCount() {
		return userCount;
	}

	/**
	 * 声明多少用户的支付的set方法
	 * @param userCount
	 */
	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}

	/**
	 * 声明总共支付多少金额的get
	 * @return
	 */
	@Column(name="payMoney",length=100,nullable=false,insertable=true,updatable=true)
	public String getPayMoney() {
		return payMoney;
	}

	/**
	 * 声明公共总支付多少金额的set方法
	 * @param payMoney
	 */
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	
}
