package com.whty.dmp.modules.dataExchange.disruptor.handler;

import com.whty.dmp.modules.dataExchange.disruptor.SendMsg;

public interface IDmpEventHandler<T extends SendMsg> {
	
	public void onEvent(T paramT, long paramLong, boolean paramBoolean) throws Exception;
	
}
