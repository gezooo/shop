package com.zg.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CommonUtils {
	
	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}
	
	/**
	 * 随机获取字符串
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
				'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
		}
		return stringBuffer.toString();
	}
	
	public static String displayMessage(String Message, List<String> params) {

        String methodName = "";
        Throwable t = new Throwable();
        StackTraceElement[] elements = t.getStackTrace();
        if (elements.length >= 1) {
            methodName = elements[1].getMethodName();

        } else {
            methodName = "";
        }
        String tmp = methodName + ": ";
        String tmpMessage = Message;
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {

                int pos = tmpMessage.indexOf("%");
                if (pos >= 0) {

                    tmp = tmp + tmpMessage.substring(0, pos);
                    tmp = tmp + params.get(i);
                    tmpMessage = tmpMessage.substring(pos + 1);

                } else {
                    tmp = tmp + tmpMessage;
                    break;
                }
            }
        } else {
            tmp = tmp + tmpMessage;
        }

        return tmp;
    }
	
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
