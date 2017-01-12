package com.whty.dmp.modules.admin.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whty.dmp.core.base.constants.BaseConstants;
import com.whty.dmp.core.mybatis.Page;
import com.whty.dmp.modules.admin.entity.ConfigDataVo;
import com.whty.dmp.modules.admin.service.ConfigDataService;
import com.whty.dmp.utils.IdGen;

/**
 * 配置数据项控制器
 * @author cjp
 * @date 2016年9月21日
 */
@Controller
public class ConfigDataController {
	
	@Autowired
	private ConfigDataService configDataService;
	
	@RequestMapping(value="/admin/dataList")
	public Object jobList(ConfigDataVo queryBean,Page page,Model model){
		if(page == null || page.getPageSize() == -1){
			page.setPageSize(10);
		}
		queryBean.setPage(page);
		List<ConfigDataVo> dataList = configDataService.selectList(queryBean);
		model.addAttribute("queryBean",queryBean);
		model.addAttribute("dataList", dataList);
		model.addAttribute("page",page);
		return "/modules/admin/data/dataList";
	} 
	
	@RequestMapping(value="/admin/dataAdd")
	public Object dictAdd(ConfigDataVo configDataVo){
		return "/modules/admin/data/dataAdd";
	}
	
	@RequestMapping(value="/admin/dataEdit")
	public Object dictEdit(ConfigDataVo queryBean,Model model){
		ConfigDataVo objBean = configDataService.findOne(queryBean);
		model.addAttribute("objBean",objBean);
		return "/modules/admin/data/dataEdit";
	}
	
	@RequestMapping(value="/admin/dataSave",method=RequestMethod.POST)
	@ResponseBody
	public Object dictSave(ConfigDataVo configDataVo){
	   Map<String,Object> result = new HashMap<String,Object>();
	   if(StringUtils.isBlank(configDataVo.getId())){
		   configDataService.insert(configDataVo);
	   }else{
		   configDataService.update(configDataVo);
	   }
	   result.put("result", true);
	   return result;
	}
	
	@RequestMapping(value="/admin/dataDelete",method=RequestMethod.POST)
	@ResponseBody
	public Object dictDelete(ConfigDataVo objBean){
		configDataService.delete(objBean);
		return BaseConstants.RESPONSE_SUCCESS;
	}
	
	/**
	 * 初始化数据绑定
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder){
		// 1、日期  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);  
	}
}
