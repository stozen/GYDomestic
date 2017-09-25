package com.gy.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017.9.13
 * @introduce 这是商品实体类
 */

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_gygoods",catalog="db_gyforeign")
public class Goods {

	/**
	 * 创建商品主键id
	 */
	private int goodsid;
	
	/**
	 * 创建商品名称
	 */
	private String goodsname;
	
	/**
	 * 创建商品数量
	 */
	private int goodsnumber;
	
	/**
	 * 创建商品价格
	 */
	private BigDecimal goodsprice;
	
	/**
	 * 创建商品总价
	 */
	private BigDecimal goodstotal;
	
	/**
	 * 创建商品生产地址
	 */
	private String goodsaddress;
	
	/**
	 * 创建商品重量
	 */
	private BigDecimal goodsweight;
	
	/**
	 * 创建商品生产日期
	 */
	private Date goodsmakedate;
	
	/**
	 * 创建商品有效时期
	 */
	private Date goodsvalidate;
	
	/**
	 * 创建商品描述
	 */
	private String goodsremark;
	
	/**
	 * 创建商品图片地址
	 */
	private String goodspicture;
	
	/**
	 * 创建商品和订单详情之间的管理管理，建立的是一对多关系
	 */
	private Set<OrderGoods> ordergoods;
	
	/**
	 * 创建商品和用户之间的关联关系，建立的是一对多关系
	 */
	private User user;
	
	/**
	 * 创建默认构造函数
	 */
	public Goods() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建有参构造函数
	 * @param goodsid
	 * @param goodsname
	 * @param goodsnumber
	 * @param goodsprice
	 * @param goodstotal
	 * @param goodsaddress
	 * @param goodsweight
	 * @param goodsmakedate
	 * @param goodsvalidate
	 * @param goodsremark
	 * @param order
	 * @param user
	 * @param goodspicture
	 */
	public Goods(int goodsid, String goodsname, int goodsnumber,
			BigDecimal goodsprice, BigDecimal goodstotal, String goodsaddress,
			BigDecimal goodsweight, Date goodsmakedate, Date goodsvalidate,
			String goodsremark, String goodspicture,
			Set<OrderGoods> ordergoods, User user) {
		super();
		this.goodsid = goodsid;
		this.goodsname = goodsname;
		this.goodsnumber = goodsnumber;
		this.goodsprice = goodsprice;
		this.goodstotal = goodstotal;
		this.goodsaddress = goodsaddress;
		this.goodsweight = goodsweight;
		this.goodsmakedate = goodsmakedate;
		this.goodsvalidate = goodsvalidate;
		this.goodsremark = goodsremark;
		this.goodspicture = goodspicture;
		this.ordergoods = ordergoods;
		this.user = user;
	}
	/**
	 * 生成属性对应的set和get方法
	 */
	
	/**
	 * 创建主键的生成策略
	 * @return
	 */
	@Id
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	@Column(name="goodsid",length=15,nullable=false,unique=true)
	public int getGoodsid() {
		return goodsid;
	}
	
	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}

	/**
	 * 创建生成商品的数量
	 * @return
	 */
	@Column(name="goodsnumber",length=18,nullable=true,insertable=true,updatable=true)
	public int getGoodsnumber() {
		return goodsnumber;
	}

	public void setGoodsnumber(int goodsnumber) {
		this.goodsnumber = goodsnumber;
	}

	/**
	 * 创建生成商品的价格
	 * @return
	 */
	/*scale:代表小数点右边有几位，precision:代表有效数字是多少位*/
	@Column(name="goodsprice",precision=16,scale=2,nullable=true,insertable=true,updatable=true)
	public BigDecimal getGoodsprice() {
		return goodsprice;
	}

	public void setGoodsprice(BigDecimal goodsprice) {
		this.goodsprice = goodsprice;
	}
	
	/**
	 * 创建生成商品的名称
	 * @return
	 */
	@Column(name="goodsname",length=50,nullable=true,insertable=true,updatable=true)
	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	
	/**
	 * 创建生成商品的总价
	 * @return
	 */
	@Column(name="goodstotal",precision=16,scale=2,nullable=true,insertable=true,updatable=true)
	public BigDecimal getGoodstotal() {
		return goodstotal;
	}

	public void setGoodstotal(BigDecimal goodstotal) {
		this.goodstotal = goodstotal;
	}

	/**
	 * 创建生成商品的生产场地
	 * @return
	 */
	@Column(name="goodsaddress",length=60,nullable=true,insertable=true,updatable=true)
	public String getGoodsaddress() {
		return goodsaddress;
	}

	public void setGoodsaddress(String goodsaddress) {
		this.goodsaddress = goodsaddress;
	}

	/**
	 * 创建生成商品的重量
	 * @return
	 */
	@Column(name="goodsweight",precision=12,scale=2,nullable=true,insertable=true,updatable=true)
	public BigDecimal getGoodsweight() {
		return goodsweight;
	}

	public void setGoodsweight(BigDecimal goodsweight) {
		this.goodsweight = goodsweight;
	}

	/**
	 * 创建生成商品的生产日期
	 * @return
	 */
	@Column(name="goodsmakedate",length=6,nullable=true,insertable=true,updatable=true)
	public Date getGoodsmakedate() {
		return goodsmakedate;
	}

	public void setGoodsmakedate(Date goodsmakedate) {
		this.goodsmakedate = goodsmakedate;
	}

	/**
	 * 创建生成商品的有效日期
	 * @return
	 */
	@Column(name="goodsvalidate",length=6,nullable=true,insertable=true,updatable=true)
	public Date getGoodsvalidate() {
		return goodsvalidate;
	}

	public void setGoodsvalidate(Date goodsvalidate) {
		this.goodsvalidate = goodsvalidate;
	}

	/**
	 * 创建生成商品的备注描述
	 * @return
	 */
	@Column(name="goodsremark",length=60,nullable=true,insertable=true,updatable=true)
	public String getGoodsremark() {
		return goodsremark;
	}

	public void setGoodsremark(String goodsremark) {
		this.goodsremark = goodsremark;
	}

	/**
	 * 创建商品图片
	 * @return
	 */
	@Column(name="goodspicture",length=200,nullable=true,insertable=true,updatable=true)
	public String getGoodspicture() {
		return goodspicture;
	}

	public void setGoodspicture(String goodspicture) {
		this.goodspicture = goodspicture;
	}

	/**
	 * 创建生成商品订单
	 * @return
	 */
	/*@OneToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="orderid")*/
	
	
	/*@ManyToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinTable(
			name="tb_gyorderitem",
			joinColumns={@JoinColumn(name="goodsid")
					    ,@JoinColumn(name="goodsnumber")
						,@JoinColumn(name="goodsname")
						,@JoinColumn(name="goodsprice")
						,@JoinColumn(name="goodstotal")
						,@JoinColumn(name="goodspicture")
			},
			inverseJoinColumns={@JoinColumn(name="orderid")}
			)*/
	@OneToMany(mappedBy="goods")
	@Cascade(CascadeType.ALL)
	/*@JoinColumn(name="goodsid")*/
	public Set<OrderGoods> getOrdergoods() {
		return ordergoods;
	}

	public void setOrdergoods(Set<OrderGoods> ordergoods) {
		this.ordergoods = ordergoods;
	}
	
	/**
	 * 创建
	 * @return
	 */
	/*@OneToMany
	@Cascade(value={CascadeType.SAVE_UPDATE})
	@JoinColumn(name="userid")*/
	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Goods [goodsid=" + goodsid + ", goodsname=" + goodsname
				+ ", goodsnumber=" + goodsnumber + ", goodsprice=" + goodsprice
				+ ", goodstotal=" + goodstotal + ", goodsaddress="
				+ goodsaddress + ", goodsweight=" + goodsweight
				+ ", goodsmakedate=" + goodsmakedate + ", goodsvalidate="
				+ goodsvalidate + ", goodsremark=" + goodsremark
				+ ", goodspicture=" + goodspicture + ", ordergoods="
				+ ordergoods + ", user=" + user + "]";
	}

}
