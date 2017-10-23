package com.gy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-14-37
 * @introduce 这是验证码表
 */

@Entity
@Table(name="tb_gyverificationcode",catalog="db_gydomestic") 
public class VerificationCode {
	
	/**
	 * 这是验证码的主键
	 */
	private int verificationCodeId;
	
	/**
	 * 这是验证码内容
	 */
	private String verificationCode;
	
	/**
	 * 这是手机号
	 */
	private String mobile;
	
	/**
	 * 这是短信验证码创建时间
	 */
	private Date createTime;
	
	/**
	 * 这是短信验证码的有效时间
	 */
	private Date verificationTime;

	public VerificationCode() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param verificationCodeId
	 * @param verificationCode
	 * @param mobile
	 * @param createTime
	 * @param verificationTime
	 */
	public VerificationCode(int verificationCodeId, String verificationCode,
			String mobile, Date createTime, Date verificationTime) {
		super();
		this.verificationCodeId = verificationCodeId;
		this.verificationCode = verificationCode;
		this.mobile = mobile;
		this.createTime = createTime;
		this.verificationTime = verificationTime;
	}

	@Id
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	@Column(name="verificationCodeId",length=12,nullable=false,unique=true)
	public int getVerificationCodeId() {
		return verificationCodeId;
	}

	public void setVerificationCodeId(int verificationCodeId) {
		this.verificationCodeId = verificationCodeId;
	}

	@Column(name="verificationCode",length=12,nullable=false,insertable=true,updatable=true)
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	@Column(name="mobile",length=12,nullable=false,insertable=true,updatable=true)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name="createTime",length=6,nullable=false,insertable=true,updatable=true)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="verificationTime",length=6,nullable=true,insertable=true,updatable=true)
	public Date getVerificationTime() {
		return verificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		this.verificationTime = verificationTime;
	}

	@Override
	public String toString() {
		return "VerificationCode [verificationCodeId=" + verificationCodeId
				+ ", verificationCode=" + verificationCode + ", mobile="
				+ mobile + ", createTime=" + createTime + ", verificationTime="
				+ verificationTime + "]";
	}
	
}
