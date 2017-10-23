package com.gy.util;

/**
 * @author chencongye
 * @version 0.1.0
 * @function 实现短信验证码的六个随机数字
 */
public class RandomCode {
	//这个是主函数
	public static void main(String[] args) {
		RandomCode.rand();
		RandomCode.getRandNum();
	}
	
	//这个是获取到随机数字
	public static int getRandNum() {
		int min = 0;
		int max = 999999;
	    int randNum = min + (int)(Math.random() * ((max - min) + 1));
	    return randNum;
	}

	public static int getRandNum(int min,int max) {
	    int randNum = min + (int)(Math.random() * ((max - min) + 1));
	    return randNum;
	}
	
	//测试生成的随机数字
	public static void rand(){
	    System.out.println(getRandNum());
	}
}
