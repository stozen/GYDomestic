package com.gy.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017.9.11
 * @introduce 这是一个用户登录的游戏实体类
 */

@SuppressWarnings("serial")
@Entity
@Table(name="tb_gygame",catalog="db_gyforeign")
public class Game implements Serializable {

	/**
	 * 创建游戏id
	 */
	private int gameid;
	
	/**
	 * 创建游戏名称
	 */
	private String gamename;
	
	/**
	 * 创建游戏的备注
	 */
	private String remark;
	
	/**
	 * 创建游戏包名
	 */
	private String gamepackage;
	
	private User user;
	
	/**
	 * 创建默认构造函数
	 */
	public Game() {
		// TODO Auto-generated constructor stub
	}

	public Game(int gameid, String gamename, String remark, String gamepackage,
			User user) {
		super();
		this.gameid = gameid;
		this.gamename = gamename;
		this.remark = remark;
		this.gamepackage = gamepackage;
		this.user = user;
	}

	@Id
	@Column(name="gameid",length=12,nullable=false)
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	@Column(name="gamename",length=30,nullable=true)
	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	@Column(name="remark",length=50,nullable=true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="gamepackage",length=50,nullable=true)
	public String getGamepackage() {
		return gamepackage;
	}

	public void setGamepackage(String gamepackage) {
		this.gamepackage = gamepackage;
	}

	/*@ManyToOne(cascade=CascadeType.ALL,optional=false,fetch=FetchType.EAGER)
	@JoinColumn(name="userid",referencedColumnName="userid")*/
	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
