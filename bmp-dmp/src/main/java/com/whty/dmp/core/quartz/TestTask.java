package com.whty.dmp.core.quartz;


import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TestTask {
	
	public void task1(){
		System.out.println("执行任务...Task1--->"+new DateTime().toString("yyyy/MM/dd HH:mm:ss"));
	}
	
	public void task2(){
		System.out.println("执行任务...Task2--->"+new DateTime().toString("yyyy/MM/dd HH:mm:ss"));
	}
	
	public void task3(){
		System.out.println("执行任务...Task3--->"+new DateTime().toString("yyyy/MM/dd HH:mm:ss"));
	}
}
