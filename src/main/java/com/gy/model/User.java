package com.gy.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是用户实体类
 * @date 2017.9.11
 */

@SuppressWarnings("serial")
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
	 * 创建用户的手机账号
	 */
	private String mobile;
	
	/**
	 * 创建用户的邮箱
	 */
	private String email;
	
	/**
	 * 创建用户登录方式
	 */
	private String type;
	
	/**
	 * 创建引用游戏
	 */
	private Set<Game> games = new HashSet<Game>();
	
	/**
	 * 创建引用的订单
	 */
	private Set<Order> orders = new HashSet<Order>();
	
	/**
	 * 创建引用的账户
	 */
	private Set<Account> accounts = new HashSet<Account>();
	
	/**
	 * 创建用户Token对象
	 */
	private Token token;

	/**
	 * 创建默认构造函数
	 */
	public User() {
		
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
	@Column(name="logintime",length=6,nullable=true,insertable=true,updatable=true)
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
	@Column(name="registtime",length=6,nullable=false,insertable=true,updatable=true)
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
	@Column(name="modifytime",length=6,nullable=true,insertable=true,updatable=true)
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	
	/**
	 * 创建用户的移动手机账号set和get方法
	 * @return
	 */
	@Column(name="mobile",length=12,nullable=false)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 创建用户的邮箱账号set和get方法
	 * @return
	 */
	@Column(name="email",length=23,nullable=false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 创建生成的关联的游戏
	 * @return
	 */
	/*@Column(name="game",length=20,nullable=true)
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="userid")*/
	@OneToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="userid")
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
	/*@Column(name="order",length=30,nullable=false)
	@OneToMany(mappedBy="user",cascade=javax.persistence.CascadeType.ALL)*/
	@OneToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="userid")
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	@Column(name="type",length=8,nullable=false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="userid")
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	
	/**
	 * 生成用户Token对象
	 * @return
	 */
	@OneToOne
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="userid")
	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
	/**
	 * 创建toString方法
	 */
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username
				+ ", password=" + password + ", logintime=" + logintime
				+ ", registtime=" + registtime + ", modifytime=" + modifytime
				+ ", mobile=" + mobile + ", games=" + games + ", orders="
				+ orders + "]";
	}
	
}
