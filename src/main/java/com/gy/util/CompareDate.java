package com.gy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2011-9-20
 * @introduce 这是比较两个日期大小的类
 */

public class CompareDate {
	
	public static void main(String args[]) {
	       /*int i= compare_date("1995-11-12 15:21", "1999-12-11 09:59");
	       System.out.println("i=="+i);*/
	    }

	    @SuppressWarnings("unused")
		public static int compare_date(Date DATE1, Date DATE2) {
	        
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	        try {
	            /*Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);*/
	            if (DATE1.getTime() > DATE2.getTime()) {
	                System.out.println("dt1 在dt2前");
	                return 1;
	            } else if (DATE1.getTime() < DATE1.getTime()) {
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
}
