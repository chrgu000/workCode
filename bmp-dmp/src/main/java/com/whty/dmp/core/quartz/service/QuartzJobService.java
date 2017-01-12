package com.whty.dmp.core.quartz.service;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.whty.dmp.core.base.constants.BaseConstants;
import com.whty.dmp.core.quartz.QuartzJobFactory;
import com.whty.dmp.core.quartz.QuartzJobSingleFactory;
import com.whty.dmp.core.quartz.dao.QuartzJobDao;
import com.whty.dmp.core.quartz.vo.QuartzJobVo;
import com.whty.dmp.utils.IdGen;

/**
 * 定时任务管理器，实际采用SchedulerFactoryBean
 * 
 * @author cjp
 * @PostConstruct 实现初始化之前进行的操作
 * @PreDestroy 销毁bean之前进行的操作
 */
@Service
public class QuartzJobService {

	private static final Logger log = LoggerFactory.getLogger(QuartzJobService.class);

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private QuartzJobDao quartzJobDao;
	
	/**
	 * 初始化Bean时，启动Quartz任务
	 * @throws SchedulerException
	 */
	@PostConstruct
	public void init() throws SchedulerException {
		log.info("初始化加载Quartz定时任务,扫描表：config_quartz_job的状态正常的所有数据");
		QuartzJobVo queryBean = new QuartzJobVo();
		queryBean.setDelFlag(BaseConstants.DEL_FLAG_NORMAL);
		List<QuartzJobVo> jobVoList = quartzJobDao.selectList(queryBean);
		if (jobVoList != null && jobVoList.size() > 0) {
			for (QuartzJobVo jobVo : jobVoList) {
				addJob(jobVo);
			}
		}
	}
	
	/**
	 * 查询单个
	 * @param queryBean
	 * @return
	 */
	public QuartzJobVo selectOne(QuartzJobVo queryBean){
		QuartzJobVo jobVo = quartzJobDao.selectOne(queryBean);
		return jobVo;
	}
	
	/**
	 * 查询列表
	 * @param queryBean
	 * @return
	 */
	public List<QuartzJobVo> selectList(QuartzJobVo queryBean){
		List<QuartzJobVo> jobList = quartzJobDao.selectList(queryBean);
		return jobList;
	}
	
	/**
	 * 保存单个
	 * @param jobVo
	 * @return
	 * @throws SchedulerException
	 */
	public int insertJob(QuartzJobVo jobVo) throws SchedulerException {
		Date createTime = new Date();
		jobVo.setId(IdGen.uuid());
		jobVo.setCreateTime(createTime);
		int size = quartzJobDao.insert(jobVo);
		addJob(jobVo);
		return size;
	}
	
	/**
	 * 删除单个
	 * @param jobVo
	 * @return
	 */
	public int deleteJob(QuartzJobVo queryBean) {
		QuartzJobVo jobVo = quartzJobDao.selectOne(queryBean);
		int size = quartzJobDao.delete(jobVo);
		removeJob(jobVo);
		return size;
	}
	
	public int updateJob(QuartzJobVo queryBean){
		queryBean.setUpdateTime(new Date());
		int size = quartzJobDao.update(queryBean);
		return size;
	}
	
	/**
	 * 停止一个job
	 * @param queryBean
	 * @return
	 */
	public int stopJob(QuartzJobVo queryBean){
		QuartzJobVo jobVo = quartzJobDao.selectOne(queryBean);
		jobVo.setUpdateTime(new Date());
		jobVo.setJobStatus(QuartzJobVo.STATUS_STOP);
		int size = quartzJobDao.update(jobVo);
		removeJob(jobVo);
		return size;
	}
	
	/**
	 * 开启一个job
	 * @param queryBean
	 * @return
	 */
	public int startJob(QuartzJobVo queryBean){
		QuartzJobVo jobVo = quartzJobDao.selectOne(queryBean);
		jobVo.setUpdateTime(new Date());
		jobVo.setJobStatus(QuartzJobVo.STATUS_RUNNING);
		int size = quartzJobDao.update(jobVo);
		resumeJob(jobVo);
		return size;
	}
	
	/**
	 * 添加一个任务
	 * @param jobVo
	 * @throws SchedulerException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addJob(QuartzJobVo jobVo) throws SchedulerException {
		if(jobVo == null || !QuartzJobVo.STATUS_RUNNING.equals(jobVo.getJobStatus())){
			return;
		}
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		log.debug(scheduler+"........ add start");
		TriggerKey triggerKey = TriggerKey.triggerKey(jobVo.getJobName(), jobVo.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if(null == trigger){
			Class clazz = QuartzJobVo.CONCURRENT_TRUE.equals(jobVo.getConcurrent())?QuartzJobFactory.class : QuartzJobSingleFactory.class;
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobVo.getJobName(),jobVo.getJobGroup()).withDescription(jobVo.getDescription()).build();
			jobDetail.getJobDataMap().put(QuartzJobVo.JOBMAP_KEY, jobVo);
			//获取trigger触发器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobVo.getCronExpression());
			scheduleBuilder.inTimeZone(TimeZone.getTimeZone("GMT"));//东八区
			trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).withDescription(jobVo.getDescription()).build();
			//把作业和触发器注册到任务调度中  
			scheduler.scheduleJob(jobDetail,trigger);
			//启动调度--有Spring来进行处理
//			scheduler.start();
		}
//		else{ //Trigger 已经存在，那么更新相应的定时设置
//			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobVo.getCronExpression());
//			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
//			scheduler.rescheduleJob(triggerKey, trigger);
//		}		
	}
	
	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(QuartzJobVo jobVo){
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobVo.getJobName(), jobVo.getJobGroup());
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(QuartzJobVo jobVo){
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobVo.getJobName(), jobVo.getJobGroup());
		try {
			if(scheduler.checkExists(jobKey)){
				scheduler.resumeJob(jobKey);
			}else{
				addJob(jobVo);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除一个job
	 * 
	 * @throws SchedulerException
	 */
	public void removeJob(QuartzJobVo jobVo){
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobVo.getJobName(), jobVo.getJobGroup());
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(QuartzJobVo jobVo) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobVo.getJobName(), jobVo.getJobGroup());
		scheduler.triggerJob(jobKey);
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
