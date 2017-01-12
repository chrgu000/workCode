package com.whty.dmp.test;

import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.modules.dataExchange.entity.TbOrgaMsgVo;
import com.whty.dmp.modules.dataExchange.service.DataLogService;
import com.whty.dmp.utils.IdGen;
import com.whty.dmp.utils.JsonUtils;

public class DataLogServiceTest extends JunitTest{
	
	@Autowired
	private DataLogService dataLogService;
	
	@Test
	public void testInsert(){
		//1.组装接收数据
		Map<String,Object> objMap = Maps.newHashMap();
		TbOrgaMsgVo orga = new TbOrgaMsgVo();
		orga.setId("111112");
		objMap.put("obj", orga);
		objMap.put("operatorType",1);
		objMap.put("platCode","3A");
		String json = JsonUtils.ojbTojson(objMap);
		
		//组装数据库日志数据
		DataLogVo dataLogVo = new DataLogVo();
		dataLogVo.setId(IdGen.uuid());
		dataLogVo.setObjId(orga.getId());
		dataLogVo.setDataJson(json);
		dataLogVo.setData("0");
		dataLogVo.setServiceCode("ddddddddddffffffffffffff");
		dataLogVo.setOperatorType("1");
		dataLogVo.setCreateTime(new Date());
		
		dataLogService.insertSubscribeLog(dataLogVo);
		
	}
}
