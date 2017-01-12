package com.whty.dmp.modules.dataExchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whty.dmp.core.mybatis.Page;
import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.modules.dataExchange.service.DataLogService;

/**
 * 数据日志控制器
 * @author cjp
 * @date 2016年9月23日
 */
@Controller
public class DataLogController {
	
	@Autowired
	private DataLogService dataLogService;
	
	@RequestMapping(value="/dataExchange/publishLogList")
	public Object publishLogList(DataLogVo queryBean,Page page,Model model){
		if(page == null || page.getPageSize() == -1){
			page.setPageSize(10);
		}
		queryBean.setPage(page);
		List<DataLogVo> dataLogList = dataLogService.selectPublishLog(queryBean);
		model.addAttribute("queryBean",queryBean);
		model.addAttribute("dataLogList", dataLogList);
		model.addAttribute("page",page);
		return "/modules/dataExchange/dataLog/publishLogList";
	} 
	
	@RequestMapping(value="/dataExchange/subscirbeLogList")
	public Object subscirbeLogList(DataLogVo queryBean,Page page,Model model){
		if(page == null || page.getPageSize() == -1){
			page.setPageSize(10);
		}
		queryBean.setPage(page);
		List<DataLogVo> dataLogList = dataLogService.selectSubscribeLog(queryBean);
		model.addAttribute("queryBean",queryBean);
		model.addAttribute("dataLogList", dataLogList);
		model.addAttribute("page",page);
		return "/modules/dataExchange/dataLog/subscirbeLogList";
	} 
	
	@RequestMapping(value="/dataExchange/doWatchDetail")
	public String doWatchDetail(DataLogVo queryBean,String type,Model model){
		DataLogVo dataLogVo = dataLogService.selectDataLog(queryBean, type);
		model.addAttribute("objBean",dataLogVo);
		return "/modules/dataExchange/dataLog/logDetail";
	}
	
}
