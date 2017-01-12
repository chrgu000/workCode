package com.whty.dmp.modules.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whty.dmp.core.base.constants.BaseConstants;
import com.whty.dmp.core.mybatis.Page;
import com.whty.dmp.modules.admin.entity.ConfigDictVo;
import com.whty.dmp.modules.admin.service.ConfigDictService;

/**
 * 配置字典控制器
 * @author cjp
 * @date 2016年9月21日
 */
@Controller
public class ConfigDictController {
	
	@Autowired
	private ConfigDictService configDictService;
	
	@RequestMapping(value="/admin/dictList")
	public Object jobList(ConfigDictVo queryBean,Page page,Model model){
		if(page == null || page.getPageSize() == -1){
			page.setPageSize(10);
		}
		queryBean.setPage(page);
		List<ConfigDictVo> dictList = configDictService.selectList(queryBean);
		List<String> typeList = configDictService.queryTypeList();
		model.addAttribute("queryBean",queryBean);
		model.addAttribute("dictList", dictList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("page",page);
		return "/modules/admin/dict/dictList";
	} 
	
	@RequestMapping(value="/admin/dictAdd")
	public Object dictAdd(){
		return "/modules/admin/dict/dictAdd";
	}
	
	@RequestMapping(value="/admin/dictEdit")
	public Object dictEdit(ConfigDictVo queryBean,Model model){
		ConfigDictVo objBean = configDictService.findOne(queryBean);
		model.addAttribute("objBean",objBean);
		return "/modules/admin/dict/dictEdit";
	}
	
	@RequestMapping(value="/admin/dictSave",method=RequestMethod.POST)
	@ResponseBody
	public Object dictSave(ConfigDictVo objBean){
		Map<String,Object> result = new HashMap<String,Object>();
		if(StringUtils.isBlank(objBean.getId())){
			configDictService.insertDict(objBean);
		}else{
			configDictService.updateDict(objBean);
		}
		result.put("result", true);
		return result;
	}
	
	@RequestMapping(value="/admin/dictDelete",method=RequestMethod.POST)
	@ResponseBody
	public Object dictDelete(ConfigDictVo objBean){
		configDictService.deleteDict(objBean);
		return BaseConstants.RESPONSE_SUCCESS;
	}
	
	

}
