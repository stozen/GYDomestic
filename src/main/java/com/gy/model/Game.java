package com.gy.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017.9.11
 * @introduce 这是一个用户登录的游戏实体类
 */

@SuppressWarnings("serial")
@Entity
@Table(name="tb_gygame",catalog="db_gydomestic")
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
	
	/**
	 * 创建游戏渠道
	 */
	private String gameChannels;
	
	/**
	 * 创建用户对应关系
	 */
	private User user;
	
	/**
	 * 创建游戏和订单之间的一对多关系
	 */
	@JsonIgnoreProperties(value={"transportOrders"}) 
	private Set<Order> order;
	
	/**
	 * 创建默认构造函数
	 */
	public Game() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建带有参数的构造函数
	 * @param gameid
	 * @param gamename
	 * @param remark
	 * @param gamepackage
	 * @param user
	 * @param order
	 */
	public Game(int gameid, String gamename, String remark, String gamepackage,
			User user ,Set<Order> order,String gameChannels) {
		super();
		this.gameid = gameid;
		this.gamename = gamename;
		this.remark = remark;
		this.gamepackage = gamepackage;
		this.user = user;
		this.order = order;
		this.gameChannels = gameChannels;
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
	
	@Column(name="gameChannels",length=100,nullable=true)
	public String getGameChannels() {
		return gameChannels;
	}
	
	public void setGameChannels(String gameChannels) {
		this.gameChannels = gameChannels;
	}

	/*@ManyToOne(cascade=CascadeType.ALL,optional=false,fetch=FetchType.EAGER)
	@JoinColumn(name="userid",referencedColumnName="userid")*/
	/**
	 * 创建用户和游戏之间的关联关系
	 * @return
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@Cascade(value={CascadeType.ALL})
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 创建游戏和订单之间的管理关系
	 * @return
	 */
	@OneToMany
	@Cascade(value={CascadeType.SAVE_UPDATE,  CascadeType.DELETE_ORPHAN,CascadeType.ALL})
	@JoinColumn(name="gameid")
	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Game [gameid=" + gameid + ", gamename=" + gamename
				+ ", remark=" + remark + ", gamepackage=" + gamepackage
				+ ", gameChannels=" + gameChannels + ", user=" + user
				+ ", order=" + order + "]";
	}

}
