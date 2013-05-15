package com.zg.util;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

	public static List<String> splitString(String str,
			int length) {
		List<String> rt = new ArrayList<String>();
		for(int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if(endIndex <= str.length()) {
				rt.add(str.substring(i,i + length));
			} else {
				rt.add(str.substring(i, str.length() - 1));
			}
		}
		return rt;
	}
	
	

}
