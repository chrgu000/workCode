package com.whty.dmp.modules.dataExchange.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.whty.dmp.modules.dataExchange.disruptor.event.SendMsgEvent;

public class SendMsgEventProducer {
	
	private final RingBuffer<SendMsgEvent> ringBuffer;  
	  
    public SendMsgEventProducer(RingBuffer<SendMsgEvent> ringBuffer){  
        this.ringBuffer = ringBuffer;  
    }  
  
    private static final EventTranslatorOneArg<SendMsgEvent, SendMsg> TRANSLATOR =  
        new EventTranslatorOneArg<SendMsgEvent, SendMsg>() {  
            public void translateTo(SendMsgEvent event, long sequence, SendMsg sendMsg){  
                event.setSendMsg(sendMsg);
            }
        };  
  
    public void productData(SendMsg sendMsg) {  
        ringBuffer.publishEvent(TRANSLATOR, sendMsg);  
    }  

}
