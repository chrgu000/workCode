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
import com.whty.dmp.modules.dataExchange.dao.TbOrgaMsgDao;
import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.modules.dataExchange.entity.TbOrgaMsgVo;
import com.whty.dmp.utils.IdGen;
import com.whty.dmp.utils.JsonUtils;

/**
 * 机构信息-service
 * 
 * @author cjp
 * @date 2016年9月23日
 */
@Service
public class TbOrgaMsgService {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(TbOrgaMsgService.class);

	@Autowired
	private TbOrgaMsgDao tbOrgaMsgDao; // 机构dao
	@Autowired
	private ConfigDataDao configDataDao;// 数据配置dao
	@Autowired
	private DataPublishLogDao dataPublishLogDao;//数据发布日志dao
	@Autowired
	private DataSubscribeLogDao dataSubscribeLogDao;//数据订阅日志dao

	/**
	 * 批量发布机构信息数据
	 * zhangmingxing 2016-12-05
	 */
	public void publishTbOrgaBatch(){
		//发布新增未发布的数据
		publishTbOrga(StatusConstants.DATA_EX_FLAG_ADD,StatusConstants.OPERATOR_TYPE_ADD);
		//发布修改未发布的数据
		publishTbOrga(StatusConstants.DATA_EX_FLAG_EDIT,StatusConstants.OPERATOR_TYPE_EDIT);
		//发布删除未发布的数据
		publishDelTbOrga(StatusConstants.DATA_EX_FLAG_DELETE,StatusConstants.OPERATOR_TYPE_DELETE);
	}
	
	private void publishTbOrga(String dataStatus,String operatorType) {
		if(StringUtils.isNotBlank(dataStatus)){
			TbOrgaMsgVo queryBean = new TbOrgaMsgVo();
			queryBean.setDataExFrom(StatusConstants.DATA_EX_FROM_LOCAL);
			queryBean.setDataExFlag(dataStatus);
			List<TbOrgaMsgVo> newList = null;
			if ((newList = tbOrgaMsgDao.selectList(queryBean)) != null && newList.size() > 0) {
				logger.info("start---开始同步到数据交换平台，数据量:" + newList.size());
				// 3.发布数据
				publishData(newList, operatorType);
				logger.info("end---同步数据至数据交换平台完毕---");
			}
		}
	}
	
	private void publishDelTbOrga(String dataStatus,String operatorType){
		if(StringUtils.isNotBlank(dataStatus)){
			TbOrgaMsgVo queryBean = new TbOrgaMsgVo();
			queryBean.setDataExFrom(StatusConstants.DATA_EX_FROM_LOCAL);
			queryBean.setDataExFlag(dataStatus);
			List<TbOrgaMsgVo> newList = null;
			if ((newList = tbOrgaMsgDao.selectDeleteList(queryBean)) != null && newList.size() > 0) {
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
	private void publishData(List<TbOrgaMsgVo> list,String operatorType){
		String dataStatus = "0";//默认成功推送
		String serviceCode_p = ConfigDictUtils.getDictValue(DataConstants.serviceCode_tbOrga_p, DataConstants.serviceCode);
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
				tbOrgaMsgDao.updateDelFlagBatch(list);
			}else{
				tbOrgaMsgDao.updateFlagBatch(list);
			}
		}else { //如果100条，那么单独每条进行数据同步
			logger.info("批量发布失败，改为逐条发布----");
			for(TbOrgaMsgVo obj : list){
				dataStatus = "0";//默认成功
				result = DataMsgUtils.publishToData(obj, Integer.parseInt(operatorType), serviceCode_p);
				if (StringUtils.isNotBlank(result)) { //返回不为空表明不正常
					dataStatus = "1";
				}
				DataLogVo dataLogVo = getData(obj, dataStatus,operatorType,serviceCode_p,result);
				dataPublishLogDao.insert(dataLogVo);
				if(StatusConstants.OPERATOR_TYPE_DELETE.equals(operatorType)){
					tbOrgaMsgDao.updateDelFlagSingle(dataLogVo);
				}else{
					tbOrgaMsgDao.updateFlagSingle(dataLogVo);
				}
			}
		}
	}
	
	
	/**
	 * 组装数据--多条
	 * @param list 目标数据
	 * @param dataStatus 0：成功、1：失败
	 * @return
	 */
	private List<DataLogVo> getDataList(List<TbOrgaMsgVo> orgaMsgList,String dataStatus,String operatorType,String serviceCode){
		if(orgaMsgList==null||orgaMsgList.size() == 0){
			return null;
		}
		Date nowTime = new Date();
		DataLogVo dataLogVo;
		String data = ConfigDictUtils.getDictValue(DataConstants.data_tbOrga,DataConstants.data);
		List<DataLogVo> list = Lists.newArrayList();
		for(TbOrgaMsgVo orgaMsgVo:orgaMsgList){
			dataLogVo = new DataLogVo(serviceCode,operatorType,data,nowTime);
			if(null != orgaMsgVo.getOperatorType()){
				dataLogVo.setOperatorType(orgaMsgVo.getOperatorType().toString());
			}
			dataLogVo.setDataStatus(dataStatus);
			dataLogVo.setId(IdGen.uuid());
			dataLogVo.setObjId(orgaMsgVo.getId());
			dataLogVo.setDataJson(JsonUtils.ojbTojson(orgaMsgVo));
			list.add(dataLogVo);
		}
		return list;
	}
	
	/**
	 * 组装数据--单条
	 * @param orgaMsgVo
	 * @param dataStatus 0：成功、1：失败
	 * @param operatorType 
	 * @return
	 */
	private DataLogVo getData(TbOrgaMsgVo orgaMsgVo,String dataStatus,String operatorType,String serviceCode,String errorMsg){
		String data = ConfigDictUtils.getDictValue(DataConstants.data_tbOrga,DataConstants.data);
		Date nowTime = new Date();
		DataLogVo dataLogVo = new DataLogVo(serviceCode,operatorType,data,nowTime);
		if(null != orgaMsgVo.getOperatorType()){
			dataLogVo.setOperatorType(orgaMsgVo.getOperatorType().toString());
		}
		dataLogVo.setErrorMsg(LogMsgUtils.SubErrorMsg(errorMsg));
		dataLogVo.setDataStatus(dataStatus);
		dataLogVo.setId(IdGen.uuid());
		dataLogVo.setObjId(orgaMsgVo.getId());
		dataLogVo.setDataJson(JsonUtils.ojbTojson(orgaMsgVo));
		return dataLogVo;
	}
	
	
	/**
	 * 获取机构发布的时间配置
	 * config_data
	 * @return
	 */
	private ConfigDataVo getPublishConfig() {
		ConfigDataVo queryBean = new ConfigDataVo(DataConstants.orga_publish);
		List<ConfigDataVo> list = configDataDao.selectList(queryBean);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 订阅数据服务
	 * 
	 * @param dataMsgVo
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public void subscribeTbOragList() {
		logger.info("start-------------开始消费数据----------------");
		//1.获取数据
		String serviceCode_d = ConfigDictUtils.getDictValue(DataConstants.serviceCode_tbOrga_d, DataConstants.serviceCode);
		String result = DataMsgUtils.subscribeData(serviceCode_d);//订阅的服务code
		if(JsonUtils.isJson(result)){
			//2.解析List<String>
			List<String> resultList = (List<String>) JsonUtils.jsonToObj(result, List.class);
			System.out.println(JsonUtils.ojbTojson(resultList));
			List<TbOrgaMsgVo> msgList = Lists.newArrayList();
			List<TbOrgaMsgVo> failList = Lists.newArrayList();
			for(String object : resultList){
				TbOrgaMsgVo tbOrgaMsgVo = (TbOrgaMsgVo) JsonUtils.jsonToObj(object, TbOrgaMsgVo.class);
				// 2.1.执行具体的业务操作
				boolean flag = subscribeOperator(tbOrgaMsgVo);
				if(flag){
					msgList.add(tbOrgaMsgVo);
				}else{
					failList.add(tbOrgaMsgVo);
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
	
	private boolean subscribeOperator(TbOrgaMsgVo tbOrgaMsgVo){
		try{
			if(DataMsgVo.OPER_INSERT == tbOrgaMsgVo.getOperatorType()){
				tbOrgaMsgDao.insert(tbOrgaMsgVo);
			}else if (DataMsgVo.OPER_UPDATE == tbOrgaMsgVo.getOperatorType()) {
				tbOrgaMsgDao.update(tbOrgaMsgVo);
			} else if (DataMsgVo.OPER_DELETE == tbOrgaMsgVo.getOperatorType()) {
				tbOrgaMsgDao.delete(tbOrgaMsgVo);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
