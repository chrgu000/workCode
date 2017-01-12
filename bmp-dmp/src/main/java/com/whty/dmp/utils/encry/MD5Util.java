package com.whty.dmp.utils.encry;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/**
	 * MD5转换
	 * 
	 * @param plainText
	 * 
	 * @return MD5字符串
	 */
	public static String toMD5(String plainText) {

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("MD5 error:", e);
		}
		messageDigest.update(plainText.getBytes());
		byte by[] = messageDigest.digest();

		StringBuffer buf = new StringBuffer();
		int val;
		for (int i = 0; i < by.length; i++) {
			val = by[i];
			if (val < 0) {
				val += 256;
			} else if (val < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(val));
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * @Title: Md5Lenth16
	 * @Description: 取长度16位
	 * @Author: Administrator
	 * @Since: 2012-3-20上午10:16:21
	 * @param plainText
	 * @return
	 */
	public static String Md5Lenth16(String plainText) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 error:", e);
		}
		md.update(plainText.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().substring(8, 24);// 16位的加密
	}

	/**
	 * 对明文进行加密(传的是MD5大写)
	 * 
	 * @param password
	 * @return
	 */
	public static String encodePassword(String password) {
		EncryptUtils eu = EncryptUtils.getInstance();
		String encodepassword = eu.encode(password, EncryptUtils.USER_SERCRETKEY);
		return encodepassword;
	}

	public static void main(String[] s) {

		String str = MD5Util.toMD5("123456");
//		System.out.println(str);
		EncryptUtils eu = EncryptUtils.getInstance();
		String password = eu.encode(str, EncryptUtils.USER_SERCRETKEY);
		System.out.println("3A加密密文： "+password);
		String dbpassword = "aKvpxU1LekEffCeVXt77rZQ0Yiclk3jz4Tvl+iFlmM0CJmh55Hs+odpV/+UEAiGrDgwNqP/OQVm76aQ";
		System.out.println(password.equals(dbpassword));

	}
}
