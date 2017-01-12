package com.whty.dmp.test;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.whty.dmp.utils.JsonUtils;
import com.whty.dmp.utils.encry.TokenUtils;

public class TokenUtilsTest extends JunitTest{
	
	@Test
	public void testToken(){
		System.out.println("[--获取token--]"+TokenUtils.getAccessToken());
	}
	
	public static void main(String[] args) {
	  long time = 1474867619714L;
	  long sysTime = System.currentTimeMillis();
	  System.out.println("time:"+ time +"sysTime: "+sysTime);
	  
	  BigDecimal bigDecimal = new BigDecimal(String.valueOf(sysTime));
	  System.out.println("bigDecimal==>"+bigDecimal);
	  Date date =new Date(); 
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	  System.out.println("now time is "+sdf.format(date)); 
	  System.out.println("now Time is "+new Date(bigDecimal.longValue()));
	}
}
