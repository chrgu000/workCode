package com.whty.dmp.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import com.google.common.collect.Lists;
import com.whty.dmp.modules.dataExchange.disruptor.DisruptorFactory;
import com.whty.dmp.modules.dataExchange.disruptor.SendMsgUtil;
import com.whty.dmp.modules.dataExchange.disruptor.event.AccountMsgEvent;
import com.whty.dmp.modules.dataExchange.disruptor.event.BindParentMsgEvent;
import com.whty.dmp.modules.dataExchange.entity.AccountMsgVo;
import com.whty.dmp.modules.dataExchange.entity.BindParentMsgVo;
import com.whty.dmp.modules.dataExchange.entity.TbUserMsgVo;

public class TestMain {

	public static void main(String[] args) throws InterruptedException {
//		queueTest();
		
//	   NumObservable number = new NumObservable();    //被观察者对象
//       number.addObserver(new NumObserver());    //给number这个被观察者添加观察者(当然可以有多个观察者)
//       number.setData(1);
//       number.setData(2);
//       number.setData(3);
		
//		disruptorTest();
		threadTest();
		System.out.println("1243");
		
		Thread.sleep(3000);
		DisruptorFactory.shutdown();
	}
	
	
	
	
	
	
	public static void disruptorTest() throws InterruptedException{
		
		DisruptorFactory disruptorFactory = DisruptorFactory.getInstance();
//		SendMsgUtil sendMsgUtil = new SendMsgUtil();
		
		for (int i = 0; i < 100; i++) {
			List<AccountMsgVo> accountMsgVoLis = new ArrayList<AccountMsgVo>();
			for (int j = 0; j < 10; j++) {
				AccountMsgVo accountMsgVo = new AccountMsgVo();
				accountMsgVo.setAccount(i+"account"+j);
				accountMsgVo.setId(j+"");
				accountMsgVoLis.add(accountMsgVo);
			}
			
			AccountMsgEvent sendEvent = new AccountMsgEvent();
			sendEvent.setData(accountMsgVoLis);
			sendEvent.setServiceCode("1");
			DisruptorFactory.getSendMsgEventProducer().productData(sendEvent);
			
//			sendMsgUtil.putMsg(accountMsgVoLis, "1");
		}
		
		
		for (int i = 0; i < 100; i++) {
			List<BindParentMsgVo> accountMsgVoLis = new ArrayList<BindParentMsgVo>();
			for (int j = 0; j < 10; j++) {
				BindParentMsgVo accountMsgVo = new BindParentMsgVo();
				accountMsgVo.setChildId(i+"childId"+j);
				accountMsgVo.setId(j+"");
				accountMsgVoLis.add(accountMsgVo);
			}
			
			BindParentMsgEvent sendEvent = new BindParentMsgEvent();
			sendEvent.setData(accountMsgVoLis);
			sendEvent.setServiceCode("1");
			DisruptorFactory.getSendMsgEventProducer().productData(sendEvent);
			
//			sendMsgUtil.putMsg(accountMsgVoLis, "1");
		}
		

	}
	
	
	/*public static void listsPartintion(){
		
		TbUserMsgVo userInfoVo1 = new TbUserMsgVo("1", null, null, null, null, null, null, null, null, null);
		TbUserMsgVo userInfoVo2 = new TbUserMsgVo("2", null, null, null, null, null, null, null, null, null);
		TbUserMsgVo userInfoVo3 = new TbUserMsgVo("3", null, null, null, null, null, null, null, null, null);
		TbUserMsgVo userInfoVo4 = new TbUserMsgVo("4", null, null, null, null, null, null, null, null, null);
		TbUserMsgVo userInfoVo5 = new TbUserMsgVo("5", null, null, null, null, null, null, null, null, null);
		TbUserMsgVo userInfoVo6 = new TbUserMsgVo("6", null, null, null, null, null, null, null, null, null);
		TbUserMsgVo userInfoVo7 = new TbUserMsgVo("7", null, null, null, null, null, null, null, null, null);
		TbUserMsgVo userInfoVo8 = new TbUserMsgVo("8", null, null, null, null, null, null, null, null, null);
		TbUserMsgVo userInfoVo9 = new TbUserMsgVo("9", null, null, null, null, null, null, null, null, null);
		
		List<TbUserMsgVo> list = Lists.newArrayList(userInfoVo1,userInfoVo2,userInfoVo3,userInfoVo4,userInfoVo5,userInfoVo6,userInfoVo7,userInfoVo8,userInfoVo9);
		
		List<List<TbUserMsgVo>> subList=Lists.partition(list,2);
		System.out.println("截取后的长度："+subList.size());
		
		for (List<TbUserMsgVo> list2 : subList) {
			System.out.println("集合中的长度："+list2.size());
			System.out.println("集合中的数据为--------------------");
			for (TbUserMsgVo userInfoVo : list2) {
				System.out.println(userInfoVo.getName());
			}
		}
	}*/
	
	
	public static void queueTest () {
	      Queue queue = new LinkedList();
	        queue.offer("Hello");
	        queue.offer("World!");
	        queue.offer("你好！");
	        System.out.println(queue.size());
	        String str;
	        while((str=(String) queue.poll())!=null){
	            System.out.print(str);
	        }
	        System.out.println();
	        System.out.println(queue.size());
	}
	
	static class NumObservable extends Observable {
		
	    int data = 0;
	    
	    public void setData(int i) {
	       data = i;
	       setChanged();    //标记此 Observable对象为已改变的对象
	       notifyObservers();    //通知所有观察者
	    }
	}
	
	static class NumObserver implements Observer{
	    public void update(Observable o, Object arg) {    //有被观察者发生变化，自动调用对应观察者的update方法
	       NumObservable myObserable=(NumObservable) o;     //获取被观察者对象
	       System.out.println("Data has changed to " +myObserable.data);
	    }
	}
	
	
	@SuppressWarnings("static-access")
	public static void threadTest() throws InterruptedException{
		DisruptorFactory.getInstance();
		MyRunnable myRunnable = new MyRunnable();
		Thread thread = new Thread(myRunnable);
		thread.setName("wang");
		
		MyRunnable2 myRunnable2 = new MyRunnable2();
		Thread thread2 = new Thread(myRunnable2);
		thread2.setName("xiao");
		
		thread.start();
		thread2.start();
	}
}

class MyRunnable implements Runnable {
     @Override
     public void run() {
    	 
// 		DisruptorFactory.getInstance();
    	
		for (int i = 0; i < 100; i++) {
			List<AccountMsgVo> accountMsgVoLis = new ArrayList<AccountMsgVo>();
			for (int j = 0; j < 10; j++) {
				AccountMsgVo accountMsgVo = new AccountMsgVo();
				accountMsgVo.setAccount(i+"account"+j);
				accountMsgVo.setId(j+"");
				accountMsgVoLis.add(accountMsgVo);
			}
			SendMsgUtil sendMsgUtil = new SendMsgUtil();
			sendMsgUtil.putMsg(accountMsgVoLis, "1");
			
//			AccountMsgEvent sendEvent = new AccountMsgEvent();
//			sendEvent.setData(accountMsgVoLis);
//			sendEvent.setServiceCode("1");
//			
//			DisruptorFactory.getSendMsgEventProducer().productData(sendEvent);
		}
     }
 }

class MyRunnable2 implements Runnable {
    @Override
    public void run() {
//    	DisruptorFactory.getInstance();
    	
		for (int i = 0; i < 100; i++) {
			List<BindParentMsgVo> accountMsgVoLis = new ArrayList<BindParentMsgVo>();
			for (int j = 0; j < 10; j++) {
				BindParentMsgVo accountMsgVo = new BindParentMsgVo();
				accountMsgVo.setChildId(i+"childId"+j);
				accountMsgVo.setId(j+"");
				accountMsgVoLis.add(accountMsgVo);
			}
			SendMsgUtil sendMsgUtil = new SendMsgUtil();
			sendMsgUtil.putMsg(accountMsgVoLis, "1");
//			BindParentMsgEvent sendEvent = new BindParentMsgEvent();
//			sendEvent.setData(accountMsgVoLis);
//			sendEvent.setServiceCode("1");
//			DisruptorFactory.getSendMsgEventProducer().productData(sendEvent);
		}
    }
}
