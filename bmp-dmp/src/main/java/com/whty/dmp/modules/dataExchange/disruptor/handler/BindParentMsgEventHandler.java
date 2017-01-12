package com.whty.dmp.modules.dataExchange.disruptor.handler;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.dmp.modules.dataExchange.dao.DataPublishLogDao;
import com.whty.dmp.modules.dataExchange.disruptor.event.BindParentMsgEvent;
import com.whty.dmp.modules.dataExchange.entity.BindParentMsgVo;
import com.whty.dmp.utils.FileUtil;

/**
 * @author xiaom
 * 绑定家长事件处理
 */
@Service
public final class BindParentMsgEventHandler implements IDmpEventHandler<BindParentMsgEvent> {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(BindParentMsgEventHandler.class);
	
	@Autowired
	private DataPublishLogDao dataPublishLogDao;

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public void onEvent(BindParentMsgEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		
		System.out.println("进入了bindparentmsghangler处理中"+event.getId());
		
		FileUtil.WriteFile("进入了bindparentmsghangler处理中"+event.getId()+"\r\n", "E:/logs/", "dmp.log", "UTF-8");
		
//		Map<String,List<BindParentMsgVo>> listMap = Maps.newHashMap();
//		listMap.put("0", (List<BindParentMsgVo>) event.getData());
		for (BindParentMsgVo bindParentMsgVo : (List<BindParentMsgVo>) event.getData()) {
			System.out.println("得到相应的处理事件数据："+bindParentMsgVo.getChildId());
			FileUtil.WriteFile("得到相应的处理事件数据："+bindParentMsgVo.getChildId()+"\r\n", "E:/logs/", "dmp.log", "UTF-8");
		}
		
//		String result = DataMsgUtils.publishToData(event.getData(), 1,event.getServiceCode());
//		if (StringUtils.isEmpty(result)) { // 返回为空表明正常
//			logger.info("success---"+event.getData().size()+"条---同步完成数据到数据交换平台---end");
//			listMap.put("0", (List<BindParentMsgVo>) event.getData());
//		}else {
//			listMap.put("1", (List<BindParentMsgVo>) event.getData());
//		}
	}

}
