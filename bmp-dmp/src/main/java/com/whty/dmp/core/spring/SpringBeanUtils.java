package com.whty.dmp.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 实现Spring后置处理器(BeanFactoryPostProcessor可以修改BEAN的配置信息)
 * @author cjp 2016年9月11日
 */
@Service
@Lazy(false)
public class SpringBeanUtils implements BeanFactoryPostProcessor{
	
	private static ConfigurableListableBeanFactory beanFactory; // Spring应用上下文环境

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringBeanUtils.beanFactory = beanFactory;
	}
	
	/**
	 * 根据名称获取Bean
	 * @param clazz
	 * @throws BeansException
	 * @date 2016年9月11日
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) beanFactory.getBean(name);
	}
	
	/**
	 * 根据Class 获取Bean
	 * @param clazz
	 * @throws BeansException
	 * @date 2016年9月11日
	 */
	public static <T> T getBean(Class<T> clazz) throws BeansException{
		T result = beanFactory.getBean(clazz);
		return result;
	}
	
	/**
	 * 是否包含bean
	 * @param name
	 * @return
	 * @date 2016年9月11日
	 */
	public static boolean containsBean(String name){
		return beanFactory.containsBean(name);
	}
	
	/**
	 * 是否是单例模式
	 * @param name
	 * @return
	 * @date 2016年9月11日
	 */
	public static boolean isSingleton(String name){
		return beanFactory.isSingleton(name);
	}
	
	
}
