package com.whty.dmp.utils;

import java.security.SecureRandom;
import java.util.UUID;


/**
 * 生成各种唯一性ID算法 工具类
 * @author cjp
 * @date 2016年7月27日
 */
public class IdGen{

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装jdk自带uuid算法
	 */
	public static String uuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String randomBase62(int length){
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return EncodesUtils.encodeBase62(randomBytes);
	}
	
	/**
	 * 封装SecureRandom随机生成long
	 * @return
	 */
	public static long randomLong(){
		return Math.abs(random.nextLong());
	}
	
	public static void main(String[] args) {
		System.out.println("uuid: "+uuid());
		System.out.println("randomBase62: "+randomBase62(32));
		System.out.println("randomLong: "+randomLong());
	}
}
