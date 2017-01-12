package com.whty.dmp.modules.dataExchange.disruptor.event;

import com.lmax.disruptor.EventFactory;
import com.whty.dmp.modules.dataExchange.disruptor.SendMsg;

/**
 * @author xiaom
 *
 */
public class SendMsgEvent {
	
	private SendMsg sendMsg;

	public SendMsg getSendMsg() {
		return sendMsg;
	}

	public void setSendMsg(SendMsg sendMsg) {
		this.sendMsg = sendMsg;
	}
	
	
	public final static EventFactory<SendMsgEvent> EVENT_FACTORY = new EventFactory<SendMsgEvent>() {
        public SendMsgEvent newInstance() {
            return new SendMsgEvent();
        }
    };
	

}
