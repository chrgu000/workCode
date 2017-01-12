/*package com.whty.dmp.modules.dataExchange.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.whty.dmp.core.base.vo.DataEntity;
import com.whty.dmp.core.mybatis.Page;
import com.whty.dmp.modules.admin.dao.ConfigDataDao;
import com.whty.dmp.modules.admin.entity.ConfigDataVo;
import com.whty.dmp.modules.admin.utils.ConfigDictUtils;
import com.whty.dmp.modules.dataExchange.common.DataConstants;
import com.whty.dmp.modules.dataExchange.common.DataMsgUtils;
import com.whty.dmp.modules.dataExchange.common.DataMsgVo;
import com.whty.dmp.modules.dataExchange.common.LogMsgUtils;
import com.whty.dmp.modules.dataExchange.dao.AccountMsgDao;
import com.whty.dmp.modules.dataExchange.dao.DataPublishLogDao;
import com.whty.dmp.modules.dataExchange.dao.DataSubscribeLogDao;
import com.whty.dmp.modules.dataExchange.entity.AccountMsgVo;
import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.modules.dataExchange.entity.OrgaMsgVo;
import com.whty.dmp.utils.IdGen;
import com.whty.dmp.utils.JsonUtils;
import com.whty.dmp.utils.encry.EncryptUtils;

*//**
 * 用户账号处理类
 * @author cjp
 * @date 2016年9月12日
 *//*
@Service
public class AccountMsgService {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(AccountMsgService.class);
	
	@Autowired
	private AccountMsgDao accountMsgDao;
	@Autowired
	private ConfigDataDao configDataDao;// 数据配置dao
	@Autowired
	private DataPublishLogDao dataPublishLogDao;//数据发布日志dao
	@Autowired
	private DataSubscribeLogDao dataSubscribeLogDao;//数据订阅日志dao

	private int pageSize = 100;
	
	*//**
	 * 账号数据发布
	 *//*
	
	public void publishAccountList(){
		// 1.数据配置时间
		ConfigDataVo configDataVo = getPublishConfig();
		Date nowTime = new Date();
		// 2.发布新增数据
		publishAccountList_Create(configDataVo, nowTime);
		// 3.发布更新数据
//		publishAccountList_update(configDataVo, nowTime);
	}
	
	*//**
	 * 发布新增数据
	 * @param configDataVo
	 * @param nowTime
	 *//*
	public void publishAccountList_Create(ConfigDataVo configDataVo,Date nowTime) {
		if (configDataVo != null && configDataVo.getCreateTime() != null){
			// 2.使用系统当前时间对比获取新增数据
			AccountMsgVo queryBean = new AccountMsgVo(nowTime);
			queryBean.setCreateTime(configDataVo.getCreateTime());
			Page page = new Page(1,pageSize); //每次查询100条--分页查询
			queryBean.setPage(page);
			List<AccountMsgVo> newList = null;
			int operatorType = 1;//新增
			while ((newList = accountMsgDao.selectCreateList(queryBean)) != null && newList.size() > 0) {
				logger.info("start---开始同步到数据交换平台，总数目:" + page.getCount() +"当前页码:"+page.getPageNo() +"当前数目:"+ newList.size());
				// 3.发布数据
				publishData(newList, operatorType);
				page.setPageNo(page.getPageNo()+1);
				queryBean.setPage(page);
			}
			if(page.getCount() != null && page.getCount() > 0){
				//4.更新同步时间--新增
				configDataVo.setCreateTime(nowTime);
				configDataDao.update(configDataVo);
			}
		}
	}
	
	*//**
	 * 发布修改数据
	 * @param configDataVo
	 * @param nowTime
	 *//*
	public void publishOrgaList_update(ConfigDataVo configDataVo,Date nowTime) {
		if (configDataVo != null && configDataVo.getUpdateTime() != null) {
			// 2.使用系统当前时间对比获取新增数据
			AccountMsgVo queryBean = new AccountMsgVo(nowTime);
			queryBean.setUpdateTime(configDataVo.getUpdateTime());
			Page page = new Page(1, pageSize); // 每次查询100条--分页查询
			queryBean.setPage(page);
			List<AccountMsgVo> updateList = null;
			int operatorType = 2;// 修改
			while ((updateList = accountMsgDao.selectUpdateList(queryBean)) != null && updateList.size() > 0) {
				logger.info("start---开始同步到数据交换平台，总数目:" + page.getCount() + "当前页码:" + page.getPageNo() + "当前数目:" + updateList.size());
				// 3.发布数据
				publishData(updateList, operatorType);
				page.setPageNo(page.getPageNo() + 1);
				queryBean.setPage(page);
			}
			if (page.getCount() != null && page.getCount() > 0) {
				// 4.更新同步时间--修改
				configDataVo.setUpdateTime(nowTime);
				configDataDao.update(configDataVo);
			}
		}
	}
	
	*//**
	 * 发布数据
	 * @param newList
	 * @param operatorType
	 *//*
	private void publishData(List<AccountMsgVo> list,int operatorType){
		String dataStatus = "0";//默认成功推送
		String serviceCode_p = ConfigDictUtils.getDictValue(DataConstants.serviceCode_account_p, DataConstants.serviceCode);
		List<DataLogVo> dataLogList = null;//插入日志信息
		String result = DataMsgUtils.publishToData(list, operatorType,serviceCode_p);
		if (StringUtils.isEmpty(result)) { // 返回为空表明正常
			logger.info("success--["+operatorType+"]-"+list.size()+"条---同步完成数据到数据交换平台---end");
			//3.插入日志信息
			dataLogList = getDataList(list, dataStatus,"1",serviceCode_p);//操作类型新增
			if(dataLogList != null && dataLogList.size() > 0){
				dataPublishLogDao.insertList(dataLogList);
			}
		}else { //如果100条，那么单独每条进行数据同步
			for(AccountMsgVo obj : list){
				dataStatus = "0";//默认成功
				result = DataMsgUtils.publishToData(obj, operatorType, serviceCode_p);
				if (StringUtils.isNotBlank(result)) { //返回不为空表明不正常
					dataStatus = "1";
				}
				DataLogVo dataLogVo = getData(obj, dataStatus, "1",serviceCode_p,result);
				dataPublishLogDao.insert(dataLogVo);
			}
		}
	}
	
	*//**
	 * 组装数据
	 * @param list 目标数据
	 * @param dataStatus 0：成功、1：失败
	 * @return
	 *//*
	public List<DataLogVo> getDataList(List<AccountMsgVo> accountMsgList,String dataStatus,String operatorType,String serviceCode){
		if(accountMsgList==null||accountMsgList.size() == 0){
			return null;
		}
		Date nowTime = new Date();
		DataLogVo dataLogVo;
		String data = ConfigDictUtils.getDictValue(DataConstants.data_account,DataConstants.data);
		List<DataLogVo> list = Lists.newArrayList();
		for(AccountMsgVo accountMsgVo:accountMsgList){
			dataLogVo = new DataLogVo(serviceCode,operatorType,data,nowTime);
			if(null != accountMsgVo.getOperatorType()){
				dataLogVo.setOperatorType(accountMsgVo.getOperatorType().toString());
			}
			dataLogVo.setDataStatus(dataStatus);
			dataLogVo.setId(IdGen.uuid());
			dataLogVo.setObjId(accountMsgVo.getId());
			dataLogVo.setDataJson(JsonUtils.ojbTojson(accountMsgVo));
			list.add(dataLogVo);
		}
		return list;
	}
	
	*//**
	 * 组装数据--单条
	 * @param accountMsgVo
	 * @param dataStatus 0：成功、1：失败
	 * @param operatorType 
	 * @return
	 *//*
	public DataLogVo getData(AccountMsgVo accountMsgVo,String dataStatus,String operatorType,String serviceCode,String errorMsg){
		Date nowTime = new Date();
		String data = ConfigDictUtils.getDictValue(DataConstants.data_account,DataConstants.data);
		DataLogVo dataLogVo = new DataLogVo(serviceCode,operatorType,data,nowTime);
		if(null != accountMsgVo.getOperatorType()){
			dataLogVo.setOperatorType(accountMsgVo.getOperatorType().toString());
		}
		dataLogVo.setErrorMsg(LogMsgUtils.SubErrorMsg(errorMsg));
		dataLogVo.setDataStatus(dataStatus);
		dataLogVo.setId(IdGen.uuid());
		dataLogVo.setObjId(accountMsgVo.getId());
		dataLogVo.setDataJson(JsonUtils.ojbTojson(accountMsgVo));
		return dataLogVo;
	}
	
	*//**
	 * 获取账号发布的时间配置
	 * config_data
	 * @return
	 *//*
	protected ConfigDataVo getPublishConfig() {
		ConfigDataVo queryBean = new ConfigDataVo(DataConstants.account_publish);
		List<ConfigDataVo> list = configDataDao.selectList(queryBean);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	*//**
	 * 订阅数据服务
	 * @param dataMsgVo
	 * @return
	 *//*
	@Transactional
	@SuppressWarnings("unchecked")
	public void subscribeAccountList(){
		logger.info("start-------------开始消费数据----------------");
		//1.获取数据
		String serviceCode_d = ConfigDictUtils.getDictValue(DataConstants.serviceCode_account_d, DataConstants.serviceCode);
		String result = DataMsgUtils.subscribeData(serviceCode_d);//订阅的服务code
		if(JsonUtils.isJson(result)){
			//2.解析List<String>
			List<String> resultList = (List<String>) JsonUtils.jsonToObj(result, List.class);
			System.out.println(JsonUtils.ojbTojson(resultList));
			List<AccountMsgVo> msgList = Lists.newArrayList();
			List<AccountMsgVo> failList = Lists.newArrayList();
			for(String object : resultList){
				AccountMsgVo accountMsgVo = (AccountMsgVo) JsonUtils.jsonToObj(object, AccountMsgVo.class);
				// 2.1.执行具体的业务操作
				boolean flag = subscribeOperator(accountMsgVo);
				if(flag){
					msgList.add(accountMsgVo);
				}else{
					failList.add(accountMsgVo);
				}
			}
			//3.转换为解析日志，保存入库
			List<DataLogVo> dataLogList = getDataList(msgList, "0","",serviceCode_d);//操作类型新增
			List<DataLogVo> failLogList = getDataList(failList, "1","",serviceCode_d);//订阅失败数据
			int size = 0;
			int failSize = 0;
			if(dataLogList!=null && dataLogList.size() >0){
				dataSubscribeLogDao.insertList(dataLogList);
				size = dataLogList.size();
			}
			if(failLogList!=null && failLogList.size()>0){
				dataSubscribeLogDao.insertList(failLogList);
				failSize = failLogList.size();
			}
			logger.info("end-------------结束消费数据--------成功数据量："+size+"--------失败数据量："+failSize);
		}else{
			logger.error("end-------------获取数据失败----------------");
		}
	}
	
	public boolean subscribeOperator(AccountMsgVo accountMsgVo){
		Date nowTime = new Date();
		try{
			if(DataMsgVo.OPER_INSERT == accountMsgVo.getOperatorType()){
				accountMsgVo.setCreateTime(nowTime);
				accountMsgVo.setCreateBy(DataEntity.CREATE_USER);
				accountMsgVo.setUpdateBy(DataEntity.CREATE_USER);
				accountMsgVo.setUpdateTime(nowTime);
				//accountMsgVo.setPassword(EncryptUtils.decode(accountMsgVo.getPassword()));
				accountMsgDao.insert(accountMsgVo);
			}else if (DataMsgVo.OPER_UPDATE == accountMsgVo.getOperatorType()) {
				accountMsgVo.setUpdateTime(nowTime);
				accountMsgDao.update(accountMsgVo);
			} else if (DataMsgVo.OPER_DELETE == accountMsgVo.getOperatorType()) {
				accountMsgDao.delete(accountMsgVo);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
*/