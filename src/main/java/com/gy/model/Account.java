package com.gy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * @author Chencongye
 * @date 2013.9.14
 * @version 0.0.1
 * @introduce 这是用户登录的其他方式，这是用户账号的实体类
 */

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_gyaccount",catalog="db_gyforeign")
public class Account {
	
	/**
	 * 创建账户的ID 
	 */
	private int accountid;
	
	/**
	 * 创建用户账户名称
	 */
	private String accountname;
	
	/**
	 * 创建用户登录的类型
	 */
	private String accounttype;
	
	/**
	 * 创建账户的密码
	 */
	private String accoutpasswd;
	
	/**
	 * 生成默认构造方法
	 */
	public Account() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建带有参数的构造方法
	 * @param accountid
	 * @param accountname
	 * @param accounttype
	 * @param accoutpasswd
	 */
	public Account(int accountid, String accountname, String accounttype,
			String accoutpasswd) {
		super();
		this.accountid = accountid;
		this.accountname = accountname;
		this.accounttype = accounttype;
		this.accoutpasswd = accoutpasswd;
	}

	/**
	 * 生成对应的set和get方法,对应的主键生成策略
	 * @return
	 */
	@Id
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	@Column(name="accountid",length=12,nullable=false,unique=true)
	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	/**
	 * 生成账户名称,以及对应的set和get方法
	 * @return
	 */
	@Column(name="accountname",length=40,nullable=false,insertable=true,updatable=true)
	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	/**
	 * 创建对应账户类型以及对应的set和get方法
	 * @return
	 */
	@Column(name="accounttype",length=8,nullable=true,insertable=true,updatable=true)
	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	/**
	 * 创建账户的密码以及对应的set和get方法
	 * @return
	 */
	@Column(name="accoutpasswd",length=18,nullable=true,insertable=true,updatable=true)
	public String getAccoutpasswd() {
		return accoutpasswd;
	}

	public void setAccoutpasswd(String accoutpasswd) {
		this.accoutpasswd = accoutpasswd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 生成toString方法
	 */
	@Override
	public String toString() {
		return "Account [accountid=" + accountid + ", accountname="
				+ accountname + ", accounttype=" + accounttype
				+ ", accoutpasswd=" + accoutpasswd + "]";
	}
}
