package com.whty.dmp.modules.dataExchange.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.whty.dmp.core.base.vo.DataEntity;
import com.whty.dmp.modules.admin.dao.ConfigDataDao;
import com.whty.dmp.modules.admin.entity.ConfigDataVo;
import com.whty.dmp.modules.admin.utils.ConfigDictUtils;
import com.whty.dmp.modules.dataExchange.common.DataConstants;
import com.whty.dmp.modules.dataExchange.common.DataMsgUtils;
import com.whty.dmp.modules.dataExchange.common.DataMsgVo;
import com.whty.dmp.modules.dataExchange.common.LogMsgUtils;
import com.whty.dmp.modules.dataExchange.common.StatusConstants;
import com.whty.dmp.modules.dataExchange.dao.DataPublishLogDao;
import com.whty.dmp.modules.dataExchange.dao.DataSubscribeLogDao;
import com.whty.dmp.modules.dataExchange.dao.TbUserMsgDao;
import com.whty.dmp.modules.dataExchange.dao.UserRelInfoDao;
import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.modules.dataExchange.entity.TbUserMsgVo;
import com.whty.dmp.modules.dataExchange.entity.UserRelInfoVo;
import com.whty.dmp.utils.IdGen;
import com.whty.dmp.utils.JsonUtils;

/**
 * 用户账号处理类
 * @author cjp
 * @date 2016年9月12日
 */
@Service
public class TbUserMsgService {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(TbUserMsgService.class);
	
	@Autowired
	private TbUserMsgDao tbUserMsgDao;
	@Autowired
	private UserRelInfoDao userRelInfoDao;
	@Autowired
	private ConfigDataDao configDataDao;// 数据配置dao
	@Autowired
	private DataPublishLogDao dataPublishLogDao;//数据发布日志dao
	@Autowired
	private DataSubscribeLogDao dataSubscribeLogDao;//数据订阅日志dao

	/**
	 * 批量发布用户信息
	 * zhangmingxing 2016-12-05
	 */
	public void publishTbUserBatch(){
		//发布新增未发布的数据
		publishTbUser(StatusConstants.DATA_EX_FLAG_ADD,StatusConstants.OPERATOR_TYPE_ADD);
		//发布修改未发布的数据
		publishTbUser(StatusConstants.DATA_EX_FLAG_EDIT,StatusConstants.OPERATOR_TYPE_EDIT);
		//发布删除未发布的数据
		//publishDelUser(StatusConstants.DATA_EX_FLAG_DELETE,StatusConstants.OPERATOR_TYPE_DELETE);
	}
	
	/**
	 * 发布新增数据
	 * @param configDataVo
	 * @param nowTime
	 */
	private void publishTbUser(String dataStatus,String operatorType){
		if(StringUtils.isNotBlank(dataStatus)){
			TbUserMsgVo queryBean = new TbUserMsgVo();
			queryBean.setDataExFrom(StatusConstants.DATA_EX_FROM_LOCAL);
			queryBean.setDataExFlag(dataStatus);
			List<TbUserMsgVo> newList = null;
			if ((newList = tbUserMsgDao.selectList(queryBean)) != null && newList.size() > 0) {
				logger.info("start---开始同步到数据交换平台，数据量:" + newList.size());
				// 3.发布数据
				publishData(newList, operatorType);
				logger.info("end---同步数据至数据交换平台完毕---");
			}
		}
	}
	
	/**
	 * 发布删除未发布数据
	 * @param configDataVo
	 * @param nowTime
	 */
	private void publishDelUser(String dataStatus,String operatorType){
		if(StringUtils.isNotBlank(dataStatus)){
			TbUserMsgVo queryBean = new TbUserMsgVo();
			queryBean.setDataExFrom(StatusConstants.DATA_EX_FROM_LOCAL);
			queryBean.setDataExFlag(dataStatus);
			List<TbUserMsgVo> newList = null;
			if ((newList = tbUserMsgDao.selectDeleteList(queryBean)) != null && newList.size() > 0) {
				logger.info("start---开始同步到数据交换平台，数据量:" + newList.size());
				// 3.发布数据
				publishData(newList, operatorType);
				logger.info("end---同步数据至数据交换平台完毕---");
			}
		}
	}
	
	/**
	 * 发布数据
	 * @param newList
	 * @param operatorType
	 */
	private void publishData(List<TbUserMsgVo> list,String operatorType){
		String dataStatus = "0";//默认成功推送
		String serviceCode_p = ConfigDictUtils.getDictValue(DataConstants.serviceCode_tbUser_p, DataConstants.serviceCode);
		List<DataLogVo> dataLogList = null;//插入日志信息
		String result = DataMsgUtils.publishToData(list, Integer.parseInt(operatorType),serviceCode_p);
		if (StringUtils.isEmpty(result)) { // 返回为空表明正常
			logger.info("success--["+operatorType+"]-"+list.size()+"条---同步完成数据到数据交换平台---end");
			//3.插入日志信息
			dataLogList = getDataList(list, dataStatus,operatorType,serviceCode_p);//操作类型新增
			if(dataLogList != null && dataLogList.size() > 0){
				dataPublishLogDao.insertList(dataLogList);
			}
			if(StatusConstants.OPERATOR_TYPE_DELETE.equals(operatorType)){
				tbUserMsgDao.updateDelFlagBatch(list);
			}else{
				tbUserMsgDao.updateFlagBatch(list);
			}
		}else { //如果100条，那么单独每条进行数据同步
			logger.info("批量发布失败，改为逐条发布----");
			for(TbUserMsgVo obj : list){
				dataStatus = "0";//默认成功
				result = DataMsgUtils.publishToData(obj, Integer.parseInt(operatorType), serviceCode_p);
				if (StringUtils.isNotBlank(result)) { //返回不为空表明不正常
					dataStatus = "1";
				}
				DataLogVo dataLogVo = getData(obj, dataStatus,operatorType,serviceCode_p,result);
				dataPublishLogDao.insert(dataLogVo);
				if(StatusConstants.OPERATOR_TYPE_DELETE.equals(operatorType)){
					tbUserMsgDao.updateDelFlagSingle(dataLogVo);
				}else{
					tbUserMsgDao.updateFlagSingle(dataLogVo);
				}
			}
		}
	}
	
	/**
	 * 组装数据
	 * @param list 目标数据
	 * @param dataStatus 0：成功、1：失败
	 * @return
	 */
	private List<DataLogVo> getDataList(List<TbUserMsgVo> userInfoList,String dataStatus,String operatorType,String serviceCode){
		if(userInfoList==null||userInfoList.size() == 0){
			return null;
		}
		Date nowTime = new Date();
		DataLogVo dataLogVo;
		String data = ConfigDictUtils.getDictValue(DataConstants.data_tbUser,DataConstants.data);
		List<DataLogVo> list = Lists.newArrayList();
		for(TbUserMsgVo userInfoVo:userInfoList){
			dataLogVo = new DataLogVo(serviceCode,operatorType,data,nowTime);
			if(null != userInfoVo.getOperatorType()){
				dataLogVo.setOperatorType(userInfoVo.getOperatorType().toString());
			}
			dataLogVo.setDataStatus(dataStatus);
			dataLogVo.setId(IdGen.uuid());
			dataLogVo.setObjId(userInfoVo.getId());
			dataLogVo.setDataJson(JsonUtils.ojbTojson(userInfoVo));
			list.add(dataLogVo);
		}
		return list;
	}
	
	/**
	 * 组装数据--单条
	 * @param userInfoVo
	 * @param dataStatus 0：成功、1：失败
	 * @param operatorType 
	 * @return
	 */
	private DataLogVo getData(TbUserMsgVo userInfoVo,String dataStatus,String operatorType,String serviceCode,String errorMsg){
		String data = ConfigDictUtils.getDictValue(DataConstants.data_tbUser,DataConstants.data);
		Date nowTime = new Date();
		DataLogVo dataLogVo = new DataLogVo(serviceCode,operatorType,data,nowTime);
		if(null != userInfoVo.getOperatorType()){
			dataLogVo.setOperatorType(userInfoVo.getOperatorType().toString());
		}
		dataLogVo.setErrorMsg(LogMsgUtils.SubErrorMsg(errorMsg));
		dataLogVo.setDataStatus(dataStatus);
		dataLogVo.setId(IdGen.uuid());
		dataLogVo.setObjId(userInfoVo.getId());
		dataLogVo.setDataJson(JsonUtils.ojbTojson(userInfoVo));
		return dataLogVo;
	}
	
	/**
	 * 获取用户发布的时间配置
	 * config_data
	 * @return
	 */
	protected ConfigDataVo getPublishConfig() {
		ConfigDataVo queryBean = new ConfigDataVo(DataConstants.user_publish);
		List<ConfigDataVo> list = configDataDao.selectList(queryBean);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 订阅数据服务
	 * @param dataMsgVo
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public void subscribeTbUserList(){
		logger.info("start-------------开始消费数据----------------");
		//1.获取数据
		String serviceCode_d = ConfigDictUtils.getDictValue(DataConstants.serviceCode_tbUser_d, DataConstants.serviceCode);
		String result = DataMsgUtils.subscribeData(serviceCode_d);//订阅的服务code
		if(JsonUtils.isJson(result)){
			//2.解析List<String>
			List<String> resultList = (List<String>) JsonUtils.jsonToObj(result, List.class);
			List<TbUserMsgVo> msgList = Lists.newArrayList();
			List<TbUserMsgVo> failList = Lists.newArrayList(); //订阅失败的数据
			for(String object : resultList){
				TbUserMsgVo tbUserInfoVo = (TbUserMsgVo) JsonUtils.jsonToObj(object, TbUserMsgVo.class);
				// 2.1.执行具体的业务操作
				boolean flag = subscribeOperator(tbUserInfoVo);
				if(flag){
					msgList.add(tbUserInfoVo);
				}else{
					failList.add(tbUserInfoVo);
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
	
	private boolean subscribeOperator(TbUserMsgVo tbUserInfoVo){
		try{
			if(DataMsgVo.OPER_INSERT == tbUserInfoVo.getOperatorType()){
				tbUserMsgDao.insert(tbUserInfoVo);
			}else if (DataMsgVo.OPER_UPDATE == tbUserInfoVo.getOperatorType()) {
				tbUserMsgDao.update(tbUserInfoVo);
			} else if (DataMsgVo.OPER_DELETE == tbUserInfoVo.getOperatorType()) {
				tbUserMsgDao.delete(tbUserInfoVo);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	
/*	
	public void insertUserInfo(UserMsgVo userInfoVo) {
		Date time = new Date();
		userInfoVo.setCreateTime(time);
		userInfoVo.setId(StringUtils.isEmpty(userInfoVo.getId())?IdGen.uuid():userInfoVo.getId());
		userMsgDao.insert(userInfoVo);
		//用户机构关系
		UserRelInfoVo userRelInfoVo = new UserRelInfoVo(userInfoVo.getId(), userInfoVo.getOrgaId(), "0", "1");
		userRelInfoVo.setId(IdGen.uuid());
		userRelInfoVo.setCreateTime(time);
		userRelInfoDao.insert(userRelInfoVo);
	}
	
	public void updateUserInfo(UserMsgVo userInfoVo) {
		Date time = new Date();
		userInfoVo.setUpdateTime(time);
		userMsgDao.update(userInfoVo);
		
		if(!StringUtils.isEmpty(userInfoVo.getOrgaId())){
			UserRelInfoVo queryUser = new UserRelInfoVo(userInfoVo.getId(), null, "0", "1");
			UserRelInfoVo userRelInfoVo = userRelInfoDao.selectOne(queryUser);
			userRelInfoVo.setUpdateTime(time);
			userRelInfoVo.setOrgId(userInfoVo.getOrgaId());
			userRelInfoVo.setId(queryUser.getId());
			userRelInfoDao.update(userRelInfoVo);
		}
	}
	
	public void deleteUserInfo(UserMsgVo userInfoVo) {
		userMsgDao.delete(userInfoVo);
	}*/
	
}
