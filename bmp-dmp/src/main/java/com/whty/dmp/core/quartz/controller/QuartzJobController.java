package com.whty.dmp.core.quartz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whty.dmp.core.base.constants.BaseConstants;
import com.whty.dmp.core.mybatis.Page;
import com.whty.dmp.core.quartz.service.QuartzJobService;
import com.whty.dmp.core.quartz.vo.QuartzJobVo;

@Controller
public class QuartzJobController {
	
	@Autowired
	private QuartzJobService quartzJobService;
	
	@RequestMapping(value="/core/jobList")
	public Object jobList(QuartzJobVo quartzJobVo,Page page,Model model){
		if(page.getPageSize() == -1){
			page.setPageSize(10);
		}
		quartzJobVo.setPage(page);
		List<QuartzJobVo> jobList = quartzJobService.selectList(quartzJobVo);
		model.addAttribute("quartzJobVo",quartzJobVo);
		model.addAttribute("jobList", jobList);
		model.addAttribute("page",page);
		return "/core/jobList";
	}
	
	@RequestMapping(value="/core/jobAdd")
	public Object jobAdd(QuartzJobVo quartzJobVo){
		return "/core/jobAdd";
	}
	
	@RequestMapping(value="/core/jobEdit")
	public Object jobEdit(QuartzJobVo queryBean,Model model){
		QuartzJobVo quartzJobVo = quartzJobService.selectOne(queryBean);
		model.addAttribute("quartzJobVo",quartzJobVo);
		return "/core/jobEdit";
	}
	
	@RequestMapping(value="/core/jobSave",method=RequestMethod.POST)
	@ResponseBody
	public Object jobSave(@Valid QuartzJobVo quartzJobVo,Errors errors) throws SchedulerException{
		Map<String,Object> result = new HashMap<String,Object>();
		if(errors.hasErrors()){
			result.put("result", false);
			result.put("msg", errors.getAllErrors().get(0).toString());
		}else{
			if(StringUtils.isEmpty(quartzJobVo.getId())){
				quartzJobService.insertJob(quartzJobVo);
			}else{
				quartzJobService.updateJob(quartzJobVo);
			}
			result.put("result", true);
		}
		return result;
	}
	
	@RequestMapping(value="/core/jobDelete",method=RequestMethod.POST)
	@ResponseBody
	public Object jobDelete(QuartzJobVo queryBean){
		quartzJobService.deleteJob(queryBean);
		return BaseConstants.RESPONSE_SUCCESS;
	}
	
	/**
	 * 暂停工作
	 * @param jobVo
	 * @throws SchedulerException
	 */
	@RequestMapping(value="/core/stopJob")
	@ResponseBody
	public Object stopJob(QuartzJobVo queryBean) throws SchedulerException{
		return quartzJobService.stopJob(queryBean);
	}
	

	/**
	 * 恢复工作
	 * @param queryBean
	 * @throws SchedulerException
	 */
	@RequestMapping(value="/core/startJob")
	@ResponseBody
	public Object resumeJob(QuartzJobVo queryBean) throws SchedulerException{
		return quartzJobService.startJob(queryBean);
	}
}
