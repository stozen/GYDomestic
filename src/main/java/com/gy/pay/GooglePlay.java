package com.gy.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.services.OrderService;

/**
 * @author Chencongye
 * @version 0.0.1
 * @introduce 这是GooglePlay支付方式放回的订单处理结果
 */
@Controller
@RequestMapping(value="/googleplay")
public class GooglePlay {
	
	/*1.开启Googleplay支付*/
	
	/**
	 * 实现自动注入
	 */
	@Autowired
	private OrderService orderService;
	
	/**
	 * 生成orderService的get方法
	 * @return
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	/**
	 * 生成orderService的set方法
	 * @param orderService
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * 只是使用googlepay支付功能
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="pay",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> googlepay(@RequestBody Map map,BindingResult bindingResult){
		
		String reqprice = (String)map.get("price");
		String reqnumber = (String)map.get("number");
		
		double price = Double.parseDouble(reqprice);
		int number = Integer.parseInt(reqnumber);
		double total = price*number;
		
		map.put("price", price);
		map.put("number", number);
		map.put("total", total);
		return map;
	}
	
	/**
	 * pay pal支付返回信息
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("/recharge")
	public void payPal(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, ParseException {
	    String itemNumber="";
	    Enumeration en = request.getParameterNames();
	    while (en.hasMoreElements()) {
	        String paramName = (String) en.nextElement();
	        String paramValue = request.getParameter(paramName);
	        if(paramName.equals("item_number")){
	            itemNumber=paramValue;
	        }
	    }
	    String id[]=itemNumber.split(",");
	    PrintWriter out=response.getWriter();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String sDate = sdf.format(new Date());
	    Date date = sdf.parse(sDate);
	    String str1 = request.getParameter("tx");
	    /*正式环境下
	    String str2 = "&at=sTvmKEM1YR2EmQXW3VyBrqYWiX-8_wr0Sj5w2DQ5uqGoakHYOCKcFsaAAU4";
	    */
	    String str2 = "&at=VmjfBuVl1vbSC6bMV7xvROqisIsrMpKftSx_bLbAnNr-UO2JsLnAR2wfzK8";
	    String str = "?tx=" + str1 + "&cmd=_notify-synch" + str2;
	    /*
	    String str = "?tx=" + str1 + "&cmd=_notify-validate" + str2;
	    */
	    /* 正式环境下
	    String payPalUrl = "https://www.paypal.com/cgi-bin/webscr";
	    */
	    String payPalUrl = "https://www.sandbox.paypal.com/cgi-bin/webscr";
	    payPalUrl = payPalUrl + str;
	    URL u = new URL(payPalUrl);
	    URLConnection uc = u.openConnection();
	    uc.setDoOutput(true);
	    uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    PrintWriter pw = new PrintWriter(uc.getOutputStream());
	    pw.println(str);
	    pw.close();
	    //接受PayPal对IPN回发的回复信息
	    BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
	    String line = "";
	    String txn_id = ""; //paypal的号码
	    String item_name = "";//本地订单号
	    String contact_phone = "";
	    int i = 0;
	    String res = "";
	    String msg = "";
	    while ((line = in.readLine()) != null) {
	        i = i + 1;
	        if (i == 1) {
	            res = line;
	        }
	        if (res.equals("SUCCESS")) {
	            if (line.indexOf("txn_id=") != -1) {
	                txn_id = line.replace("txn_id=", "");
	            } else if (line.indexOf("item_name=") != -1) {
	                item_name = line.replace("item_name=", "");
	            } else if (line.indexOf("contact_phone=") != -1) {
	                contact_phone = line.replace("contact_phone=", "");
	            }
	        }
	    }
	    if (!txn_id.equals("") && !item_name.equals("")) {
	       /* UserRecord userRecord=userRecordService.findById(Integer.parseInt(id[1]));
	        userRecord.setCommitDate(date);
	        userRecord.setHandler(id[2]);
	        userRecord.setState(0);
	        userRecordService.updateUserRecord(userRecord);*///修改数据库的字段信息
	        msg = "Pay for success! Please wait for delivery!  Your Order Number: " + txn_id + " !";
	    } else {
	        msg = "Sorry ! Your operating error! Please contact website administrator !!";
	    }
	    out.print("<script>alert('" + msg + "');location.href='" + request.getContextPath() + "/goto/back'</script>");//支付完毕返回 用户信息页 !
	}
	
}
