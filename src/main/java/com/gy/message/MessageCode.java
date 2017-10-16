package com.gy.message;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-10-16
 * @introduce 这是生成随机的六位验证码
 */
public class MessageCode {

	//这个是主函数
	public static void main(String[] args) {
		MessageCode.rand();
		MessageCode.getRandNum();
	}
	
	//这个是获取到随机数字
	public static int getRandNum() {
		int min = 0;
		int max = 999999;
	    int randNum = min + (int)(Math.random() * ((max - min) + 1));
	    return randNum;
	}

	//测试生成的随机数字
	public static void rand(){
	    System.out.println(getRandNum());
	}
		
}
