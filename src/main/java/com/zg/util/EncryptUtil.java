package com.zg.util;

import java.net.InetAddress;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import java.util.Locale;
import java.util.ResourceBundle;

public class EncryptUtil {

	private static final String Algorithm = "DESede";

	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {

			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);

		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {

			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);

		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	private static byte[] hexStringToByteArray(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}

	public static void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating "
					+ parameterName + " value error");
		}
	}

	public static String MsgHandler(String paramString1, String paramString2) {
		ResourceBundle rb = null;
		boolean initialized = false;
		Object localObject;
		if (!(initialized)) {
			localObject = null;
			localObject = Locale.getDefault();

			String str = ((Locale) localObject).getLanguage();
			if (!(str.equals("en")))
				try {
					rb = ResourceBundle.getBundle("activatorUtilsRB",
							(Locale) localObject);
				} catch (Exception localException2) {
					rb = null;
				}
			else
				rb = null;
			initialized = true;
		}
		if (rb == null)
			return paramString2;

		try {
			localObject = rb.getString(paramString1);
		} catch (Exception localException1) {
			localObject = paramString2;
		}
		return ((String) localObject);
	}

	private static void printUsage() {
		System.out.println();

		System.out.println(MsgHandler("16",
				"Usage: generateEncryptedPassword[.bat] -password <password>"));
		System.out.println();
		System.out.println(MsgHandler("145",
				"This script allows you to generate an encrypted password"));
		System.out.println();
		System.out.println("The options include:");
		System.out
				.println(MsgHandler("146",
						"-password <Mcafee Partner Password that has to be encrypted>"));
		System.out
				.println(MsgHandler("147",
						"Sample usage: generateEncryptedPassword -password ovsaPassword"));
	}

	public static String dencrypt(String propPassword) {
		String address = EncryptUtil.getAddress().replace(":", "-");
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,
				0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
				0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,
				(byte) 0xE2 };

		byte[] dencodedPsw = hexStringToByteArray(propPassword);
		byte[] dencoded = decryptMode(keyBytes, dencodedPsw);
		String password = new String(dencoded).replace(address, "");
		return password;
	}
	
	public static String getAddress() {
		String IP_addr = null;
		try {
			InetAddress localMachine = InetAddress.getLocalHost();
			IP_addr = localMachine.getHostAddress().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return IP_addr;
	}

	

	public static void main(String[] paramArrayOfString) {
		if (paramArrayOfString.length != 2) {
			printUsage();
			return;
		}

		String str = paramArrayOfString[0];

		if (!(str.equals("-password"))) {
			printUsage();
			return;
		}

		String address = EncryptUtil.getAddress().replace(":", "-");

		try {

			String encryptStr = paramArrayOfString[1];
			String encrypt = encryptStr + address;
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88,
					0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB,
					(byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98,
					0x30, 0x40, 0x36, (byte) 0xE2 };

			byte[] encoded = encryptMode(keyBytes, encrypt.getBytes());
			String encodedPsw = byteArrayToHexString(encoded);
			System.out.printf("GenerateEncryptedPassword is: " + encodedPsw);
			System.out.println();
			// writeProperties(fileName, itemName, encodedPsw);
		} catch (Exception e) {
			System.out.println();
		}

	}

}