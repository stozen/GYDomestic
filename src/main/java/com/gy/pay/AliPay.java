package com.gy.pay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.gy.services.OrderGoodsService;
import com.gy.services.OrderService;
import com.paypal.api.payments.CreditCard;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

/**
 * @author Chencongye
 * @date 2017.9.11
 */
@Controller
@RequestMapping(value="/alipay")
public class AliPay {
	
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
	 */
	
	@RequestMapping(value="/pay",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkOrder(@RequestBody Map map,@RequestHeader String token) {
		// TODO Auto-generated method stub
		/*商户网站唯一订单号*/
		String out_trade_no = ((String) map.get("orderid")).trim();
		
		
		/*商品的标题/交易标题/订单标题/订单关键字等*/
		String subject = ((String) map.get("subject")).trim();
		
		/*从数据库里面调取付款金额和商品信息*/
		
		/*订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]*/
		String total_amount = ((String) map.get("total_amount")).trim();
		/*销售产品码，商家和支付宝签约的产品码。该产品请填写固定值：QUICK_WAP_WAY*/
		String product_code = "QUICK_WAP_WAY";

		/*创建支付宝支付的公共参数*/
		final String APP_ID = "2016080301699003";
		final String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJzuFpK2ikT/cLzBMQS2G0VBDJM0vjHser7pV+AG5d2kfkSSzRgDMKyTiq871M8jQmEfVlZJNtgXcKdyV5bUhoCNQL4Tq3Jp8Ndo8oAQ/3NvSux794kkq6L2UhHwckJ5yoTb4bNzQYwkGXEmAal22+bZwsc6IVwNzk2TJ0H6VdpVAgMBAAECgYAoA9G/sUoKk/PkPYLJR8ImY5LYSl+hDUKzQX7FwhyE6rfDtocTc2TK7Ig1bJU0CDKZ30q9j8erTDbOi6pn7GMrKAzpF1nSMTjJgio03Kat9784YfI7tcT0YJjaGIsjNCeUiEhy/Hd1LxpExB1Dcet9Siy3USe4qXvzY7lXlkf9AQJBANCY+cWllFUJPwxg3kx77nrqlRBCodKuizcqZBJsZc3k/IDB8LX9UU3sljeNHJM9Ee/AU/fUzDLww4E/BsP0X5UCQQDAl2Nr/RylEw9cveOJDSstYFrVmWU+lZQN0Nq3StFcg/wEtV1H/ajOEHxn4/lYvLN2RcVTgIMm8lwxm1bWu9/BAkAUCU2cjX4E+QFkV/2iTRkoF1ZAHJZcnUVkBB9eoajZsRAL8hUD9hQULxByv4wqHGiXpdqq6HbAwd2VkY89zUBNAkEAl/wgms0RuPfUrMSx9qssws+Cf4RhkMUsJMcIg5OIqzEBRpn19mUovQ3nj3kqgqvQGGsxMRd+6NJkjUVgf2+eQQJAT1uJnT3N9h1O/FAhXrcg1f0tBswtCyvtcZNh3EStARDj2NluJwJiMMbgRZe12jfvfN6lmq0sUvwOT298H8W6qQ==";
		final String CHARSET = "utf-8";
		
		final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
		final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";
		
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA");
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject("App支付测试Java");
		model.setOutTradeNo(out_trade_no);
		model.setTimeoutExpress("30m");
		model.setTotalAmount(total_amount);
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl("商户外网可以访问的异步地址");
		try {
		        //这里和普通的接口调用不同，使用的是sdkExecute
		        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
		        if(response.getBody()!=null)
		        {
		        	status = "0200";
		        	message = "支付成功！";
		        	map.put("status", status);
		        	map.put("paydata", response.getBody());
		        	map.put("message", message);
		        }
		        else
		        {
		        	status = "0404";
		        	message = "支付失败！";
		        	map.put("status", status);
		        	map.put("paydata", null);
		        	map.put("message", message);
		        }
		    } catch (AlipayApiException e) {
		        e.printStackTrace();
		}
		
		/*JAVA服务端验证异步通知信息参数示例*/
		
		/*//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  	}
		    //乱码解决，这段代码在出现乱码时使用。
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET,"RSA");
		map.put("flag", flag);*/
		
		map.remove("out_trade_no");
		map.remove("total_amount");
		map.remove("subject");
		
		return map;
	}
	
}
