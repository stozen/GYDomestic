package com.gy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import com.gy.model.Game;
import com.gy.model.User;
import com.gy.config.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@SuppressWarnings("unused")
@Component
public class JwtUtil {
	
    private static String profiles="develop";
	
	/**
	 * 由字符串生成加密key
	 * @return
	 */
	public static SecretKey generalKey(){
		String stringKey = profiles+Constant.JWT_SECRET;
		byte[] encodedKey = Base64.decodeBase64(stringKey);
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}
	
	public JwtUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static int compareDate(Date DATE1, Date DATE2) {
        
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            if (DATE1.getTime() > DATE2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (DATE1.getTime() < DATE2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

	/**
	 * 创建jwt
	 * @param id
	 * @param subject
	 * @param ttlMillisx
	 * @return
	 * @throws Exception
	 */
	public static String createJWT(String id, String subject,int ttlMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder()
			.setId(id)
			.setIssuedAt(now)
			.setSubject(subject)
		    .signWith(signatureAlgorithm, key);
		if (ttlMillis >= 0) {
		    /*long expMillis = nowMillis + ttlMillis;*/
		    /*Date exp = new Date(expMillis);*/
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
			String date = dateFormat.format(now); 
			String exp = JwtUtil.addDateMinut(date,ttlMillis);
			Date expirationDate = dateFormat.parse(exp);
		    builder.setExpiration(expirationDate);
		}
		return builder.compact();
	}
	
	/**
	 * 解密jwt
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception,ExpiredJwtException{
		Claims claims = null;
		try {
			SecretKey key = generalKey();
			claims = Jwts.parser()         
			   .setSigningKey(key)
			   .parseClaimsJws(jwt).getBody();
		} catch (SignatureException | MalformedJwtException e) {
			e.printStackTrace();
        } catch (ExpiredJwtException e) {
            // TODO: handle exception
            // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
        	System.err.println("日期已经过期了");
        }
		
		return claims;
	}
	
	public static String addDateMinut(String day, int x)//返回的是字符串型的时间，输入的
	//是String day, int x
	 {   
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制  
	//引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
	//量day格式一致
	        Date date = null;   
	        try {   
	            date = format.parse(day);   
	        } catch (Exception ex) {   
	            ex.printStackTrace();   
	        }   
	        if (date == null)   
	            return "";   
	        System.out.println("front:" + format.format(date)); //显示输入的日期  
	        Calendar cal = Calendar.getInstance();   
	        cal.setTime(date);   
	        cal.add(Calendar.MINUTE, x);// 24小时制   
	        date = cal.getTime();   
	        System.out.println("after:" + format.format(date));  //显示更新后的日期 
	        cal = null;   
	        return format.format(date);   
	    }  
	
	/**
	 * 生成subject信息
	 * @param user
	 * @return
	 */
	public static String generalSubject(User user,Game game){
		JSONObject jo = new JSONObject();
		jo.put("userid", user.getUserid());
		jo.put("username", user.getUsername());
		jo.put("password", user.getPassword());
		jo.put("gameid", game.getGameid());
		return jo.toJSONString();
	}
	
	public static void main(String[] args) throws Exception,ExpiredJwtException {
		User user = new User();
		user.setUserid(1);
		user.setUsername("chencongye");
		user.setPassword("12345678");
		Game game = new Game();
		game.setGamename("王者荣耀");
		game.setGameid(1);
		game.setGamepackage("com.king.author");
		JwtUtil jwtUtil = new JwtUtil();
		String subject = jwtUtil.generalSubject(user, game);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
		Date date = dateFormat.parse("2017-10-23 11:50:30"); 
		long ttlMillis = date.getTime();
		long mills = 2*60*60*1000;
		ttlMillis+=mills;
		String token = jwtUtil.createJWT(String.valueOf(RandomCode.getRandNum(1, 9999)), subject, 60);
		System.err.println("生成的Token为："+token);
		
		Claims claims = jwtUtil.parseJWT(token);
		System.err.println("获得主题："+claims.getSubject());
		Date expirateDate = claims.getExpiration();
		System.err.println("过期时间:"+expirateDate);
		System.err.println("过期日期："+dateFormat.format(expirateDate));
		claims.get("subject");
		
		
		 int i= compareDate(new Date(), claims.getExpiration());
	     System.out.println("i=="+i);
	     jwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0NjY5IiwiaWF0IjoxNTA4NzM2MjI5LCJzdWIiOiJ7XCJnYW1laWRcIjo3LFwicGFzc3dvcmRcIjpcIjEyMzQ1Njc4XCIsXCJ1c2VyaWRcIjowLFwidXNlcm5hbWVcIjpcIjE1OTAwNzg1MzgzXCJ9IiwiZXhwIjoxNTA4NzM2MzQ5fQ.aENiNQzWM1A3rEUOk_8rs7_qVku1l4QqFj_avoZbTrw");
		
		jwtUtil.addDateMinut("2013-08-29 08:16:36",1);
	}
}
