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
 * @introduce 这是微信支付的配置类
 */

@Entity
@Table(name="tb_gywxpayconfig",catalog="db_gydomestic")
public class WeixinPayConfig {

	/**
	 * 声明微信支付的配置id
	 */
	private String weixinPayConfigId;
	
	/**
	 * 声明微信支付的应用ID
	 */
	private String APP_ID;
	
	/**
	 * 声明微信支付的应用对应的凭证
	 */
	private String APP_SECRET;
	
	/**
	 * 声明微信支付的API秘钥也就是申请微信时的32位秘钥
	 */
	private String APP_KEY;
	
	/**
	 * 声明微信支付商户号
	 */
	private String MCH_ID;
	
	/**
	 * 商户号对应的密钥(和微信app支付一样，公众号支付)
	 */
	private String PARTNER_KEY;
	
	/**
	 * 声明常用授权类型，固定值
	 */
	private String GRANT_TYPE="client_credential";
	
	/**
	 * 声明异步通知地址
	 */
	private String NOTIFY_URL;
	
	/**
	 * 声明回退地址
	 */
	private String RETURN_URL;
	
	/**
	 * 声明游戏包名
	 */
	private String GAME_PACKAGE;
	
	/**
	 * 声明微信商户id，也就是登录微信公众平台的账号，例如1491013092@1491013092
	 */
	private String PARTNER_ID;
	
	/**
	 * 声明游戏名称
	 */
	private String GAME_NAME;

	/**
	 * 声明无参构造函数
	 */
	public WeixinPayConfig() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 声明有参构造函数
	 * @param weixinPayConfigId
	 * @param aPP_ID
	 * @param aPP_SECRET
	 * @param aPP_KEY
	 * @param mCH_ID
	 * @param pARTNER_KEY
	 * @param gRANT_TYPE
	 * @param nOTIFY_URL
	 * @param rETURN_URL
	 * @param gAME_PACKAGE
	 * @param pARTNER_ID
	 */
	public WeixinPayConfig(String weixinPayConfigId, String aPP_ID,
			String aPP_SECRET, String aPP_KEY, String mCH_ID,
			String pARTNER_KEY, String gRANT_TYPE, String nOTIFY_URL,
			String rETURN_URL, String gAME_PACKAGE, String pARTNER_ID) {
		super();
		this.weixinPayConfigId = weixinPayConfigId;
		APP_ID = aPP_ID;
		APP_SECRET = aPP_SECRET;
		APP_KEY = aPP_KEY;
		MCH_ID = mCH_ID;
		PARTNER_KEY = pARTNER_KEY;
		GRANT_TYPE = gRANT_TYPE;
		NOTIFY_URL = nOTIFY_URL;
		RETURN_URL = rETURN_URL;
		GAME_PACKAGE = gAME_PACKAGE;
		PARTNER_ID = pARTNER_ID;
	}

	/**
	 * 实现微信支付配置的主键生成策略
	 * @return
	 */
	@Id
	@GenericGenerator(name="genarator",strategy="uuid")
	@GeneratedValue(generator="genarator")
	@Column(name="weixinPayConfigId",length=200,nullable=false,unique=true)
	public String getWeixinPayConfigId() {
		return weixinPayConfigId;
	}

	/**
	 * @param weixinPayConfigId
	 */
	public void setWeixinPayConfigId(String weixinPayConfigId) {
		this.weixinPayConfigId = weixinPayConfigId;
	}

	/**
	 * 声明微信支付的应用的id的get方法
	 * @return
	 */
	@Column(name="APP_ID",length=2000,nullable=true,insertable=true,updatable=true)
	public String getAPP_ID() {
		return APP_ID;
	}

	/**
	 * 声明微信支付的应用id的set方法
	 * @param aPP_ID
	 */
	public void setAPP_ID(String aPP_ID) {
		APP_ID = aPP_ID;
	}

	/**
	 * 声明微信支付的应用的凭证的get方法
	 * @return
	 */
	@Column(name="APP_SECRET",length=2000,nullable=true,insertable=true,updatable=true)
	public String getAPP_SECRET() {
		return APP_SECRET;
	}

	/**
	 * 声明微信支付的应用的凭证的set方法
	 * @param aPP_SECRET
	 */
	public void setAPP_SECRET(String aPP_SECRET) {
		APP_SECRET = aPP_SECRET;
	}

	/**
	 * 声明应用对应的密钥的get方法
	 * @return
	 */
	@Column(name="APP_KEY",length=2000,nullable=true,insertable=true,updatable=true)
	public String getAPP_KEY() {
		return APP_KEY;
	}

	/**
	 * 声明应用对应的密钥的set方法
	 * @param aPP_KEY
	 */
	public void setAPP_KEY(String aPP_KEY) {
		APP_KEY = aPP_KEY;
	}

	/**
	 * 声明微信支付商户号的get方法
	 * @return
	 */
	@Column(name="MCH_ID",length=500,nullable=true,insertable=true,updatable=true)
	public String getMCH_ID() {
		return MCH_ID;
	}

	/**
	 * 声明微信支付商户号的set方法
	 * @param mCH_ID
	 */
	public void setMCH_ID(String mCH_ID) {
		MCH_ID = mCH_ID;
	}

	/**
	 * 声明公众号支付时候的商户号对应的密钥的get方法
	 * @return
	 */
	@Column(name="PARTNER_KEY",length=2000,nullable=true,insertable=true,updatable=true)
	public String getPARTNER_KEY() {
		return PARTNER_KEY;
	}

	/**
	 * 声明公众号支付时候的商户号对应的密钥的set方法
	 * @param pARTNER_KEY
	 */
	public void setPARTNER_KEY(String pARTNER_KEY) {
		PARTNER_KEY = pARTNER_KEY;
	}

	/**
	 * 声明授权类型固定值的get方法
	 * @return
	 */
	@Column(name="GRANT_TYPE",length=500,nullable=true,insertable=true,updatable=true)
	public String getGRANT_TYPE() {
		return GRANT_TYPE;
	}

	/**
	 * 声明授权类型固定值的set方法
	 * @param gRANT_TYPE
	 */
	public void setGRANT_TYPE(String gRANT_TYPE) {
		GRANT_TYPE = gRANT_TYPE;
	}

	/**
	 * 声明异步通知的地址的get方法
	 * @return
	 */
	@Column(name="NOTIFY_URL",length=800,nullable=true,insertable=true,updatable=true)
	public String getNOTIFY_URL() {
		return NOTIFY_URL;
	}

	/**
	 * 声明异步通知地址的set方法
	 * @param nOTIFY_URL
	 */
	public void setNOTIFY_URL(String nOTIFY_URL) {
		NOTIFY_URL = nOTIFY_URL;
	}

	/**
	 * 声明回退地址的get方法
	 * @return
	 */
	@Column(name="RETURN_URL",length=800,nullable=true,insertable=true,updatable=true)
	public String getRETURN_URL() {
		return RETURN_URL;
	}

	/**
	 * 声明回退地址的set方法
	 * @param rETURN_URL
	 */
	public void setRETURN_URL(String rETURN_URL) {
		RETURN_URL = rETURN_URL;
	}

	/**
	 * 声明获得包名的get方法
	 * @return
	 */
	@Column(name="GAME_PACKAGE",length=300,nullable=true,insertable=true,updatable=true)
	public String getGAME_PACKAGE() {
		return GAME_PACKAGE;
	}

	/**
	 * 声明设置包名的set方法
	 * @param gAME_PACKAGE
	 */
	public void setGAME_PACKAGE(String gAME_PACKAGE) {
		GAME_PACKAGE = gAME_PACKAGE;
	}

	/**
	 * 声明商户id的get方法
	 * @return
	 */
	public String getPARTNER_ID() {
		return PARTNER_ID;
	}

	/**
	 * 声明商户id的set方法
	 * @param pARTNER_ID
	 */
	public void setPARTNER_ID(String pARTNER_ID) {
		PARTNER_ID = pARTNER_ID;
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
