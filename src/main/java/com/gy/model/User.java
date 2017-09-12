package com.gy.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gy.model.*;


/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户实体类
 * @date 2017.9.11
 */

@Entity
@Table(name="tb_gyuser",catalog="db_gyforeign")
public class User implements Serializable {
	
	/**
	 * 创建ID字段
	 */
	private int userid;
	
	/**
	 * 创建用户名字段
	 */
	/*@JsonProperty(value="username")*/
	private String username;
	
	/**
	 * 创建用户密码字段
	 */
	private String password;
	
	/**
	 * 创建用户登录时间
	 */
	private Date logintime;
	
	/**
	 * 创建用户注册时间
	 */
	private Date registtime;
	
	/**
	 * 创建用户修改时间
	 */
	private Date modifytime;
	
	/**
	 * 创建引用游戏
	 */
	private Set<Game> games = new HashSet<Game>();
	
	/**
	 * 创建引用的订单
	 */
	private Set<Order> orders = new HashSet<Order>();
	
	/**
	 * 创建默认构造函数
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建带有参数的构造函数
	 */
	public User(int userid, String username, String password) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
	}

	/**
	 * 创建对应字段的set和get方法
	 * 创建主键生成策略
	 */
	@Id
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	@Column(name="userid",length=12,nullable=false,unique=true)
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * 创建用户登录的用户名
	 * @return
	 */
	@Column(name="username",length=30,nullable=false,insertable=true,updatable=true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 创建用户登录密码
	 * @return
	 */
	@Column(name="passwd",length=40,nullable=false,insertable=true,updatable=true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 创建用户登录时间
	 * @return
	 */
	@Column(name="logintime",length=6,nullable=false)
	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	/**
	 * 创建用户注册时间
	 * @return
	 */
	@Column(name="registtime",length=6,nullable=false)
	public Date getRegisttime() {
		return registtime;
	}

	public void setRegisttime(Date registtime) {
		this.registtime = registtime;
	}

	/**
	 * 创建用户修改时间
	 * @return
	 */
	@Column(name="modifytime",length=6,nullable=false)
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	/**
	 * 创建生成的关联的游戏
	 * @return
	 */
	@Column(name="game",length=20,nullable=false)
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}
	
	/**
	 * 创建生成关联的订单
	 * @return
	 */
	@Column(name="order",length=30,nullable=false)
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 创建toString方法
	 */
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username
				+ ", password=" + password + ", logintime=" + logintime
				+ ", registtime=" + registtime + ", modifytime=" + modifytime
				+ ", games=" + games + "]";
	}
	
}
