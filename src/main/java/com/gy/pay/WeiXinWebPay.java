package com.gy.pay;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gy.model.Game;
import com.gy.model.Goods;
import com.gy.model.Order;
import com.gy.model.OrderGoods;
import com.gy.model.PayRecord;
import com.gy.model.User;
import com.gy.model.WeixinPayConfig;
import com.gy.pay.PrepayIdRequestHandler;
import com.gy.pay.ConstantUtil;
import com.gy.pay.MD5Util;
import com.gy.pay.WXUtil;
import com.gy.services.OrderGoodsService;
import com.gy.services.OrderService;
import com.gy.services.PayRecordService;
import com.gy.services.WeixinPayConfigService;


@Controller
@RequestMapping("/weixin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WeiXinWebPay {
    
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
	 * 声明支付记录服务，实现自动依赖注入
	 */
	@Autowired
	private PayRecordService payRecordService;
	
	/**
	 * 声明微信支付配置服务的依赖注入
	 */
	@Autowired
	private WeixinPayConfigService weixinPayConfigService;
	
	/**
	 * 实现微信支付的配置服务的依赖注入的get方法
	 * @return
	 */
	public WeixinPayConfigService getWeixinPayConfigService() {
		return weixinPayConfigService;
	}
	
	/**
	 * 创建订单服务
	 */
	@Autowired
	private OrderService orderService;

	/**
	 * 实现微信支付的配置服务的依赖注入的set方法
	 * @param weixinPayConfigService
	 */
	public void setWeixinPayConfigService(
			WeixinPayConfigService weixinPayConfigService) {
		this.weixinPayConfigService = weixinPayConfigService;
	}

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
	 * 实现支付记录的get方法
	 * @return
	 */
	public PayRecordService getPayRecordService() {
		return payRecordService;
	}

	/**
	 * 实现支付记录的set方法
	 * @param payRecordService
	 */
	public void setPayRecordService(PayRecordService payRecordService) {
		this.payRecordService = payRecordService;
	}
	
	/**
	 * 实现订单服务的get方法
	 * @return
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	/**
	 * 实现订单服务的set方法
	 * @param orderService
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@ResponseBody 
    @RequestMapping(value="/webandroidpay",method=RequestMethod.POST)
    public Map<String, Object> getAndroidOrder(@RequestBody Map map,@RequestHeader String token,HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /*Map<String, Object> map = new HashMap<String, Object>();*/
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
			// 获取生成预支付订单的请求类
	        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
	        //String totalFee = request.getParameter("total_fee");
	       // int total_fee=(int) (Float.valueOf(totalFee)*100);
	        Order order = orderGoods.getOrder();
            Game game = order.getGames();
            String gamePackage = game.getGamepackage(); 
            String otherOrderID = order.getOtherOrderID();
            WeixinPayConfig weixinPayConfig = weixinPayConfigService.queryGamepackage(gamePackage);
            if(weixinPayConfig==null)
            {
            	status = "0403";
				message = "数据库中没有添加微信支付的配置信息!";
				/*final String APP_ID = weixinPayConfig.getAPP_ID();
    	        final String MCH_ID = weixinPayConfig.getMCH_ID();
    	        final String NOTIFY_URL = weixinPayConfig.getNOTIFY_URL();*/
    	        /*final String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";*/
    	        /*final String PARTNER_ID = weixinPayConfig.getPARTNER_ID();
    	        final String APP_KEY = weixinPayConfig.getAPP_KEY();*/
    	        
    	        /*商品的标题/交易标题/订单标题/订单关键字等*/
    			String body = orderGoods.getTitle();
    			/*转换商品单价小数点为两位*/
    			String price = orderGoods.getPrice().toString();
    			BigDecimal total = orderGoods.getPrice();
    			System.err.println("大数据:"+total.multiply(new BigDecimal(100)));
    			/*订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]*/
    			int total_fee = (int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString());
    			System.out.println("body"+(int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString()));
    	        /*int total_fee=1;*/
    	        /*System.out.println("total:"+total_fee);
    	        System.out.println("total_fee:" + total_fee);*/
    	        prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
    	        prepayReqHandler.setParameter("body", body);
    	        prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
    	        String nonce_str = WXUtil.getNonceStr();
    	        prepayReqHandler.setParameter("nonce_str", nonce_str);
    	        prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
    	        Map<String, Object> scene_info = new HashMap<String, Object>();
    	        scene_info.put("type", "Android");
    	        scene_info.put("app_name", body);
    	        scene_info.put("package_name", game.getGamepackage());
    	        Map<String, Object> mapinfo = new HashMap<String, Object>();
    	        mapinfo.put("h5_info", scene_info);
    	        ObjectMapper mapper = new ObjectMapper();
    	        String info = "";
    	        info = mapper.writeValueAsString(mapinfo);
    	        prepayReqHandler.setParameter("scene_info", info);
    	        /*String out_trade_no = "2017101217182852172";*/
    	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	        Date now = new Date();
    	        String time = sdf.format(now);
    	        System.out.println("时间:"+time);
    	        int randomNum = (int)((Math.random()*9+1)*1000);
    	        /*String number = time+randomNum+out_trade_no;*/
    	        String number = otherOrderID;
    	        System.err.println("订单号:"+number);
    	        prepayReqHandler.setParameter("out_trade_no", number);
    	        /*prepayReqHandler.setParameter("sign_type","MD5");*/
    	        String spbill_create_ip=request.getRemoteAddr();
    	        prepayReqHandler.setParameter("spbill_create_ip", spbill_create_ip);
    	        String timestamp = WXUtil.getTimeStamp();
    	        prepayReqHandler.setParameter("time_start", timestamp);
    	        System.err.println("签名之前的时间:"+timestamp);
    	        System.out.println(String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("trade_type", "MWEB");
    	        
    	        /**
    	         * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
    	         */
    	        String sign = prepayReqHandler.createMD5Sign();
    	        prepayReqHandler.setParameter("sign", sign);
    	        
    	        
    	        prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
    	        String prepayid = prepayReqHandler.sendPrepay();
    	        String mweb_url = prepayReqHandler.sendWebPay();
    	        
    	        System.err.println("之前生成的签名:"+sign);
    	        // 若获取prepayid成功，将相关信息返回客户端
    	        if (prepayid != null && !prepayid.equals("")) {
    	        	
    	        	System.err.println("签名之后的时间："+timestamp);
    	            
    	            String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
    	                    + ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
    	                    + ConstantUtil.APP_KEY;
    	            /*System.out.println("签名之前字符串:"+signs);*/
    	            
    	            
    	            /*Order order = orderGoods.getOrder();
    	            Game game = order.getGames();
    	            String gamePackage = game.getGamepackage();*/
    	            /*String gameChannels = game.getGameChannels();*/
    	            String goodname = orderGoods.getTitle();
    	            orderGoods.getTotalprice();
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
    	            payRecord.setGameChanel("微信支付");
    	            payRecord.setGamePackage(gamePackage);
    	            payRecord.setOutTradeNumber(number);
    	            payRecord.setOrderid(out_trade_no);
    	            payRecord.setPayMoney(total.toString());
    	            payRecord.setPayStyle("微信");
    	            payRecord.setPayStatus("1");
    	            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMddHHmmss");
    	            System.err.println("时间日期:"+timestamp);
    	            payRecord.setPayTime(new Date());
    	            User user = order.getUser();
    	            payRecord.setPhone(user.getUsername());
    	            payRecordService.add(payRecord);
    	            map.put("code", 0);
    	            map.put("info", "success");
    	            map.put("prepayid", prepayid);
		        	map.put("otherOrderID", otherOrderID);
		        	map.put("goodName", goodname);
		        	map.put("goodPrice", total.toString());
		        	map.put("ourOrderID", out_trade_no);
		        	map.put("mweb_url",mweb_url);
    	            /*map.put("mergeSigns",signs);*/
    	            
    	            /**
    	             * 签名方式与上面类似
    	             */
    	            /*map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());*/
    	            map.put("sign",MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            System.err.println("之后生成的签名:"+MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            map.put("appid", ConstantUtil.APP_ID);
    	            map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
    	            map.put("noncestr", nonce_str);   //与请求prepayId时值一致
    	            map.put("package", "Sign=WXPay");  //固定常量
    	            map.put("partnerid", ConstantUtil.PARTNER_ID);
    	            map.put("key", ConstantUtil.APP_KEY);
    	        } else {
    	            map.put("code", 1);
    	            map.put("info", "获取prepayid失败");
    	        }
            }
            else
            {
            	final String APP_ID = weixinPayConfig.getAPP_ID();
    	        final String MCH_ID = weixinPayConfig.getMCH_ID();
    	        final String NOTIFY_URL = weixinPayConfig.getNOTIFY_URL();
    	        final String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    	        final String PARTNER_ID = weixinPayConfig.getPARTNER_ID();
    	        final String APP_KEY = weixinPayConfig.getAPP_KEY();
    	        
    	        /*商品的标题/交易标题/订单标题/订单关键字等*/
    			String body = orderGoods.getTitle();
    			/*转换商品单价小数点为两位*/
    			String price = orderGoods.getPrice().toString();
    			BigDecimal total = orderGoods.getPrice();
    			System.err.println("大数据:"+total.multiply(new BigDecimal(100)));
    			/*订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]*/
    			int total_fee = (int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString());
    			System.out.println("body"+(int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString()));
    	        /*int total_fee=1;*/
    	        /*System.out.println("total:"+total_fee);
    	        System.out.println("total_fee:" + total_fee);*/
    	        prepayReqHandler.setParameter("appid", APP_ID);
    	        prepayReqHandler.setParameter("body", body);
    	        prepayReqHandler.setParameter("mch_id", MCH_ID);
    	        String nonce_str = WXUtil.getNonceStr();
    	        prepayReqHandler.setParameter("nonce_str", nonce_str);
    	        prepayReqHandler.setParameter("notify_url", NOTIFY_URL);
    	        
    	        Map<String, Object> scene_info = new HashMap<String, Object>();
    	        scene_info.put("type", "Android");
    	        scene_info.put("app_name", body);
    	        scene_info.put("package_name", game.getGamepackage());
    	        Map<String, Object> mapinfo = new HashMap<String, Object>();
    	        mapinfo.put("h5_info", scene_info);
    	        ObjectMapper mapper = new ObjectMapper();
    	        String info = "";
    	        info = mapper.writeValueAsString(mapinfo);
    	        prepayReqHandler.setParameter("scene_info", info);
    	        
    	        /*String out_trade_no = "2017101217182852172";*/
    	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	        Date now = new Date();
    	        String time = sdf.format(now);
    	        System.out.println("时间:"+time);
    	        int randomNum = (int)((Math.random()*9+1)*1000);
    	        /*String number = time+randomNum+out_trade_no;*/
    	        String number = otherOrderID;
    	        System.err.println("订单号:"+number);
    	        prepayReqHandler.setParameter("out_trade_no", number);
    	        /*prepayReqHandler.setParameter("sign_type","MD5");*/
    	        String spbill_create_ip=request.getRemoteAddr();
    	        prepayReqHandler.setParameter("spbill_create_ip", spbill_create_ip);
    	        String timestamp = WXUtil.getTimeStamp();
    	        prepayReqHandler.setParameter("time_start", timestamp);
    	        System.err.println("签名之前的时间:"+timestamp);
    	        System.out.println(String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("trade_type", "MWEB");
    	        
    	        /**
    	         * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
    	         */
    	        String sign = prepayReqHandler.createMD5Sign(APP_KEY);
    	        prepayReqHandler.setParameter("sign", sign);
    	        
    	        
    	        prepayReqHandler.setGateUrl(GATEURL);
    	        String prepayid = prepayReqHandler.sendPrepay();
    	        String mweb_url = prepayReqHandler.sendWebPay();
    	        
    	        System.err.println("之前生成的签名:"+sign);
    	        // 若获取prepayid成功，将相关信息返回客户端
    	        if (prepayid != null && !prepayid.equals("")) {
    	        	
    	        	System.err.println("签名之后的时间："+timestamp);
    	            
    	            String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
    	                    + PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
    	                    + APP_KEY;
    	            /*System.out.println("签名之前字符串:"+signs);*/
    	            
    	            
    	            /*Order order = orderGoods.getOrder();
    	            Game game = order.getGames();
    	            String gamePackage = game.getGamepackage();*/
    	            /*String gameChannels = game.getGameChannels();*/
    	            String goodname = orderGoods.getTitle();
    	            orderGoods.getTotalprice();
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
    	            payRecord.setGameChanel("微信支付");
    	            payRecord.setGamePackage(gamePackage);
    	            payRecord.setOutTradeNumber(number);
    	            payRecord.setOrderid(out_trade_no);
    	            payRecord.setPayMoney(total.toString());
    	            payRecord.setPayStyle("微信");
    	            payRecord.setPayStatus("1");
    	            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMddHHmmss");
    	            System.err.println("时间日期:"+timestamp);
    	            payRecord.setPayTime(new Date());
    	            User user = order.getUser();
    	            payRecord.setPhone(user.getUsername());
    	            payRecordService.add(payRecord);
    	            map.put("code", 0);
    	            map.put("info", "success");
    	            map.put("prepayid", prepayid);
		        	map.put("otherOrderID", otherOrderID);
		        	map.put("goodName", goodname);
		        	map.put("goodPrice", total.toString());
		        	map.put("ourOrderID", out_trade_no);
		        	map.put("mweb_url", mweb_url);
		        	
    	            /*map.put("mergeSigns",signs);*/
    	            
    	            /**
    	             * 签名方式与上面类似
    	             */
    	            /*map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());*/
    	            map.put("sign",MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            System.err.println("之后生成的签名:"+MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            map.put("appid", APP_ID);
    	            map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
    	            map.put("noncestr", nonce_str);   //与请求prepayId时值一致
    	            map.put("package", "Sign=WXPay");  //固定常量
    	            map.put("partnerid", PARTNER_ID);
    	            map.put("key", APP_KEY);
    	        } else {
    	            map.put("code", 1);
    	            map.put("info", "获取prepayid失败");
    	        }
            }
		}
        
        return map;
    }

	@ResponseBody 
    @RequestMapping(value="/webiospay",method=RequestMethod.POST)
    public Map<String, Object> getIOSOrder(@RequestBody Map map,@RequestHeader String token,HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /*Map<String, Object> map = new HashMap<String, Object>();*/
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
			// 获取生成预支付订单的请求类
	        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
	        //String totalFee = request.getParameter("total_fee");
	       // int total_fee=(int) (Float.valueOf(totalFee)*100);
	        Order order = orderGoods.getOrder();
            Game game = order.getGames();
            String gamePackage = game.getGamepackage(); 
            String otherOrderID = order.getOtherOrderID();
            WeixinPayConfig weixinPayConfig = weixinPayConfigService.queryGamepackage(gamePackage);
            if(weixinPayConfig==null)
            {
            	status = "0403";
				message = "数据库中没有添加微信支付的配置信息!";
				/*final String APP_ID = weixinPayConfig.getAPP_ID();
    	        final String MCH_ID = weixinPayConfig.getMCH_ID();
    	        final String NOTIFY_URL = weixinPayConfig.getNOTIFY_URL();*/
    	        /*final String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";*/
    	        /*final String PARTNER_ID = weixinPayConfig.getPARTNER_ID();
    	        final String APP_KEY = weixinPayConfig.getAPP_KEY();*/
    	        
    	        /*商品的标题/交易标题/订单标题/订单关键字等*/
    			String body = orderGoods.getTitle();
    			/*转换商品单价小数点为两位*/
    			String price = orderGoods.getPrice().toString();
    			BigDecimal total = orderGoods.getPrice();
    			System.err.println("大数据:"+total.multiply(new BigDecimal(100)));
    			/*订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]*/
    			int total_fee = (int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString());
    			System.out.println("body"+(int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString()));
    	        /*int total_fee=1;*/
    	        /*System.out.println("total:"+total_fee);
    	        System.out.println("total_fee:" + total_fee);*/
    	        prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
    	        prepayReqHandler.setParameter("body", body);
    	        prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
    	        String nonce_str = WXUtil.getNonceStr();
    	        prepayReqHandler.setParameter("nonce_str", nonce_str);
    	        prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
    	        
    	        /*这一段设置场景信息--开始*/
    	        Map<String, Object> scene_info = new HashMap<String, Object>();
    	        scene_info.put("type", "IOS");
    	        scene_info.put("app_name", body);
    	        scene_info.put("bundle_id", game.getGamepackage());
    	        Map<String, Object> mapinfo = new HashMap<String, Object>();
    	        mapinfo.put("h5_info", scene_info);
    	        ObjectMapper mapper = new ObjectMapper();
    	        String info = "";
    	        info = mapper.writeValueAsString(mapinfo);
    	        prepayReqHandler.setParameter("scene_info", info);
    	        /*这一段设置场景信息--结束*/
    	        
    	        /*String out_trade_no = "2017101217182852172";*/
    	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	        Date now = new Date();
    	        String time = sdf.format(now);
    	        System.out.println("时间:"+time);
    	        int randomNum = (int)((Math.random()*9+1)*1000);
    	        /*String number = time+randomNum+out_trade_no;*/
    	        String number = otherOrderID;
    	        System.err.println("订单号:"+number);
    	        prepayReqHandler.setParameter("out_trade_no", number);
    	        /*prepayReqHandler.setParameter("sign_type","MD5");*/
    	        String spbill_create_ip=request.getRemoteAddr();
    	        prepayReqHandler.setParameter("spbill_create_ip", spbill_create_ip);
    	        String timestamp = WXUtil.getTimeStamp();
    	        prepayReqHandler.setParameter("time_start", timestamp);
    	        System.err.println("签名之前的时间:"+timestamp);
    	        System.out.println(String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("trade_type", "MWEB");
    	        
    	        /**
    	         * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
    	         */
    	        String sign = prepayReqHandler.createMD5Sign();
    	        prepayReqHandler.setParameter("sign", sign);
    	        
    	        
    	        prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
    	        String prepayid = prepayReqHandler.sendPrepay();
    	        String mweb_url = prepayReqHandler.sendPrepay();
    	        
    	        System.err.println("之前生成的签名:"+sign);
    	        // 若获取prepayid成功，将相关信息返回客户端
    	        if (prepayid != null && !prepayid.equals("")) {
    	        	
    	        	System.err.println("签名之后的时间："+timestamp);
    	            
    	            String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
    	                    + ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
    	                    + ConstantUtil.APP_KEY;
    	            /*System.out.println("签名之前字符串:"+signs);*/
    	            
    	            
    	            /*Order order = orderGoods.getOrder();
    	            Game game = order.getGames();
    	            String gamePackage = game.getGamepackage();*/
    	            /*String gameChannels = game.getGameChannels();*/
    	            String goodname = orderGoods.getTitle();
    	            orderGoods.getTotalprice();
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
    	            payRecord.setGameChanel("微信支付");
    	            payRecord.setGamePackage(gamePackage);
    	            payRecord.setOutTradeNumber(number);
    	            payRecord.setOrderid(out_trade_no);
    	            payRecord.setPayMoney(total.toString());
    	            payRecord.setPayStyle("微信");
    	            payRecord.setPayStatus("1");
    	            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMddHHmmss");
    	            System.err.println("时间日期:"+timestamp);
    	            payRecord.setPayTime(new Date());
    	            User user = order.getUser();
    	            payRecord.setPhone(user.getUsername());
    	            payRecordService.add(payRecord);
    	            map.put("code", 0);
    	            map.put("info", "success");
    	            map.put("prepayid", prepayid);
		        	map.put("otherOrderID", otherOrderID);
		        	map.put("goodName", goodname);
		        	map.put("goodPrice", total.toString());
		        	map.put("ourOrderID", out_trade_no);
		        	map.put("mweb_url", mweb_url);
    	            /*map.put("mergeSigns",signs);*/
    	            
    	            /**
    	             * 签名方式与上面类似
    	             */
    	            /*map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());*/
    	            map.put("sign",MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            System.err.println("之后生成的签名:"+MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            map.put("appid", ConstantUtil.APP_ID);
    	            map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
    	            map.put("noncestr", nonce_str);   //与请求prepayId时值一致
    	            map.put("package", "Sign=WXPay");  //固定常量
    	            map.put("partnerid", ConstantUtil.PARTNER_ID);
    	            map.put("key", ConstantUtil.APP_KEY);
    	        } else {
    	            map.put("code", 1);
    	            map.put("info", "获取prepayid失败");
    	        }
            }
            else
            {
            	final String APP_ID = weixinPayConfig.getAPP_ID();
    	        final String MCH_ID = weixinPayConfig.getMCH_ID();
    	        final String NOTIFY_URL = weixinPayConfig.getNOTIFY_URL();
    	        final String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    	        final String PARTNER_ID = weixinPayConfig.getPARTNER_ID();
    	        final String APP_KEY = weixinPayConfig.getAPP_KEY();
    	        
    	        /*商品的标题/交易标题/订单标题/订单关键字等*/
    			String body = orderGoods.getTitle();
    			/*转换商品单价小数点为两位*/
    			String price = orderGoods.getPrice().toString();
    			BigDecimal total = orderGoods.getPrice();
    			System.err.println("大数据:"+total.multiply(new BigDecimal(100)));
    			/*订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]*/
    			int total_fee = (int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString());
    			System.out.println("body"+(int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString()));
    	        /*int total_fee=1;*/
    	        /*System.out.println("total:"+total_fee);
    	        System.out.println("total_fee:" + total_fee);*/
    	        prepayReqHandler.setParameter("appid", APP_ID);
    	        prepayReqHandler.setParameter("body", body);
    	        prepayReqHandler.setParameter("mch_id", MCH_ID);
    	        String nonce_str = WXUtil.getNonceStr();
    	        prepayReqHandler.setParameter("nonce_str", nonce_str);
    	        prepayReqHandler.setParameter("notify_url", NOTIFY_URL);
    	        
    	        /*这一段设置场景信息--开始*/
    	        Map<String, Object> scene_info = new HashMap<String, Object>();
    	        scene_info.put("type", "IOS");
    	        scene_info.put("app_name", body);
    	        scene_info.put("bundle_id", game.getGamepackage());
    	        Map<String, Object> mapinfo = new HashMap<String, Object>();
    	        mapinfo.put("h5_info", scene_info);
    	        ObjectMapper mapper = new ObjectMapper();
    	        String info = "";
    	        info = mapper.writeValueAsString(mapinfo);
    	        prepayReqHandler.setParameter("scene_info", info);
    	        /*这一段设置场景信息--结束*/
    	        
    	        /*String out_trade_no = "2017101217182852172";*/
    	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	        Date now = new Date();
    	        String time = sdf.format(now);
    	        System.out.println("时间:"+time);
    	        int randomNum = (int)((Math.random()*9+1)*1000);
    	        /*String number = time+randomNum+out_trade_no;*/
    	        String number = otherOrderID;
    	        System.err.println("订单号:"+number);
    	        prepayReqHandler.setParameter("out_trade_no", number);
    	        /*prepayReqHandler.setParameter("sign_type","MD5");*/
    	        String spbill_create_ip=request.getRemoteAddr();
    	        prepayReqHandler.setParameter("spbill_create_ip", spbill_create_ip);
    	        String timestamp = WXUtil.getTimeStamp();
    	        prepayReqHandler.setParameter("time_start", timestamp);
    	        System.err.println("签名之前的时间:"+timestamp);
    	        System.out.println(String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("trade_type", "MWEB");
    	        
    	        /**
    	         * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
    	         */
    	        String sign = prepayReqHandler.createMD5Sign(APP_KEY);
    	        prepayReqHandler.setParameter("sign", sign);
    	        
    	        
    	        prepayReqHandler.setGateUrl(GATEURL);
    	        String prepayid = prepayReqHandler.sendPrepay();
    	        String mweb_url = prepayReqHandler.sendPrepay();
    	        
    	        System.err.println("之前生成的签名:"+sign);
    	        // 若获取prepayid成功，将相关信息返回客户端
    	        if (prepayid != null && !prepayid.equals("")) {
    	        	
    	        	System.err.println("签名之后的时间："+timestamp);
    	            
    	            String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
    	                    + PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
    	                    + APP_KEY;
    	            /*System.out.println("签名之前字符串:"+signs);*/
    	            
    	            
    	            /*Order order = orderGoods.getOrder();
    	            Game game = order.getGames();
    	            String gamePackage = game.getGamepackage();*/
    	            /*String gameChannels = game.getGameChannels();*/
    	            String goodname = orderGoods.getTitle();
    	            orderGoods.getTotalprice();
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
    	            payRecord.setGameChanel("微信支付");
    	            payRecord.setGamePackage(gamePackage);
    	            payRecord.setOutTradeNumber(number);
    	            payRecord.setOrderid(out_trade_no);
    	            payRecord.setPayMoney(total.toString());
    	            payRecord.setPayStyle("微信");
    	            payRecord.setPayStatus("1");
    	            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMddHHmmss");
    	            System.err.println("时间日期:"+timestamp);
    	            payRecord.setPayTime(new Date());
    	            User user = order.getUser();
    	            payRecord.setPhone(user.getUsername());
    	            payRecordService.add(payRecord);
    	            map.put("code", 0);
    	            map.put("info", "success");
    	            map.put("prepayid", prepayid);
		        	map.put("otherOrderID", otherOrderID);
		        	map.put("goodName", goodname);
		        	map.put("goodPrice", total.toString());
		        	map.put("ourOrderID", out_trade_no);
		        	map.put("mweb_url", mweb_url);
    	            /*map.put("mergeSigns",signs);*/
    	            
    	            /**
    	             * 签名方式与上面类似
    	             */
    	            /*map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());*/
    	            map.put("sign",MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            System.err.println("之后生成的签名:"+MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            map.put("appid", APP_ID);
    	            map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
    	            map.put("noncestr", nonce_str);   //与请求prepayId时值一致
    	            map.put("package", "Sign=WXPay");  //固定常量
    	            map.put("partnerid", PARTNER_ID);
    	            map.put("key", APP_KEY);
    	        } else {
    	            map.put("code", 1);
    	            map.put("info", "获取prepayid失败");
    	        }
            }
		}
        
        return map;
    }
	
	@ResponseBody 
    @RequestMapping(value="/webwappay",method=RequestMethod.POST)
    public Map<String, Object> getWAPOrder(@RequestBody Map map,@RequestHeader String token,HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /*Map<String, Object> map = new HashMap<String, Object>();*/
		/*商户网站唯一订单号*/
		String out_trade_no = ((String) map.get("orderid")).trim();
		String wapUrl = ((String) map.get("wapUrl")).trim();
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
			// 获取生成预支付订单的请求类
	        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
	        //String totalFee = request.getParameter("total_fee");
	       // int total_fee=(int) (Float.valueOf(totalFee)*100);
	        Order order = orderGoods.getOrder();
            Game game = order.getGames();
            String gamePackage = game.getGamepackage(); 
            String otherOrderID = order.getOtherOrderID();
            WeixinPayConfig weixinPayConfig = weixinPayConfigService.queryGamepackage(gamePackage);
            if(weixinPayConfig==null)
            {
            	status = "0403";
				message = "数据库中没有添加微信支付的配置信息!";
				/*final String APP_ID = weixinPayConfig.getAPP_ID();
    	        final String MCH_ID = weixinPayConfig.getMCH_ID();
    	        final String NOTIFY_URL = weixinPayConfig.getNOTIFY_URL();*/
    	        /*final String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";*/
    	        /*final String PARTNER_ID = weixinPayConfig.getPARTNER_ID();
    	        final String APP_KEY = weixinPayConfig.getAPP_KEY();*/
    	        
    	        /*商品的标题/交易标题/订单标题/订单关键字等*/
    			String body = orderGoods.getTitle();
    			/*转换商品单价小数点为两位*/
    			String price = orderGoods.getPrice().toString();
    			BigDecimal total = orderGoods.getPrice();
    			System.err.println("大数据:"+total.multiply(new BigDecimal(100)));
    			/*订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]*/
    			int total_fee = (int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString());
    			System.out.println("body"+(int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString()));
    	        /*int total_fee=1;*/
    	        /*System.out.println("total:"+total_fee);
    	        System.out.println("total_fee:" + total_fee);*/
    	        prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
    	        prepayReqHandler.setParameter("body", body);
    	        prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
    	        String nonce_str = WXUtil.getNonceStr();
    	        prepayReqHandler.setParameter("nonce_str", nonce_str);
    	        prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
    	        
    	        /*这一段设置场景信息--开始*/
    	        Map<String, Object> scene_info = new HashMap<String, Object>();
    	        scene_info.put("type", "Wap");
    	        scene_info.put("wap_url", wapUrl);
    	        scene_info.put("wap_name", body);
    	        Map<String, Object> mapinfo = new HashMap<String, Object>();
    	        mapinfo.put("h5_info", scene_info);
    	        ObjectMapper mapper = new ObjectMapper();
    	        String info = "";
    	        info = mapper.writeValueAsString(mapinfo);
    	        prepayReqHandler.setParameter("scene_info", info);
    	        /*这一段设置场景信息--结束*/
    	        
    	        /*String out_trade_no = "2017101217182852172";*/
    	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	        Date now = new Date();
    	        String time = sdf.format(now);
    	        System.out.println("时间:"+time);
    	        int randomNum = (int)((Math.random()*9+1)*1000);
    	        /*String number = time+randomNum+out_trade_no;*/
    	        String number = otherOrderID;
    	        System.err.println("订单号:"+number);
    	        prepayReqHandler.setParameter("out_trade_no", number);
    	        /*prepayReqHandler.setParameter("sign_type","MD5");*/
    	        String spbill_create_ip=request.getRemoteAddr();
    	        prepayReqHandler.setParameter("spbill_create_ip", spbill_create_ip);
    	        String timestamp = WXUtil.getTimeStamp();
    	        prepayReqHandler.setParameter("time_start", timestamp);
    	        System.err.println("签名之前的时间:"+timestamp);
    	        System.out.println(String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("trade_type", "MWEB");
    	        
    	        /**
    	         * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
    	         */
    	        String sign = prepayReqHandler.createMD5Sign();
    	        prepayReqHandler.setParameter("sign", sign);
    	        
    	        
    	        prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
    	        String prepayid = prepayReqHandler.sendPrepay();
    	        String mweb_url = prepayReqHandler.sendPrepay();
    	        
    	        System.err.println("之前生成的签名:"+sign);
    	        // 若获取prepayid成功，将相关信息返回客户端
    	        if (prepayid != null && !prepayid.equals("")) {
    	        	
    	        	System.err.println("签名之后的时间："+timestamp);
    	            
    	            String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
    	                    + ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
    	                    + ConstantUtil.APP_KEY;
    	            /*System.out.println("签名之前字符串:"+signs);*/
    	            
    	            
    	            /*Order order = orderGoods.getOrder();
    	            Game game = order.getGames();
    	            String gamePackage = game.getGamepackage();*/
    	            /*String gameChannels = game.getGameChannels();*/
    	            String goodname = orderGoods.getTitle();
    	            orderGoods.getTotalprice();
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
    	            payRecord.setGameChanel("微信支付");
    	            payRecord.setGamePackage(gamePackage);
    	            payRecord.setOutTradeNumber(number);
    	            payRecord.setOrderid(out_trade_no);
    	            payRecord.setPayMoney(total.toString());
    	            payRecord.setPayStyle("微信");
    	            payRecord.setPayStatus("1");
    	            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMddHHmmss");
    	            System.err.println("时间日期:"+timestamp);
    	            payRecord.setPayTime(new Date());
    	            User user = order.getUser();
    	            payRecord.setPhone(user.getUsername());
    	            payRecordService.add(payRecord);
    	            map.put("code", 0);
    	            map.put("info", "success");
    	            map.put("prepayid", prepayid);
		        	map.put("otherOrderID", otherOrderID);
		        	map.put("goodName", goodname);
		        	map.put("goodPrice", total.toString());
		        	map.put("ourOrderID", out_trade_no);
		        	map.put("mweb_url", mweb_url);
    	            /*map.put("mergeSigns",signs);*/
    	            
    	            /**
    	             * 签名方式与上面类似
    	             */
    	            /*map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());*/
    	            map.put("sign",MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            System.err.println("之后生成的签名:"+MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            map.put("appid", ConstantUtil.APP_ID);
    	            map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
    	            map.put("noncestr", nonce_str);   //与请求prepayId时值一致
    	            map.put("package", "Sign=WXPay");  //固定常量
    	            map.put("partnerid", ConstantUtil.PARTNER_ID);
    	            map.put("key", ConstantUtil.APP_KEY);
    	        } else {
    	            map.put("code", 1);
    	            map.put("info", "获取prepayid失败");
    	        }
            }
            else
            {
            	final String APP_ID = weixinPayConfig.getAPP_ID();
    	        final String MCH_ID = weixinPayConfig.getMCH_ID();
    	        final String NOTIFY_URL = weixinPayConfig.getNOTIFY_URL();
    	        final String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    	        final String PARTNER_ID = weixinPayConfig.getPARTNER_ID();
    	        final String APP_KEY = weixinPayConfig.getAPP_KEY();
    	        
    	        /*商品的标题/交易标题/订单标题/订单关键字等*/
    			String body = orderGoods.getTitle();
    			/*转换商品单价小数点为两位*/
    			String price = orderGoods.getPrice().toString();
    			BigDecimal total = orderGoods.getPrice();
    			System.err.println("大数据:"+total.multiply(new BigDecimal(100)));
    			/*订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]*/
    			int total_fee = (int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString());
    			System.out.println("body"+(int)Float.parseFloat(total.multiply(new BigDecimal(100)).toString()));
    	        /*int total_fee=1;*/
    	        /*System.out.println("total:"+total_fee);
    	        System.out.println("total_fee:" + total_fee);*/
    	        prepayReqHandler.setParameter("appid", APP_ID);
    	        prepayReqHandler.setParameter("body", body);
    	        prepayReqHandler.setParameter("mch_id", MCH_ID);
    	        String nonce_str = WXUtil.getNonceStr();
    	        prepayReqHandler.setParameter("nonce_str", nonce_str);
    	        prepayReqHandler.setParameter("notify_url", NOTIFY_URL);
    	        
    	        /*这一段设置场景信息--开始*/
    	        Map<String, Object> scene_info = new HashMap<String, Object>();
    	        scene_info.put("type", "Wap");
    	        scene_info.put("wap_url", "http://23sdk.23h5.cn/h5SDK/index.html");
    	        scene_info.put("wap_name", "游达SDK支付");
    	        Map<String, Object> mapinfo = new HashMap<String, Object>();
    	        mapinfo.put("h5_info", scene_info);
    	        ObjectMapper mapper = new ObjectMapper();
    	        String info = "";
    	        info = mapper.writeValueAsString(mapinfo);
    	        prepayReqHandler.setParameter("scene_info", info);
    	        /*这一段设置场景信息--结束*/
    	        
    	        /*String out_trade_no = "2017101217182852172";*/
    	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	        Date now = new Date();
    	        String time = sdf.format(now);
    	        System.out.println("时间:"+time);
    	        int randomNum = (int)((Math.random()*9+1)*1000);
    	        /*String number = time+randomNum+out_trade_no;*/
    	        String number = otherOrderID;
    	        System.err.println("订单号:"+number);
    	        prepayReqHandler.setParameter("out_trade_no", number);
    	        /*prepayReqHandler.setParameter("sign_type","MD5");*/
    	        String spbill_create_ip=request.getRemoteAddr();
    	        prepayReqHandler.setParameter("spbill_create_ip", spbill_create_ip);
    	        String timestamp = WXUtil.getTimeStamp();
    	        prepayReqHandler.setParameter("time_start", timestamp);
    	        System.err.println("签名之前的时间:"+timestamp);
    	        System.out.println(String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
    	        prepayReqHandler.setParameter("trade_type", "MWEB");
    	        
    	        /**
    	         * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
    	         */
    	        String sign = prepayReqHandler.createMD5Sign(APP_KEY);
    	        prepayReqHandler.setParameter("sign", sign);
    	        
    	        
    	        prepayReqHandler.setGateUrl(GATEURL);
    	        String prepayid = prepayReqHandler.sendPrepay();
    	        String mweb_url = prepayReqHandler.sendPrepay();
    	        
    	        System.err.println("之前生成的签名:"+sign);
    	        // 若获取prepayid成功，将相关信息返回客户端
    	        if (prepayid != null && !prepayid.equals("")) {
    	        	
    	        	System.err.println("签名之后的时间："+timestamp);
    	            
    	            String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
    	                    + PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
    	                    + APP_KEY;
    	            /*System.out.println("签名之前字符串:"+signs);*/
    	            
    	            
    	            /*Order order = orderGoods.getOrder();
    	            Game game = order.getGames();
    	            String gamePackage = game.getGamepackage();*/
    	            /*String gameChannels = game.getGameChannels();*/
    	            String goodname = orderGoods.getTitle();
    	            orderGoods.getTotalprice();
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
    	            payRecord.setGameChanel("微信支付");
    	            payRecord.setGamePackage(gamePackage);
    	            payRecord.setOutTradeNumber(number);
    	            payRecord.setOrderid(out_trade_no);
    	            payRecord.setPayMoney(total.toString());
    	            payRecord.setPayStyle("微信");
    	            payRecord.setPayStatus("1");
    	            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMddHHmmss");
    	            System.err.println("时间日期:"+timestamp);
    	            payRecord.setPayTime(new Date());
    	            User user = order.getUser();
    	            payRecord.setPhone(user.getUsername());
    	            payRecordService.add(payRecord);
    	            map.put("code", 0);
    	            map.put("info", "success");
    	            map.put("prepayid", prepayid);
		        	map.put("otherOrderID", otherOrderID);
		        	map.put("goodName", goodname);
		        	map.put("goodPrice", total.toString());
		        	map.put("ourOrderID", out_trade_no);
		        	map.put("mweb_url", mweb_url);
    	            /*map.put("mergeSigns",signs);*/
    	            
    	            /**
    	             * 签名方式与上面类似
    	             */
    	            /*map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());*/
    	            map.put("sign",MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            System.err.println("之后生成的签名:"+MD5Util.MD5Encode(signs, "utf8").toUpperCase());
    	            map.put("appid", APP_ID);
    	            map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
    	            map.put("noncestr", nonce_str);   //与请求prepayId时值一致
    	            map.put("package", "Sign=WXPay");  //固定常量
    	            map.put("partnerid", PARTNER_ID);
    	            map.put("key", APP_KEY);
    	        } else {
    	            map.put("code", 1);
    	            map.put("info", "获取prepayid失败");
    	        }
            }
		}
        
        return map;
    }
	
    /**
     * 接收微信支付成功通知
     * @param request
     * @param response
     * @throws IOException
     */
	@RequestMapping(value = "/signwebpay")
	@ResponseBody
    public Map<String,Object> signWebPay(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("微信支付回调");
        /*PrintWriter writer = response.getWriter();*/
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        String out_trade_no = request.getParameter("out_trade_no");
        System.err.println("订单编号:"+out_trade_no);
        Map<String,Object> mapdata = new HashMap<String,Object>();
        /*PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);*/
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        System.out.println("微信支付通知结果：" + result);
        Map<String, String> map = null;
        try {
            /**
             * 解析微信通知返回的信息
             */
            map = XMLUtil.doXMLParse(result);
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("=========:"+result);
        // 若支付成功，则告知微信服务器收到通知
        if (map.get("return_code").equals("SUCCESS")) {
            if (map.get("result_code").equals("SUCCESS")) {
                System.out.println("充值成功！");
                PayRecord payRecord = payRecordService.get(out_trade_no);
                System.err.println("支付情况:"+payRecord);
                /*System.out.println("订单号："+Long.valueOf(map.get("out_trade_no")));*/
                /*System.out.println("payRecord.getPayTime():"+payRecord.getPayTime()==null+","+payRecord.getPayTime());*/
                //判断通知是否已处理，若已处理，则不予处理
                if(payRecord.getPayTime()!=null){
                    System.out.println("通知微信后台");
                    payRecord.setPayTime(new Date());
                    String phone=payRecord.getPhone();
                    /*AppCustomer appCustomer=appCustomerService.getByPhone(phone);
                    float balance=appCustomer.getBalance();
                    balance+=Float.valueOf(map.get("total_fee"))/100;
                    appCustomer.setBalance(balance);
                    appCustomerService.update(appCustomer);*/
                    /*String notifyStr = XMLUtil.setXML("SUCCESS", "支付成功!");
                    writer.write(notifyStr);
                    writer.flush();*/
                    String send_url="";
                    String appid = "";
					try {
						appid = map.get("appid");
						System.err.println("获取到的APPID是:"+appid);
	                    WeixinPayConfig weixinPayConfig = weixinPayConfigService.queryBysql("from WeixinPayConfig where APP_ID="+"'"+appid+"'");
	                    send_url = weixinPayConfig.getRETURN_URL();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
                    String SEND_URL = send_url;
                    Order order = orderService.queryBysql("from Order where otherOrderID="+"'"+out_trade_no+"'");
	    			String  resultcode = order.getIsPushed();
	    			if(resultcode==null || resultcode.equals(""))
	    			{
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
		    		        obj.put("status", "0200");
		    		        obj.put("message", "微信支付验签成功");
		    		        System.err.println("通知第三方服务器");
		    		        
		    		        out.writeBytes(obj.toString());
		    		        out.flush();
		    		        out.close();
		    			} catch (Exception e)
		    			{
		    				e.printStackTrace();
		    			}
	    				order.setIsPushed("1");
	    				orderService.update(order);
	    				payRecord.setPayStatus("1");
			            payRecordService.update(payRecord);
	    			}
	    			else
	    			{
	    				System.err.println("已经推送了，不需要推送了");
	    				order.setIsPushed("1");
	    				orderService.update(order);
	    			}
                }
            }
            else
            {
                PayRecord payRecord = payRecordService.get(out_trade_no);
                String send_url="";
                String appid = "";
				try {
					appid = map.get("appid");
					System.err.println("获取到的APPID是:"+appid);
                    WeixinPayConfig weixinPayConfig = weixinPayConfigService.queryBysql("from WeixinPayConfig where APP_ID="+"'"+appid+"'");
                    send_url = weixinPayConfig.getRETURN_URL();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
                String SEND_URL = send_url;
                Order order = orderService.queryBysql("from Order where otherOrderID="+"'"+out_trade_no+"'");
    			String  resultcode = order.getIsPushed();
    			if(resultcode==null || resultcode.equals(""))
    			{
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
	    		        obj.put("status", "0604");
	    		        obj.put("message", "微信支付验签失败");
	    		        System.err.println("通知第三方服务器");
	    		        
	    		        out.writeBytes(obj.toString());
	    		        out.flush();
	    		        out.close();
	    			} catch (Exception e)
	    			{
	    				e.printStackTrace();
	    			}
    				order.setIsPushed("1");
    				orderService.update(order);
    				payRecord.setPayStatus("1");
		            payRecordService.update(payRecord);
    			}
            }
        }
        return mapdata;
    }
	 
	@RequestMapping(value="receive",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> receiveServer(@RequestParam String status,String out_trade_no){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(status.equals("0200"))
		{
			
			PayRecord payRecord = payRecordService.get(out_trade_no);
			if(payRecord==null)
			{
				status = "0604";
				message = "没有这个订单";
			}
			else
			{
				message = "支付成功!";
				status = "0200";
				payRecord.setPayStatus("1");
				payRecordService.update(payRecord);
			}
		}
		else
		{
			PayRecord payRecord = payRecordService.get(out_trade_no);
			if(payRecord==null)
			{
				status = "0604";
				message = "没有这个订单";
			}
			else
			{
				message = "支付失败!";
				status = "0605";
				payRecord.setPayStatus("0");
				payRecordService.update(payRecord);
			}
		}
		
		map.put("status",status);
		map.put("message", message);
		map.remove("out_trade_no");
		
		return map;
	}
}