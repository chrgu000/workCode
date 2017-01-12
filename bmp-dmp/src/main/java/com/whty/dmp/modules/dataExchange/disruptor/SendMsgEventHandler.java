package com.whty.dmp.modules.dataExchange.disruptor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.whty.dmp.modules.dataExchange.disruptor.event.AccountMsgEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.BindParentMsgEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.ClassInfoMsgEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.OrgaInfoEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.SendMsgEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.UserClassRelInfoEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.UserInfoEvent;
import com.whty.dmp.modules.dataExchange.disruptor.handler.AccountMsgEventHandler;
import com.whty.dmp.modules.dataExchange.disruptor.handler.BindParentMsgEventHandler;
import com.whty.dmp.modules.dataExchange.disruptor.handler.ClassInfoMsgEventHandler;
import com.whty.dmp.modules.dataExchange.disruptor.handler.IDmpEventHandler;
import com.whty.dmp.modules.dataExchange.disruptor.handler.OrgaInfoEventHandler;
import com.whty.dmp.modules.dataExchange.disruptor.handler.UserClassRelInfoEventHandler;
import com.whty.dmp.modules.dataExchange.disruptor.handler.UserInfoEventHandler;

/**
 * @author xiaom
 * 核心 处理器
 */
public class SendMsgEventHandler implements EventHandler<SendMsgEvent> {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SendMsgEventHandler.class);
	
	private Map<Class<? extends SendMsg>, IDmpEventHandler<? extends SendMsg>> handlerMap = new HashMap<Class<? extends SendMsg>, IDmpEventHandler<? extends SendMsg>>();

    public SendMsgEventHandler(){
      handlerMap.put(AccountMsgEvent.class, new AccountMsgEventHandler());
      handlerMap.put(BindParentMsgEvent.class, new BindParentMsgEventHandler());
      handlerMap.put(ClassInfoMsgEvent.class, new ClassInfoMsgEventHandler());
      handlerMap.put(OrgaInfoEvent.class, new OrgaInfoEventHandler());
      handlerMap.put(UserClassRelInfoEvent.class, new UserClassRelInfoEventHandler());
      handlerMap.put(UserInfoEvent.class, new UserInfoEventHandler());
    }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onEvent(SendMsgEvent sendMsgEvent, long sequence, boolean endOfBatch)
			throws Exception {
		    SendMsg event = sendMsgEvent.getSendMsg();

	        //如果能好到对应类型的消息处理器,则获取
	        if(handlerMap.get(event.getClass())!=null){
	        	IDmpEventHandler handler= handlerMap.get(event.getClass());
	        	try{
	        		//处理消息
	        		handler.onEvent(event, sequence, endOfBatch);
	        	}catch(Exception t){
	        		logger.error(t.getMessage(),t);
	        	}
	        }}

}
