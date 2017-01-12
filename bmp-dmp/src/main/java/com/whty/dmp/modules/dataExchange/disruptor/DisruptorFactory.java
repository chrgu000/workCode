package com.whty.dmp.modules.dataExchange.disruptor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.whty.dmp.modules.dataExchange.disruptor.event.SendMsgEvent;

/**
 * @author xiaom
 * 宸ュ巶瀹氫箟
 */
@SuppressWarnings("unchecked")
public class DisruptorFactory {
	
	
	private final int QUEUE_SIZE = 1024;
	
	private  Disruptor<SendMsgEvent> disruptor;
	
	private static SendMsgEventProducer sendMsgEventProducer; 
	
	private static DisruptorFactory instance = null;
	private DisruptorFactory(){
	}
	
	public static DisruptorFactory getInstance(){
		 if(instance == null){
			 instance = new DisruptorFactory();
			 initAndStart();
			 sendMsgEventProducer = new SendMsgEventProducer(instance.disruptor.getRingBuffer());
		 }
		 return instance;
	}
	
	
	@SuppressWarnings("deprecation")
	private void start(){
        Executor executor = Executors.newCachedThreadPool();
        disruptor = new Disruptor<SendMsgEvent>(SendMsgEvent.EVENT_FACTORY, QUEUE_SIZE, executor, ProducerType.SINGLE,new BlockingWaitStrategy());
        disruptor.handleEventsWith(new SendMsgEventHandler());
        disruptor.start();
    }
	
	private void stop(){
		disruptor.shutdown();
	}
	
	
	public static void initAndStart(){
		instance.start();
	}
	
	public static void shutdown(){
		instance.disruptor.shutdown();
	}

	public static SendMsgEventProducer getSendMsgEventProducer() {
		return sendMsgEventProducer;
	}
	
}
