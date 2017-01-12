package com.whty.dmp.core.quartz.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whty.dmp.core.base.vo.DataEntity;
import com.whty.dmp.core.mybatis.Page;

/**
 * 定时任务配置表
 * config_quartz_job
 * @author cjp
 *
 */
public class QuartzJobVo extends DataEntity{

	private static final long serialVersionUID = 1L;
	
	public static final String STATUS_RUNNING = "0"; //在运行状态
	public static final String STATUS_STOP = "1"; //不在运行状态
	public static final String CONCURRENT_FALSE = "0"; //不允许并行
	public static final String CONCURRENT_TRUE = "1"; //允许并行
	
	/**
	 * 约定jobMap的key值
	 */
	public static final String JOBMAP_KEY = "quartzJob";
	
	
	private String jobName; //任务名称
	private String jobGroup; //任务组
	private String jobStatus; //任务状态
	private String cronExpression; //任务时间
	private String targetObject; //目标对象
	private String targetMethod; //目标方法
	private String beanClass; //对象class
	private String description;//描述
	private String concurrent;//是否并行运行
	private Page page;
	
	@JsonIgnore
	public Page getPage() {
		if (page == null) {
			page = new Page();
		}
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}
	public String getTargetMethod() {
		return targetMethod;
	}
	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}
	public String getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getConcurrent() {
		return concurrent;
	}
	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}
	
	
	
}
