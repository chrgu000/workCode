package com.whty.dmp.utils.encry;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;


/**
 * AAM字符串加密以及解密工具类
 */
public class EncryptUtils {

	public static String UC_KEY = "12345";
	
	public final static String USER_SERCRETKEY = "user_sercretkey";

	private static EncryptUtils instance = new EncryptUtils();

	private EncryptUtils() {
	}

	public static EncryptUtils getInstance() {
		return instance;
	}

	private String md5(String input) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return byte2hex(md.digest(input.getBytes()));
	}

	private String base64_decode(String input) {
		try {
			return new String(Base64.decode(input.toCharArray()), "iso-8859-1");
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private String base64_encode(String input) {
		try {
			return new String(Base64.encode(input.getBytes("iso-8859-1")));
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString();
	}

	private String substr(String input, int begin, int length) {
		return input.substring(begin, begin + length);
	}

	private String substr(String input, int begin) {
		if (begin > 0) {
			return input.substring(begin);
		} else {
			return input.substring(input.length() + begin);
		}
	}

	private String sprintf(String format, long input) {
		String temp = "0000000000" + input;
		return temp.substring(temp.length() - 10);
	}

	/**
	 * 加密
	 * 
	 * @param string
	 * @param key
	 *            :密钥
	 * @return
	 */
	public String encode(String string, String key) {
		return uc_authcode(string, "ENCODE", key);
	}

	/**
	 * 解密
	 * 
	 * @param string
	 * @param key
	 *            :密钥
	 * @return
	 */
	public String decode(String string, String key) {
		return uc_authcode(string, "DECODE", key);
	}

	private String uc_authcode(String string, String operation, String key) {
		return uc_authcode(string, operation, key, 0);
	}

	/**
	 * 字符串加密以及解密函数
	 * 
	 * @param string
	 *            string 原文或者密文
	 * @param string
	 *            operation 操作(ENCODE | DECODE), 默认为 DECODE
	 * @param string
	 *            key 密钥
	 * @param int expiry 密文有效期, 加密时候有效， 单位 秒，0 为永久有效
	 * @return string 处理后的 原文或者 经过 base64_encode 处理后的密文
	 * 
	 * @example
	 * 
	 *          a = authcode('abc', 'ENCODE', 'key'); b = authcode( a, 'DECODE',
	 *          'key'); // b(abc)
	 * 
	 *          a = authcode('abc', 'ENCODE', 'key', 3600); b = authcode('abc',
	 *          'DECODE', 'key'); // 在一个小时内， b(abc)，否则 b 为空
	 */
	private String uc_authcode(String string, String operation, String key, int expiry) {

		// ckey_length 随机密钥长度 取值 0-32;
		// ckey_length 加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，加密结果也会每次不同，增大破解难度。
		// ckey_length 取值越大，密文变动规律越大，密文变化 = 16 的 ckey_length 次方
		// ckey_length 当此值为 0 时，则不产生随机密钥
		int ckey_length = 1;

		key = md5(key != null ? key : UC_KEY);
		String keya = md5(substr(key, 0, 16));
		String keyb = md5(substr(key, 16, 16));
		// String keyc = ckey_length > 0? ( operation.equals("DECODE") ? substr(
		// string, 0, ckey_length): substr(md5(microtime()), - ckey_length)) :
		// "";
		String keyc = "a";

		String cryptkey = keya + md5(keya + keyc);
		int key_length = cryptkey.length();

		string = operation.equals("DECODE") ? base64_decode(substr(string, ckey_length)) : sprintf("%010d", expiry > 0 ? expiry : 0) + substr(md5(string + keyb), 0, 16) + string;
		int string_length = string.length();

		StringBuffer result1 = new StringBuffer();

		int[] box = new int[256];
		for (int i = 0; i < 256; i++) {
			box[i] = i;
		}

		int[] rndkey = new int[256];
		for (int i = 0; i <= 255; i++) {
			rndkey[i] = (int) cryptkey.charAt(i % key_length);
		}

		int j = 0;
		for (int i = 0; i < 256; i++) {
			j = (j + box[i] + rndkey[i]) % 256;
			int tmp = box[i];
			box[i] = box[j];
			box[j] = tmp;
		}

		j = 0;
		int a = 0;
		for (int i = 0; i < string_length; i++) {
			a = (a + 1) % 256;
			j = (j + box[a]) % 256;
			int tmp = box[a];
			box[a] = box[j];
			box[j] = tmp;

			result1.append((char) (((int) string.charAt(i)) ^ (box[(box[a] + box[j]) % 256])));

		}

		if (operation.equals("DECODE")) {
			String result = result1.substring(0, result1.length());
			if ((Integer.parseInt(substr(result.toString(), 0, 10)) == 0 || Long.parseLong(substr(result.toString(), 0, 10)) > 0)
					&& substr(result.toString(), 10, 16).equals(substr(md5(substr(result.toString(), 26) + keyb), 0, 16))) {
				return substr(result.toString(), 26);
			} else {
				return "";
			}
		} else {
			return keyc + base64_encode(result1.toString()).replaceAll("=", "");
		}
	}

	public static String decode(String str) {
		return StringUtils.isEmpty(str) ? str : instance.decode(str, USER_SERCRETKEY);
	}
}
