package com.gy.pay;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.google.gson.annotations.Since;
import com.gy.model.AliPayConfig;
import com.gy.model.Game;
import com.gy.model.Order;
import com.gy.model.OrderGoods;
import com.gy.model.PayRecord;
import com.gy.model.User;
import com.gy.services.AliPayConfigService;
import com.gy.services.OrderGoodsService;
import com.gy.services.PayRecordService;

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
	
	@Autowired
	private PayRecordService payRecordService;
	
	@Autowired
	private AliPayConfigService aliPayConfigService;
	
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
	 * 声明阿里支付的服务的依赖注入的get方法
	 * @return
	 */
	public AliPayConfigService getAliPayConfigService() {
		return aliPayConfigService;
	}

	/**
	 * 声明阿里支付的服务的依赖注入的set方法
	 * @param aliPayConfigService
	 */
	public void setAliPayConfigService(AliPayConfigService aliPayConfigService) {
		this.aliPayConfigService = aliPayConfigService;
	}

	/**
	 * 声明支付记录的依赖注入的get方法
	 * @return
	 */
	public PayRecordService getPayRecordService() {
		return payRecordService;
	}

	/**
	 * 声明支付记录的依赖注入的set方法
	 * @param payRecordService
	 */
	public void setPayRecordService(PayRecordService payRecordService) {
		this.payRecordService = payRecordService;
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
		String number = ((String) map.get("orderid")).trim();
		/*查询订单详情里面是否有这个订单*/
		OrderGoods orderGoods = orderGoodsService.query(Integer.parseInt(number));
		if(orderGoods == null)
		{
			map.put("status", "0404");
			map.put("message", "不存在这个订单，请重新生成订单号！");
			map.put("paydata", "");
		}
		else
		{
			Order order = orderGoods.getOrder();
            Game game = order.getGames();
            String gamePackage = game.getGamepackage();
            /*String gameChannels = game.getGameChannels();*/
            String goodname = orderGoods.getTitle();
            orderGoods.getTotalprice();
            AliPayConfig aliPayConfig = aliPayConfigService.queryGamepackage(gamePackage);
	        String otherOrderID = order.getOtherOrderID();
			if(aliPayConfig==null)
			{
				status = "0403";
				message = "数据库中没有添加支付支付的配置信息!";
				/*final String APP_ID = aliPayConfig.getAPP_ID();
				final String APP_PRIVATE_KEY = aliPayConfig.getAPP_PRIVATE_KEY();
				final String ALIPAY_PUBLIC_KEY = aliPayConfig.getALIPAY_PUBLIC_KEY();*/
				String returnUrl = "http://23sdk.23h5.cn/h5SDK/validation.html";
				String callBackUrl = "http://23sdk.23h5.cn/h5SDK/validation.html";
				 
				/*创建支付宝支付的公共参数*/
				final String APP_ID = "2016080301699003";
				final String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJzuFpK2ikT/cLzBMQS2G0VBDJM0vjHser7pV+AG5d2kfkSSzRgDMKyTiq871M8jQmEfVlZJNtgXcKdyV5bUhoCNQL4Tq3Jp8Ndo8oAQ/3NvSux794kkq6L2UhHwckJ5yoTb4bNzQYwkGXEmAal22+bZwsc6IVwNzk2TJ0H6VdpVAgMBAAECgYAoA9G/sUoKk/PkPYLJR8ImY5LYSl+hDUKzQX7FwhyE6rfDtocTc2TK7Ig1bJU0CDKZ30q9j8erTDbOi6pn7GMrKAzpF1nSMTjJgio03Kat9784YfI7tcT0YJjaGIsjNCeUiEhy/Hd1LxpExB1Dcet9Siy3USe4qXvzY7lXlkf9AQJBANCY+cWllFUJPwxg3kx77nrqlRBCodKuizcqZBJsZc3k/IDB8LX9UU3sljeNHJM9Ee/AU/fUzDLww4E/BsP0X5UCQQDAl2Nr/RylEw9cveOJDSstYFrVmWU+lZQN0Nq3StFcg/wEtV1H/ajOEHxn4/lYvLN2RcVTgIMm8lwxm1bWu9/BAkAUCU2cjX4E+QFkV/2iTRkoF1ZAHJZcnUVkBB9eoajZsRAL8hUD9hQULxByv4wqHGiXpdqq6HbAwd2VkY89zUBNAkEAl/wgms0RuPfUrMSx9qssws+Cf4RhkMUsJMcIg5OIqzEBRpn19mUovQ3nj3kqgqvQGGsxMRd+6NJkjUVgf2+eQQJAT1uJnT3N9h1O/FAhXrcg1f0tBswtCyvtcZNh3EStARDj2NluJwJiMMbgRZe12jfvfN6lmq0sUvwOT298H8W6qQ==";
				final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
				final String CHARSET = "utf-8";
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		        Date now = new Date();
		        String time = sdf.format(now);
		        System.out.println("时间:"+time);
		        int randomNum = (int)((Math.random()*9+1)*100);
		        String out_trade_no = otherOrderID;
				
				//实例化客户端
				AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA");
				AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
				alipayRequest.setReturnUrl(returnUrl);
				alipayRequest.setNotifyUrl(callBackUrl);//在公共参数中设置回跳和通知地址
				alipayRequest.setBizContent("{" +
						"    \"out_trade_no\":\""+out_trade_no+"\"," +
				        "    \"product_code\":\""+product_code+"\"," +
				        "    \"body\":\""+subject+otherOrderID+"\"," +
				        "    \"total_amount\":\""+total_amount+"\"," +
				        "    \"subject\":\""+subject+"\"" +
				        "  }");//填充业务参数
				String form="";
				try {
				form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
				
	            PayRecord payRecord = new PayRecord();
	            /*if(gameChannels.equals(""))
	            {
	            	payRecord.setGameChanel("");
	            }
	            else
	            {
	            	payRecord.setGameChanel(gameChannels);
	            }*/
	            /*payRecord.setGameChanel(gameChannels);*/
	            payRecord.setGameChanel("支付宝手机APP网站支付");
	            payRecord.setGamePackage(gamePackage);
	            payRecord.setOutTradeNumber(out_trade_no);
	            payRecord.setOrderid(number);
	            payRecord.setPayMoney(total_amount);
	            payRecord.setPayStyle("支付宝手机APP网站支付");
	            payRecord.setPayStatus("0");
	            /*SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMddHHmmss");
	            System.err.println("时间日期:"+timestamp);*/
	            payRecord.setPayTime(new Date());
	            User user = order.getUser();
	            payRecord.setPhone(user.getUsername());
	            payRecordService.add(payRecord);
				} catch (AlipayApiException e) {
					e.printStackTrace();
				}
				httpResponse.setContentType("text/html;charset=" + CHARSET);
				httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
				httpResponse.getWriter().flush();
				httpResponse.getWriter().close();
			}
			else
			{
				final String APP_ID = aliPayConfig.getAPP_ID();
				final String APP_PRIVATE_KEY = aliPayConfig.getAPP_PRIVATE_KEY();
				final String ALIPAY_PUBLIC_KEY = aliPayConfig.getALIPAY_PUBLIC_KEY();
				String returnUrl = aliPayConfig.getRETURN_URL();
				String callBackUrl = aliPayConfig.getNOTIFY_URL();
				 
				/*创建支付宝支付的公共参数*/
				/*final String APP_ID = "2016080301699003";
				final String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJzuFpK2ikT/cLzBMQS2G0VBDJM0vjHser7pV+AG5d2kfkSSzRgDMKyTiq871M8jQmEfVlZJNtgXcKdyV5bUhoCNQL4Tq3Jp8Ndo8oAQ/3NvSux794kkq6L2UhHwckJ5yoTb4bNzQYwkGXEmAal22+bZwsc6IVwNzk2TJ0H6VdpVAgMBAAECgYAoA9G/sUoKk/PkPYLJR8ImY5LYSl+hDUKzQX7FwhyE6rfDtocTc2TK7Ig1bJU0CDKZ30q9j8erTDbOi6pn7GMrKAzpF1nSMTjJgio03Kat9784YfI7tcT0YJjaGIsjNCeUiEhy/Hd1LxpExB1Dcet9Siy3USe4qXvzY7lXlkf9AQJBANCY+cWllFUJPwxg3kx77nrqlRBCodKuizcqZBJsZc3k/IDB8LX9UU3sljeNHJM9Ee/AU/fUzDLww4E/BsP0X5UCQQDAl2Nr/RylEw9cveOJDSstYFrVmWU+lZQN0Nq3StFcg/wEtV1H/ajOEHxn4/lYvLN2RcVTgIMm8lwxm1bWu9/BAkAUCU2cjX4E+QFkV/2iTRkoF1ZAHJZcnUVkBB9eoajZsRAL8hUD9hQULxByv4wqHGiXpdqq6HbAwd2VkY89zUBNAkEAl/wgms0RuPfUrMSx9qssws+Cf4RhkMUsJMcIg5OIqzEBRpn19mUovQ3nj3kqgqvQGGsxMRd+6NJkjUVgf2+eQQJAT1uJnT3N9h1O/FAhXrcg1f0tBswtCyvtcZNh3EStARDj2NluJwJiMMbgRZe12jfvfN6lmq0sUvwOT298H8W6qQ==";
				final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";*/
				final String CHARSET = "utf-8";
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		        Date now = new Date();
		        String time = sdf.format(now);
		        System.out.println("时间:"+time);
		        int randomNum = (int)((Math.random()*9+1)*100);
		        String out_trade_no = otherOrderID;
				
				//实例化客户端
				AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA");
				AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
				alipayRequest.setReturnUrl(returnUrl);
				alipayRequest.setNotifyUrl(callBackUrl);//在公共参数中设置回跳和通知地址
				alipayRequest.setBizContent("{" +
						"    \"out_trade_no\":\""+out_trade_no+"\"," +
				        "    \"product_code\":\""+product_code+"\"," +
				        "    \"body\":\""+subject+otherOrderID+"\"," +
				        "    \"total_amount\":\""+total_amount+"\"," +
				        "    \"subject\":\""+subject+"\"" +
				        "  }");//填充业务参数
				String form="";
				try {
				form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
				
	            PayRecord payRecord = new PayRecord();
	            /*if(gameChannels.equals(""))
	            {
	            	payRecord.setGameChanel("");
	            }
	            else
	            {
	            	payRecord.setGameChanel(gameChannels);
	            }*/
	            /*payRecord.setGameChanel(gameChannels);*/
	            payRecord.setGameChanel("支付宝手机APP网站支付");
	            payRecord.setGamePackage(gamePackage);
	            payRecord.setOutTradeNumber(out_trade_no);
	            payRecord.setOrderid(number);
	            payRecord.setPayMoney(total_amount);
	            payRecord.setPayStyle("支付宝手机APP网站支付");
	            payRecord.setPayStatus("0");
	            /*SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMddHHmmss");
	            System.err.println("时间日期:"+timestamp);*/
	            payRecord.setPayTime(new Date());
	            User user = order.getUser();
	            payRecord.setPhone(user.getUsername());
	            payRecordService.add(payRecord);
				} catch (AlipayApiException e) {
					e.printStackTrace();
				}
				httpResponse.setContentType("text/html;charset=" + CHARSET);
				httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
				httpResponse.getWriter().flush();
				httpResponse.getWriter().close();
			}
		}
	}
	
	@RequestMapping(value="/signali",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> signData(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		/*Map<String, String> paramsMap = ... //将异步通知中收到的所有参数都存放到map中*/		
		Map<String, String> paramsMap = new HashMap<String,String>();
		//获取请求参数
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
		    paramsMap.put(name, valueStr);
		}
		/*paramsMap.put("app_id", (String)requestParams.get("app_id"));
		paramsMap.put("auth_app_id", (String) requestParams.get("auth_app_id"));
		paramsMap.put("charset", (String)requestParams.get("charset"));
		System.err.println("获取订单编号:"+requestParams.get("out_trade_no"));*/
		/*创建支付宝支付的公共参数*/
		final String APP_ID = "2016080301699003";
		final String APP_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJzuFpK2ikT/cLzBMQS2G0VBDJM0vjHser7pV+AG5d2kfkSSzRgDMKyTiq871M8jQmEfVlZJNtgXcKdyV5bUhoCNQL4Tq3Jp8Ndo8oAQ/3NvSux794kkq6L2UhHwckJ5yoTb4bNzQYwkGXEmAal22+bZwsc6IVwNzk2TJ0H6VdpVAgMBAAECgYAoA9G/sUoKk/PkPYLJR8ImY5LYSl+hDUKzQX7FwhyE6rfDtocTc2TK7Ig1bJU0CDKZ30q9j8erTDbOi6pn7GMrKAzpF1nSMTjJgio03Kat9784YfI7tcT0YJjaGIsjNCeUiEhy/Hd1LxpExB1Dcet9Siy3USe4qXvzY7lXlkf9AQJBANCY+cWllFUJPwxg3kx77nrqlRBCodKuizcqZBJsZc3k/IDB8LX9UU3sljeNHJM9Ee/AU/fUzDLww4E/BsP0X5UCQQDAl2Nr/RylEw9cveOJDSstYFrVmWU+lZQN0Nq3StFcg/wEtV1H/ajOEHxn4/lYvLN2RcVTgIMm8lwxm1bWu9/BAkAUCU2cjX4E+QFkV/2iTRkoF1ZAHJZcnUVkBB9eoajZsRAL8hUD9hQULxByv4wqHGiXpdqq6HbAwd2VkY89zUBNAkEAl/wgms0RuPfUrMSx9qssws+Cf4RhkMUsJMcIg5OIqzEBRpn19mUovQ3nj3kqgqvQGGsxMRd+6NJkjUVgf2+eQQJAT1uJnT3N9h1O/FAhXrcg1f0tBswtCyvtcZNh3EStARDj2NluJwJiMMbgRZe12jfvfN6lmq0sUvwOT298H8W6qQ==";
		final String CHARSET = "utf-8";
		
		final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
		final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";
		boolean signVerified = false;
		
		try {
			signVerified = AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET,"RSA");
			String out_trade_no = paramsMap.get("out_trade_no");
			String app_id = paramsMap.get("app_id");
			
			AliPayConfig aliPayConfig = aliPayConfigService.queryBysql("from AliPayConfig where APP_ID="+"'"+app_id+"'");
			
			String send_url = "";
			if(aliPayConfig==null)
			{
				status = "0404";
				message = "没有第三方通知的地址";
			}
			else
			{
				status = "6200";
				message = "有第三方通知的地址";
				send_url = aliPayConfig.getRETURN_URL();
			}
			PayRecord payRecord = payRecordService.get(out_trade_no);
			String SEND_URL = send_url;
			if(signVerified){
				// TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
				if(payRecord==null)
				{
					status = "0404";
					message = "数据库中不存在这个订单";
				}
				else
				{
					status = "0200";
					message = "验证签名支付成功";
					try {
	    		        //创建连接
	    		        URL url = new URL(SEND_URL);
	    		        HttpURLConnection connection = (HttpURLConnection) url
	    		                .openConnection();
	    		        connection.setDoOutput(true);
	    		        connection.setDoInput(true);
	    		        connection.setRequestMethod("POST");
	    		        /* connection.setRequestProperty("Authorization", token);*/
	    		        connection.setUseCaches(false);
	    		        connection.setInstanceFollowRedirects(true);
	    		        connection.setRequestProperty("Content-Type","application/json");
	    		        connection.connect();
	    		        
	    		        //POST请求
	    		        DataOutputStream out = new DataOutputStream(
	    		                connection.getOutputStream());
	    		        JSONObject obj = new JSONObject();
	    		        obj.put("out_trade_no", out_trade_no);
	    		        obj.put("status", status);
	    		        obj.put("message", message);
	    		        obj.put("total_amount", requestParams.get("total_amount"));
	    		        System.err.println("通知第三方服务器");
	    		        
	    		        out.writeBytes(obj.toString());
	    		        out.flush();
	    		        out.close();
	    			} catch (Exception e)
	    			{
	    				e.printStackTrace();
	    			}
					payRecord.setPayStatus("1");
					payRecordService.update(payRecord);
					System.err.println("更新支付状态成功");
				}
			}else{
			// TODO 验签失败则记录异常日志，并在response中返回failure.
				if(payRecord==null)
				{
					status = "0404";
					message = "数据库中不存在这个订单";
				}
				else
				{
					status = "0604";
					message = "验证签名支付失败";
					try {
	    		        //创建连接
	    		        URL url = new URL(SEND_URL);
	    		        HttpURLConnection connection = (HttpURLConnection) url
	    		                .openConnection();
	    		        connection.setDoOutput(true);
	    		        connection.setDoInput(true);
	    		        connection.setRequestMethod("POST");
	    		        /* connection.setRequestProperty("Authorization", token);*/
	    		        connection.setUseCaches(false);
	    		        connection.setInstanceFollowRedirects(true);
	    		        connection.setRequestProperty("Content-Type","application/json");
	    		        connection.connect();
	    		        
	    		        //POST请求
	    		        DataOutputStream out = new DataOutputStream(
	    		                connection.getOutputStream());
	    		        JSONObject obj = new JSONObject();
	    		        obj.put("out_trade_no", out_trade_no);
	    		        obj.put("status", status);
	    		        obj.put("message", message);
	    		        obj.put("total_amount", requestParams.get("total_amount"));
	    		        System.err.println("通知第三方服务器");
	    		        
	    		        out.writeBytes(obj.toString());
	    		        out.flush();
	    		        out.close();
	    			} catch (Exception e)
	    			{
	    				e.printStackTrace();
	    			}
					payRecord.setPayStatus("0");
					payRecordService.update(payRecord);
				}
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //调用SDK验证签名
		
		map.put("status", status);
		map.put("message", message);
		return map;
	}
}
