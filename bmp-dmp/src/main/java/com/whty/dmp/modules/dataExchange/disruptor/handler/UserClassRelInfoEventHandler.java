package com.whty.dmp.modules.dataExchange.disruptor.handler;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.whty.dmp.modules.dataExchange.common.DataMsgUtils;
import com.whty.dmp.modules.dataExchange.dao.DataPublishLogDao;
import com.whty.dmp.modules.dataExchange.disruptor.event.UserClassRelInfoEvent;
import com.whty.dmp.modules.dataExchange.entity.TbUserIdentityMsgVo;

/**
 * @author xiaom
 * 用户班级关系事件处理
 *
 */
@Service
public final class UserClassRelInfoEventHandler implements IDmpEventHandler<UserClassRelInfoEvent> {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(UserClassRelInfoEventHandler.class);
	
	@Autowired
	private DataPublishLogDao dataPublishLogDao;

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public void onEvent(UserClassRelInfoEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		
		Map<String,List<TbUserIdentityMsgVo>> listMap = Maps.newHashMap();
		
		String result = DataMsgUtils.publishToData(event.getData(), 1,event.getServiceCode());
		if (StringUtils.isEmpty(result)) { // 返回为空表明正常
			logger.info("success---"+event.getData().size()+"条---同步完成数据到数据交换平台---end");
			listMap.put("0", (List<TbUserIdentityMsgVo>) event.getData());
		}else {
			listMap.put("1", (List<TbUserIdentityMsgVo>) event.getData());
		}
		
		//写入日志信息
		
	}

}
