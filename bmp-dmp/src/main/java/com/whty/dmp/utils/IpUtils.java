package com.whty.dmp.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取本机Ip
 * @author cjp
 * @date 2016年9月30日
 */
public class IpUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);
	
	/**
	 * 获取本机ip
	 * @return
	 */
	public static String getIp() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress address = null;
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				Enumeration<InetAddress> addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements()) {
					address = addresses.nextElement();
					if (!address.isLoopbackAddress() && address.getHostAddress().indexOf(":") == -1) {
						return address.getHostAddress();
					}
				}
			}
			logger.info("xxl job getHostAddress fail");
			return null;
		} catch (Throwable t) {
			logger.error("xxl job getHostAddress error, {}", t);
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getIp());
	}
}
