package com.gy.pay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.ResponseBody;


import com.gy.model.OrderGoods;
import com.gy.pay.PrepayIdRequestHandler;
import com.gy.pay.ConstantUtil;
import com.gy.pay.MD5Util;
import com.gy.pay.WXUtil;
import com.gy.services.OrderGoodsService;


@Controller
@RequestMapping("/weixin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WeiXinPay {
    
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
    
	@ResponseBody 
    @RequestMapping("/pay")
    public Map<String, Object> getOrder(@RequestBody Map map,@RequestHeader String token,HttpServletRequest request, HttpServletResponse response)
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
	        /*String out_trade_no = "2017101217182852172";*/
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	        Date now = new Date();
	        String time = sdf.format(now);
	        System.out.println("时间:"+time);
	        int randomNum = (int)((Math.random()*9+1)*1000);
	        /*String number = time+randomNum+out_trade_no;*/
	        String number = time;
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
	        prepayReqHandler.setParameter("trade_type", "APP");
	        
	        /**
	         * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
	         */
	        String sign = prepayReqHandler.createMD5Sign();
	        prepayReqHandler.setParameter("sign", sign);
	        
	        
	        prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
	        String prepayid = prepayReqHandler.sendPrepay();
	        
	        /*String signni = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
                    + ConstantUtil.MCH_ID  + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
                    + ConstantUtil.APP_KEY;
            System.out.println("签名之前字符串:"+signni);*/
	        System.err.println("之前生成的签名:"+sign);
	        // 若获取prepayid成功，将相关信息返回客户端
	        if (prepayid != null && !prepayid.equals("")) {
	        	
	        	System.err.println("签名之后的时间："+timestamp);
	            /*String signs = "appid=" + ConstantUtil.APP_ID +"&body="+body+"&mch_id="+ConstantUtil.MCH_ID +"&nonce_str=" + nonce_str +"&notify_url="+ConstantUtil.NOTIFY_URL+"&out_trade_no="+number+"&sign_type="+"MD5"+"&spbill_create_ip="+spbill_create_ip+ "&time_start=" + timestamp +"&total_fee="+String.valueOf(total_fee)+"&trade_type="+"APP"+ "&key="
	                    + ConstantUtil.PARTNER_KEY;*/
	            
	            String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
	                    + ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
	                    + ConstantUtil.APP_KEY;
	            /*System.out.println("签名之前字符串:"+signs);*/
	            map.put("code", 0);
	            map.put("info", "success");
	            map.put("prepayid", prepayid);
	            /*map.put("mergeSigns",signs);*/
	            
	            /**
	             * 签名方式与上面类似
	             */
	            /*map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());*/
	            map.put("sign",MD5Util.MD5Encode(signs, "utf8").toUpperCase());
	            /*System.err.println("之后生成的签名:"+MD5Util.MD5Encode(signs, "utf8").toUpperCase());*/
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
        
        return map;
    }

    /**
     * 接收微信支付成功通知
     * @param request
     * @param response
     * @throws IOException
     */
    /*@RequestMapping(value = "/notify")
    public void getnotify(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("微信支付回调");
        PrintWriter writer = response.getWriter();
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
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
            *//**
             * 解析微信通知返回的信息
             *//*
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
                PayRecord payRecord=payRecordService.get(Long.valueOf(map.get("out_trade_no")));
                System.out.println("订单号："+Long.valueOf(map.get("out_trade_no")));
                System.out.println("payRecord.getPayTime():"+payRecord.getPayTime()==null+","+payRecord.getPayTime());
                //判断通知是否已处理，若已处理，则不予处理
                if(payRecord.getPayTime()==null){
                    System.out.println("通知微信后台");
                    payRecord.setPayTime(new Date());
                    String phone=payRecord.getPhone();
                    AppCustomer appCustomer=appCustomerService.getByPhone(phone);
                    float balance=appCustomer.getBalance();
                    balance+=Float.valueOf(map.get("total_fee"))/100;
                    appCustomer.setBalance(balance);
                    appCustomerService.update(appCustomer);
                    payRecordService.update(payRecord);
                    String notifyStr = XMLUtil.setXML("SUCCESS", "");
                    writer.write(notifyStr);
                    writer.flush();
                }
            }
        }
    }*/

}