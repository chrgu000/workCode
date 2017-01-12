package com.whty.dmp.utils.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * properties文件读取工具类
 * @author cjp 2016年6月23日
 */
public class PropertiesReader {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
	
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	private final Properties properties;
	
	/**
	 * 构造方式给对象properties赋值
	 * @param filePaths
	 */
	public PropertiesReader(String[] filePaths){
		properties = readProperties(filePaths);
	}
	
	
	/**
	 * 读取文件
	 * @date 2016年6月23日
	 */
	private Properties readProperties(String[] filePaths){
		Properties props = new Properties();
		for(String filePath : filePaths){
			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(filePath);
				is = resource.getInputStream();
				props.load(is);
			} catch (IOException e) {
				logger.error("Could not load properties from path:" + filePath + ", " + e.getMessage());
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return props;
	}
	
	/**
	 * 取出Property，但以System的Property优先,取不到返回空字符串.
	 */
	public String getValue(String key) {
		String systemProperty = System.getProperty(key);
		if (systemProperty != null) {
			return systemProperty;
		}
		if (properties.containsKey(key)) {
	        return properties.getProperty(key);
	    }
	    return null;
	}

	public Properties getProperties() {
		return properties;
	}
}
