package com.whty.dmp.modules.dataExchange.disruptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.whty.dmp.modules.dataExchange.disruptor.event.AccountMsgEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.BindParentMsgEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.ClassInfoMsgEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.OrgaInfoEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.UserClassRelInfoEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.UserInfoEvent;
import com.whty.dmp.modules.dataExchange.entity.AccountMsgVo;
import com.whty.dmp.modules.dataExchange.entity.BindParentMsgVo;
import com.whty.dmp.modules.dataExchange.entity.ClassInfoMsgVo;
import com.whty.dmp.modules.dataExchange.entity.TbOrgaMsgVo;
import com.whty.dmp.modules.dataExchange.entity.TbUserIdentityMsgVo;
import com.whty.dmp.modules.dataExchange.entity.TbUserMsgVo;

/**
 * @author xiaom
 * 公共接口调用类，统一规范入口使用
 */
public class SendMsgUtil {
	
	private  Map<Class<?>, SendMsg> utilMap =  new HashMap<Class<?>, SendMsg>();
	
	public SendMsgUtil(){
		utilMap.put(AccountMsgVo.class, new AccountMsgEvent());
		utilMap.put(BindParentMsgVo.class, new BindParentMsgEvent());
		utilMap.put(ClassInfoMsgVo.class, new ClassInfoMsgEvent());
		utilMap.put(TbOrgaMsgVo.class, new OrgaInfoEvent());
		utilMap.put(TbUserIdentityMsgVo.class, new UserClassRelInfoEvent());
		utilMap.put(TbUserMsgVo.class, new UserInfoEvent());
	}
	
//	private static SendMsgUtil instance = null;
//	public static SendMsgUtil getInstance(){
//		 if(instance == null){
//			 instance = new SendMsgUtil();
//		 }
//		 return instance;
//	}
	
	public <T> void putMsg(List<T> MsgVoList , String serviceCode , int pageSize){
		if(MsgVoList != null && MsgVoList.size() >= 10000){
			Class<?> msgClass = MsgVoList.get(0).getClass();
			for (List<T> needData : Lists.partition(MsgVoList, pageSize)) {
				SendMsg sendMsg = utilMap.get(msgClass);
				sendMsg.setServiceCode(serviceCode);
				sendMsg.setData(needData);
//				DisruptorFactory.getInstance();
				//				Thread.sleep(2000);
				DisruptorFactory.getSendMsgEventProducer().productData(sendMsg);
			}
		}
	}
	
	public <T> void putMsg(List<T> MsgVoList , String serviceCode){
		if(MsgVoList != null && MsgVoList.size() > 0){
			Class<?> msgClass = MsgVoList.get(0).getClass();
			SendMsg sendMsg = utilMap.get(msgClass);
			sendMsg.setServiceCode(serviceCode);
			sendMsg.setData(MsgVoList);
//			DisruptorFactory.getInstance();
			//				Thread.sleep(2000);
			DisruptorFactory.getSendMsgEventProducer().productData(sendMsg);
		}
	}
}
