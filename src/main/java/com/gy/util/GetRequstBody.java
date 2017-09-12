package com.gy.util;

import java.io.BufferedReader;
import java.io.IOException;

public class GetRequstBody {
	public static String getBodyString(BufferedReader br) {
		// TODO Auto-generated method stub
		String inputLine;
	       String str = "";
	     try {
	       while ((inputLine = br.readLine()) != null) {
	        str += inputLine;
	       }
	       br.close();
	     } catch (IOException e) {
	       System.out.println("IOException: " + e);
	     }
	     return str;
	}
}
