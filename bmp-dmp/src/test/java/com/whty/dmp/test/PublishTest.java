package com.whty.dmp.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.whty.dmp.modules.dataExchange.service.ClassInfoMsgService;
import com.whty.dmp.modules.dataExchange.service.TbOrgaMsgService;
import com.whty.dmp.modules.dataExchange.service.TbUserIdentityMsgService;
import com.whty.dmp.modules.dataExchange.service.TbUserMsgService;
import com.whty.dmp.modules.dataExchange.service.TbUserOrgaMsgService;

/**
 * 机构测试
 * @author cjp
 * @date 2016年9月24日
 */
public class PublishTest extends JunitTest{
	
	@Autowired
	private TbOrgaMsgService tbOrgaMsgService;
	/*@Autowired
	private AccountMsgService accountMsgService;*/
	@Autowired
	private TbUserMsgService tbUserMsgService;
	@Autowired
	private TbUserIdentityMsgService tbUserIdentityMsgService;
	@Autowired
	private TbUserOrgaMsgService tbUserOrgaMsgService;
	
	/** 发布测试  **/
	
	@Test
	public void orgaPublishTest(){
		tbOrgaMsgService.publishTbOrgaBatch();
	}
	
	
	@Test
	public void userPublishTest(){
		tbUserMsgService.publishTbUserBatch();
	}
	
	@Test
	public void userOrgaPublishTest(){
		tbUserOrgaMsgService.publishTbUserOrgaBatch();
	}
	
	@Test
	public void userIdentityPublishTest(){
		tbUserIdentityMsgService.publishTbUserIdentityBatch();
	}
	

	
	/**以下为订阅测试  **/
	
	@Test
	public void orgaSubscribeTest(){
		tbOrgaMsgService.subscribeOragList();
	}
	
	
	@Test
	public void accountSubscribeTest(){
		//accountMsgService.subscribeAccountList();
	}
	
	@Test
	public void userSubscribeTest(){
		tbUserMsgService.subscribeUserList();
	}
	
	@Test
	public void userOrgaRelSubscribeTest(){
		tbUserOrgaMsgService.subscribeUserOrgaRelList();
	}
	
	@Test
	public void userClassRelSubscribeTest(){
		tbUserIdentityMsgService.subscribeUserClassRelList();
	}
	
	public static void main(String[] args){
		Map<String,String> map = new HashMap<String,String>();
		System.out.println(map.isEmpty());
	}
}
