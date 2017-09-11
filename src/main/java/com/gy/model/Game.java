package com.gy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017.9.11
 * @introduce 这是一个用户登录的游戏实体类
 */

@Entity
@Table(name="tb_gygame",catalog="db_gygame")
public class Game {
	
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
	
	
}
