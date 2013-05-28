package com.zg.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import com.zg.util.EncryptUtil;

import sun.misc.BASE64Encoder;


public class Key {
	
	public static String readKeyFile(String keyFilePath) {
		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			InputStream is = ClassLoadUtil.getResourceAsStream(keyFilePath);
			bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line;
			for (int i = 0; i < 4; i ++) {
				line = bufferedReader.readLine();
				if (line == null) {
					break;
				}
				if (i == 0) {
					BASE64Encoder bASE64Encoder = new BASE64Encoder();
					String baseString = bASE64Encoder.encode(line.getBytes());
					if (!StringUtils.equals(StringUtils.substring(baseString, 8), "RkIyNUREQUE3OTUyMkE0Q0MwM0QwMTA4MThEOUEwOTM3RkQ3Q0Y5QTBERTBDREUyQzJG\r\nRjJDNkUxNg==")) {
						throw new ExceptionInInitializerError();
					}
				}
				System.out.println(line);
				stringBuffer.append(line);
			}
			if (stringBuffer.length() != 64) {
				throw new ExceptionInInitializerError();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(bufferedReader != null) {
				try{
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return stringBuffer.toString();

	}
	
	public static void writeKeyFile(String keyFilePath) {
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(keyFilePath); 
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			java.security.Key key = keyGenerator.generateKey();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(key);
			objectOutputStream.close();
		} catch (Exception e1) {
			e1.printStackTrace();
        }
	}
	
	public static byte[] encrypt(String content, String keyFilePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(keyFilePath);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			java.security.Key key = (java.security.Key) objectInputStream.readObject();
			System.out.println(key.toString());
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherText = cipher.doFinal(content.getBytes("UTF-8"));
			return cipherText;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String decrypt(byte[] content, String keyFilePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(keyFilePath);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			java.security.Key key = (java.security.Key) objectInputStream.readObject();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] cipherText = cipher.doFinal(content);
			return new String(cipherText);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		
		String s = readKeyFile("keyfile");
		System.out.println(EncryptUtil.dencrypt(s));
	}

}
