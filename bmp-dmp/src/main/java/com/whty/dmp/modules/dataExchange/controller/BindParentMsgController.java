package com.whty.dmp.modules.dataExchange.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whty.dmp.modules.dataExchange.common.DataMsgUtils;
import com.whty.dmp.modules.dataExchange.common.DataMsgVo;
import com.whty.dmp.modules.dataExchange.entity.BindParentMsgVo;
import com.whty.dmp.modules.dataExchange.service.BindParentMsgService;

/**
 * 家长学生关系信息控制器
 * @author zhangmingxing
 * @date 2016年9月12日
 */
@Controller
public class BindParentMsgController {
	
	@Autowired
	private BindParentMsgService bindParentMsgService;
	
	@RequestMapping(value = "/bindParent/subscribe",method = RequestMethod.POST)
	@ResponseBody
	public Object subscribeSingle(HttpServletRequest request,HttpServletResponse response){
		DataMsgVo dataMsgVo = DataMsgUtils.getDataFromRequest(request,BindParentMsgVo.class);
		int num = 0;
		if(dataMsgVo != null){
			num = bindParentMsgService.subscribeSingle(dataMsgVo);
		}
		return num;
	}
}
