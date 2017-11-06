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
 * @date 2017-10-25
 * @introduce 这是阿里支付的配置类
 */

@Entity
@Table(name="tb_gyalipayconfig",catalog="db_gydomestic")
public class AliPayConfig {

	/**
	 * 声明阿里支付的id主键
	 */
	private String AliPayConfigId;
	
	/**
	 * 声明属于哪个应用id
	 */
	private String APP_ID;
	
	/**
	 * 声明支付宝私钥
	 */
	private String APP_PRIVATE_KEY;
	
	/**
	 * 声明支付宝公钥
	 */
	private String ALIPAY_PUBLIC_KEY;
	
	/**
	 * 声明游戏包名
	 */
	private String GAME_PACKAGE;
	
	/**
	 * 声明回调地址
	 */
	private String NOTIFY_URL;
	
	/**
	 * 声明返回地址
	 */
	private String RETURN_URL;
	
	/**
	 * 声明游戏名称
	 */
	private String GAME_NAME;
	
	/**
	 * 声明无参构造函数
	 */
	public AliPayConfig() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 声明有参构造函数
	 * @param aliPayConfigId
	 * @param aPP_ID
	 * @param aPP_PRIVATE_KEY
	 * @param aLIPAY_PUBLIC_KEY
	 * @param gAME_PACKAGE
	 * @param nOTIFY_URL
	 * @param rETURN_URL
	 * @param gAME_NAME
	 */
	public AliPayConfig(String aliPayConfigId, String aPP_ID,
			String aPP_PRIVATE_KEY, String aLIPAY_PUBLIC_KEY,
			String gAME_PACKAGE, String nOTIFY_URL, String rETURN_URL,
			String gAME_NAME) {
		super();
		AliPayConfigId = aliPayConfigId;
		APP_ID = aPP_ID;
		APP_PRIVATE_KEY = aPP_PRIVATE_KEY;
		ALIPAY_PUBLIC_KEY = aLIPAY_PUBLIC_KEY;
		GAME_PACKAGE = gAME_PACKAGE;
		NOTIFY_URL = nOTIFY_URL;
		RETURN_URL = rETURN_URL;
		GAME_NAME = gAME_NAME;
	}

	/**
	 * 声明阿里支付配置的主键id生成策略
	 * @return
	 */
	@Id
	@GenericGenerator(name="genarator",strategy="uuid")
	@GeneratedValue(generator="genarator")
	@Column(name="AliPayConfigId",length=200,nullable=false,unique=true)
	public String getAliPayConfigId() {
		return AliPayConfigId;
	}

	/**
	 * 声明阿里支付配置的主键id的生成策略
	 * @param aliPayConfigId
	 */
	public void setAliPayConfigId(String aliPayConfigId) {
		AliPayConfigId = aliPayConfigId;
	}

	/**
	 * 实现获得appId的get方法
	 * @return
	 */
	@Column(name="APP_ID",length=200,nullable=true,insertable=true,updatable=true)
	public String getAPP_ID() {
		return APP_ID;
	}

	/**
	 * 实现设置appId的set方法
	 * @param aPP_ID
	 */
	public void setAPP_ID(String aPP_ID) {
		APP_ID = aPP_ID;
	}

	/**
	 * 声明获得私钥的get方法
	 * @return
	 */
	@Column(name="APP_PRIVATE_KEY",length=5000,nullable=true,insertable=true,updatable=true)
	public String getAPP_PRIVATE_KEY() {
		return APP_PRIVATE_KEY;
	}

	/**
	 * 声明设置私钥的set方法
	 * @param aPP_PRIVATE_KEY
	 */
	public void setAPP_PRIVATE_KEY(String aPP_PRIVATE_KEY) {
		APP_PRIVATE_KEY = aPP_PRIVATE_KEY;
	}

	/**
	 * 实现获得公钥的get方法
	 * @return
	 */
	@Column(name="ALIPAY_PUBLIC_KEY",length=4000,nullable=true,insertable=true,updatable=true)
	public String getALIPAY_PUBLIC_KEY() {
		return ALIPAY_PUBLIC_KEY;
	}

	/**
	 * 实现设置公钥的set方法
	 * @param aLIPAY_PUBLIC_KEY
	 */
	public void setALIPAY_PUBLIC_KEY(String aLIPAY_PUBLIC_KEY) {
		ALIPAY_PUBLIC_KEY = aLIPAY_PUBLIC_KEY;
	}

	/**
	 * 实现获取游戏包名的get方法
	 * @return
	 */
	@Column(name="GAME_PACKAGE",length=100,nullable=true,insertable=true,updatable=true)
	public String getGAME_PACKAGE() {
		return GAME_PACKAGE;
	}

	/**
	 * 实现设置游戏报名的set方法
	 * @param gAME_PACKAGE
	 */
	public void setGAME_PACKAGE(String gAME_PACKAGE) {
		GAME_PACKAGE = gAME_PACKAGE;
	}

	/**
	 * 实现获得回调地址的get方法
	 * @return
	 */
	@Column(name="NOTIFY_URL",length=400,nullable=true,insertable=true,updatable=true)
	public String getNOTIFY_URL() {
		return NOTIFY_URL;
	}

	/**
	 * 实现设置回调地址的set方法
	 * @param cALL_BACK_URL
	 */
	public void setNOTIFY_URL(String nOTIFY_URL) {
		NOTIFY_URL = nOTIFY_URL;
	}

	/**
	 * 实现返回地址的get方法
	 * @return
	 */
	@Column(name="RETURN_URL",length=400,nullable=true,insertable=true,updatable=true)
	public String getRETURN_URL() {
		return RETURN_URL;
	}

	/**
	 * 实现返回地址的set方法
	 * @param rETURN_URL
	 */
	public void setRETURN_URL(String rETURN_URL) {
		RETURN_URL = rETURN_URL;
	}

	/**
	 * 声明游戏名称的get方法
	 * @return
	 */
	@Column(name="GAME_NAME",length=400,nullable=true,insertable=true,updatable=true)
	public String getGAME_NAME() {
		return GAME_NAME;
	}

	/**
	 * 声明游戏名称的set方法
	 * @param gAME_NAME
	 */
	public void setGAME_NAME(String gAME_NAME) {
		GAME_NAME = gAME_NAME;
	}
	
}
