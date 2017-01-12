package com.whty.dmp.modules.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Index.jsp 控制器
 * @author cjp 2016年9月9日
 */
@Controller
public class IndexController {
	
	@RequestMapping("welcome")
	public String header(){
		return "welcome";
	}
	
}
