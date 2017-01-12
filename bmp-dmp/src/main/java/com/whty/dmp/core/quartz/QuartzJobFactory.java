package com.whty.dmp.core.quartz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.whty.dmp.core.quartz.vo.QuartzJobVo;

/**
 * 任务执行处
 * @author cjp
 *
 */
public class QuartzJobFactory implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzJobVo jobVo = (QuartzJobVo) context.getMergedJobDataMap().get(QuartzJobVo.JOBMAP_KEY);
		QuartzJobUtils.invokeMethod(jobVo);
	}

}
