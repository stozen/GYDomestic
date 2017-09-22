package com.gy.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-9-20
 * @introduce 这是用户登录token时的·
 */

@SuppressWarnings("serial")
@Entity
@Table(name="tb_gytoken",catalog="db_gyforeign")
public class Token implements Serializable {
	
	/**
	 * 生成Token主键
	 */
	private int tokenid;
	
	/**
	 * 生成Token的值
	 */
	private String token;
	
	/**
	 * 创建关联用户对象
	 */
	private User user;
	
	/**
	 * 生成默认无参构造函数 
	 */
	public Token() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建带有参数的构造函数
	 * @param tokenid
	 * @param token
	 * @param user
	 */
	public Token(int tokenid, String token, User user) {
		super();
		this.tokenid = tokenid;
		this.token = token;
		this.user = user;
	}

	/**
	 * 创建主键的生成策略
	 * @return
	 */
	@Id
	@GenericGenerator(name="genarator",strategy="native")
	@GeneratedValue(generator="genarator")
	@Column(name="tokenid",length=8,nullable=false,unique=true)
	public int getTokenid() {
		return tokenid;
	}

	public void setTokenid(int tokenid) {
		this.tokenid = tokenid;
	}

	/**
	 * 创建Token生成策略
	 * @return
	 */
	@Column(name="token",length=200,nullable=true,insertable=true,updatable=true)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@OneToOne
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Token [tokenid=" + tokenid + ", token=" + token + ", user="
				+ user + "]";
	}
	
}
