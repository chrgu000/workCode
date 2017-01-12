package com.whty.dmp.modules.dataExchange.thread;

import java.util.Date;

import com.whty.dmp.core.spring.SpringContextHolder;
import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.modules.dataExchange.service.DataLogService;

public class DataSubscribeLogThread implements Runnable{
	
	DataLogVo objBean;
	String type;
	
	private DataLogService dataLogService = SpringContextHolder.getBean("dataLogService");
	
	public DataSubscribeLogThread(){}
	
	public DataSubscribeLogThread(DataLogVo objBean,String type){
		this.objBean = objBean;
		this.type = type;
	}
	

	@Override
	public void run() {
		Date nowTime = new Date();
	}

}
