package com.whty.dmp.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * <b>封装各种格式的编码工具类</b></br>
 * 1.Commons-codec的hex/base64</br>
 * 
 * @author cjp
 * @date 2016年7月27日
 */
public class EncodesUtils {
	
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	
	/**
	 * Hex编码
	 * @param input
	 */
	public static String encodeHex(byte[] input){
		return new String(Hex.encodeHex(input));
	}
	
	/**
	 * Hex解码
	 * @param input
	 */
	public static byte[] decodeHex(String input){
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw Exceptions.unchecked(e);
		}
	}
	
	/**
	 * Base64编码
	 * @param input
	 */
	public static String encodeBase64(byte[] input){
		return new String(Base64.encodeBase64(input));
	}
	
	/**
	 * Base64编码(UTF-8)
	 * @param input
	 */
	public static String encodeBase64(String input){
		try {
			return new String(Base64.encodeBase64(input.getBytes(CharEncoding.UTF_8)));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	/**
	 * Base64解码
	 * @param input
	 */
	public static byte[] decodeBase64(String input){
		return Base64.decodeBase64(input.getBytes());
	}
	
	/**
	 * Base64解码(UTF-8)
	 * @param input
	 */
	public static String decodeBase64Str(String input){
		try {
			return new String(Base64.decodeBase64(input.getBytes(CharEncoding.UTF_8)));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	/**
	 * Base62编码。
	 */
	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}
	
}
