package com.whty.dmp.core.quartz;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.StatefulJob;

import com.whty.dmp.core.quartz.vo.QuartzJobVo;


/**
 * 任务执行单个，不允许并行
 * use DisallowConcurrentExecution and/or PersistJobDataAfterExecution
 * 或者实现StatefulJob
 * @author cjp
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJobSingleFactory implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzJobVo jobVo = (QuartzJobVo) context.getMergedJobDataMap().get(QuartzJobVo.JOBMAP_KEY);
		QuartzJobUtils.invokeMethod(jobVo);
		
	}

}
