package com.whty.dmp.core.quartz;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.dmp.core.quartz.vo.QuartzJobVo;
import com.whty.dmp.core.spring.SpringBeanUtils;

/**
 * 采用反射调用任务
 * @author cjp
 *
 */
public class QuartzJobUtils {
	
	public static final Logger log = LoggerFactory.getLogger(QuartzJobUtils.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void invokeMethod(QuartzJobVo jobVo){
		Object object = null;
		Class clazz = null;
		if(StringUtils.isNotBlank(jobVo.getTargetObject())){
			object  = SpringBeanUtils.getBean(jobVo.getTargetObject());
		}else if(StringUtils.isNotBlank(jobVo.getBeanClass())){
			try {
				clazz = Class.forName(jobVo.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		if(object == null){
			log.error("任务名称=【"+jobVo.getJobName()+"】-----------未启动成功，请检查任务的参数配置！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(jobVo.getTargetMethod());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("任务名称=【"+jobVo.getJobName()+"】-----------未启动成功，请检查任务的方法配置！");
			return;
		}
		if(method != null){
			try {
				method.invoke(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("任务名称【"+jobVo.getJobName()+"】--{"+jobVo.getTargetObject()+"}-["+jobVo.getTargetMethod()+"]--------启动成功");
		
	}
}
