package com.zg.common.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
* @author gez
* @version 0.1
*/

public class GenerateKeyUtils {
	
	public static void main(String[] args) {
		
		String line = "6BCE61FB25DDAA79522A4CC03D010818D9A0937FD7CF9A0DE0CDE2C2FF2C6E16";
		System.out.println(line.length());
		BASE64Encoder bASE64Encoder = new BASE64Encoder();
		String baseString = bASE64Encoder.encode(line.getBytes());
		String zgshop_key = bASE64Encoder.encode("ZGSHOP_KEY".getBytes());
		System.out.println(zgshop_key);
		
		String compareString = "NkJDRTYxRkIyNUREQUE3OTUyMkE0Q0MwM0QwMTA4MThEOUEwOTM3RkQ3Q0Y5QTBERTBDREUyQzJG\r\nRjJDNkUxNg==";
		BASE64Decoder bASE64Decoder = new BASE64Decoder();
		try {
			String originalStr = new String(bASE64Decoder.decodeBuffer(compareString));
			System.out.println(originalStr.equals("6BCE61FB25DDAA79522A4CC03D010818D9A0937FD7CF9A0DE0CDE2C2FF2C6E16"));
			System.out.println(new String(bASE64Decoder.decodeBuffer(compareString)));
			System.out.println(baseString);
			System.out.println(StringUtils.substring(baseString, 8));
			if (!StringUtils.equals(StringUtils.substring(baseString, 8), "RkIyNUREQUE3OTUyMkE0Q0MwM0QwMTA4MThEOUEwOTM3RkQ3Q0Y5QTBERTBDREUyQzJG\r\nRjJDNkUxNg==")) {
				throw new ExceptionInInitializerError();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
