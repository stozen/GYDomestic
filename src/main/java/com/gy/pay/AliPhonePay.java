package com.gy.pay;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.gy.model.OrderGoods;
import com.gy.services.OrderGoodsService;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value="/alipay")
public class AliPhonePay {

	/**
	 * 创建返回给客户端的状态信息
	 */
	private String status;
	
	/**
	 * 创建返回给客户端的信息提示
	 */
	private String message;
	
	/**
	 * 创建订单详情编号
	 */
	@Autowired
	private OrderGoodsService orderGoodsService;
	
	/**
	 * 获取订单详情服务
	 * @return
	 */
	public OrderGoodsService getOrderGoodsService() {
		return orderGoodsService;
	}

	/**
	 * 设置订单详情服务
	 * @param orderGoodsService
	 */
	public void setOrderGoodsService(OrderGoodsService orderGoodsService) {
		this.orderGoodsService = orderGoodsService;
	}

	/**
	 * 声明Alipay的支付接口
	 * @param map
	 * @param token
	 * @return
	 * @throws IOException 
	 */
	
	@RequestMapping(value="/phonepay",method=RequestMethod.POST)
	public void payOrder(@RequestBody Map map,@RequestHeader String token,HttpServletRequest httpRequest,HttpServletResponse httpResponse) throws ServletException, IOException {
		
		/*商户网站唯一订单号*/
		String out_trade_no = ((String) map.get("orderid")).trim();
		/*查询订单详情里面是否有这个订单*/
		OrderGoods orderGoods = orderGoodsService.query(Integer.parseInt(out_trade_no));
		if(orderGoods == null)
		{
			map.put("status", "0404");
			map.put("message", "不存在这个订单，请重新生成订单号！");
			map.put("paydata", "");
		}
		else
		{
			/*商品的标题/交易标题/订单标题/订单关键字等
			String subject = ((String)orderGoods.getTitle()).trim();
			订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
			String total_amount = (orderGoods.getPrice().toString()).trim();
			销售产品码，商家和支付宝签约的产品码。该产品请填写固定值：QUICK_WAP_WAY
			String product_code = "QUICK_WAP_WAY";
			System.err.println("金额:"+total_amount);*/
			/*创建支付宝支付的公共参数*/
			final String APP_ID = "2016080301699003";
			final String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJzuFpK2ikT/cLzBMQS2G0VBDJM0vjHser7pV+AG5d2kfkSSzRgDMKyTiq871M8jQmEfVlZJNtgXcKdyV5bUhoCNQL4Tq3Jp8Ndo8oAQ/3NvSux794kkq6L2UhHwckJ5yoTb4bNzQYwkGXEmAal22+bZwsc6IVwNzk2TJ0H6VdpVAgMBAAECgYAoA9G/sUoKk/PkPYLJR8ImY5LYSl+hDUKzQX7FwhyE6rfDtocTc2TK7Ig1bJU0CDKZ30q9j8erTDbOi6pn7GMrKAzpF1nSMTjJgio03Kat9784YfI7tcT0YJjaGIsjNCeUiEhy/Hd1LxpExB1Dcet9Siy3USe4qXvzY7lXlkf9AQJBANCY+cWllFUJPwxg3kx77nrqlRBCodKuizcqZBJsZc3k/IDB8LX9UU3sljeNHJM9Ee/AU/fUzDLww4E/BsP0X5UCQQDAl2Nr/RylEw9cveOJDSstYFrVmWU+lZQN0Nq3StFcg/wEtV1H/ajOEHxn4/lYvLN2RcVTgIMm8lwxm1bWu9/BAkAUCU2cjX4E+QFkV/2iTRkoF1ZAHJZcnUVkBB9eoajZsRAL8hUD9hQULxByv4wqHGiXpdqq6HbAwd2VkY89zUBNAkEAl/wgms0RuPfUrMSx9qssws+Cf4RhkMUsJMcIg5OIqzEBRpn19mUovQ3nj3kqgqvQGGsxMRd+6NJkjUVgf2+eQQJAT1uJnT3N9h1O/FAhXrcg1f0tBswtCyvtcZNh3EStARDj2NluJwJiMMbgRZe12jfvfN6lmq0sUvwOT298H8W6qQ==";
			final String CHARSET = "utf-8";
			
			final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
			final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";
			
			/*创建支付宝的扩展参数*/
			/*商品的标题/交易标题/订单标题/订单关键字等*/
			String subject = orderGoods.getTitle();
			/*转换商品单价小数点为两位*/
			String price = orderGoods.getPrice().toString();
			/*订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]*/
			String total_amount = String.valueOf(Float.parseFloat(price)).trim();
			/*销售产品码，商家和支付宝签约的产品码。该产品请填写固定值：QUICK_WAP_WAY*/
			String product_code = "QUICK_WAP_WAY";
			
			//实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA");
			AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
			alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
			alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
			/*String request_str = "{" +
					"    \"out_trade_no\":\""+out_trade_no+"\"," +
			        "    \"product_code\":\""+product_code+"\"," +
			        "    \"total_amount\":\""+total_amount+"\"," +
			        "    \"subject\":\""+subject+"\"," +
			        "  }";
			System.err.println("提交参数:"+request_str);*/
			alipayRequest.setBizContent("{" +
					"    \"out_trade_no\":\""+out_trade_no+"\"," +
			        "    \"product_code\":\""+product_code+"\"," +
			        "    \"total_amount\":\""+total_amount+"\"," +
			        "    \"subject\":\""+subject+"\"," +
			        "  }");//填充业务参数
			String form="";
			try {
			form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
			} catch (AlipayApiException e) {
			e.printStackTrace();
			}
			if(form.contains("display:none"))
			{
				
			}
			httpResponse.setContentType("text/html;charset=" + CHARSET);
			httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
		}
	}
}
