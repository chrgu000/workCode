package com.whty.dmp.modules.dataExchange.service;

import java.text.SimpleDateFormat;
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
import com.whty.dmp.modules.dataExchange.dao.ClassInfoMsgDao;
import com.whty.dmp.modules.dataExchange.dao.DataPublishLogDao;
import com.whty.dmp.modules.dataExchange.dao.DataSubscribeLogDao;
import com.whty.dmp.modules.dataExchange.entity.ClassInfoMsgVo;
import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.utils.IdGen;
import com.whty.dmp.utils.JsonUtils;

/**
 * 班级信息处理类
 * @author zhangmingxing
 * @date 2016年9月13日
 */
@Service
public class ClassInfoMsgService {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ClassInfoMsgService.class);
	
	@Autowired
	private ClassInfoMsgDao classInfoMsgDao;
	@Autowired
	private ConfigDataDao configDataDao;// 数据配置dao
	@Autowired
	private DataPublishLogDao dataPublishLogDao;//数据发布日志dao
	@Autowired
	private DataSubscribeLogDao dataSubscribeLogDao;//数据订阅日志dao
	
	/**
	 * 批量发布班级信息数据
	 * zhangmingxing 2016-12-02
	 */
	public void publishClassBatch(){
		//发布新增未发布的数据
		publishClass(StatusConstants.DATA_EX_FLAG_ADD,StatusConstants.OPERATOR_TYPE_ADD);
		//发布修改未发布的数据
		publishClass(StatusConstants.DATA_EX_FLAG_EDIT,StatusConstants.OPERATOR_TYPE_EDIT);
		//发布删除未发布的数据
		publishDelClass(StatusConstants.DATA_EX_FLAG_DELETE,StatusConstants.OPERATOR_TYPE_DELETE);
	}
	
	public void publishClass(String dataStatus,String operatorType){
		if(StringUtils.isNotBlank(dataStatus)){
			ClassInfoMsgVo queryBean = new ClassInfoMsgVo();
			queryBean.setDataExFrom(StatusConstants.DATA_EX_FROM_LOCAL);
			queryBean.setDataExFlag(dataStatus);
			List<ClassInfoMsgVo> newList = null;
			if ((newList = classInfoMsgDao.selectList(queryBean)) != null && newList.size() > 0) {
				logger.info("start---开始同步到数据交换平台，数据量:" + newList.size());
				// 3.发布数据
				publishData(newList, operatorType);
				logger.info("end---同步数据至数据交换平台完毕---");
			}
		}
	}
	
	private void publishDelClass(String dataStatus,String operatorType){
		if(StringUtils.isNotBlank(dataStatus)){
			ClassInfoMsgVo queryBean = new ClassInfoMsgVo();
			queryBean.setDataExFrom(StatusConstants.DATA_EX_FROM_LOCAL);
			queryBean.setDataExFlag(dataStatus);
			List<ClassInfoMsgVo> newList = null;
			if ((newList = classInfoMsgDao.selectDeleteList(queryBean)) != null && newList.size() > 0) {
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
	private void publishData(List<ClassInfoMsgVo> list,String operatorType){
		String dataStatus = "0";//默认成功推送
		String serviceCode_p = ConfigDictUtils.getDictValue(DataConstants.serviceCode_class_p, DataConstants.serviceCode);
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
				classInfoMsgDao.updateDelFlagBatch(list);
			}else{
				classInfoMsgDao.updateFlagBatch(list);
			}
		}else { //如果100条，那么单独每条进行数据同步
			logger.info("批量发布失败，改为逐条发布----");
			for(ClassInfoMsgVo obj : list){
				dataStatus = "0";//默认成功
				result = DataMsgUtils.publishToData(obj, Integer.parseInt(operatorType), serviceCode_p);
				if (StringUtils.isNotBlank(result)) { //返回不为空表明不正常
					dataStatus = "1";
				}
				DataLogVo dataLogVo = getData(obj, dataStatus,operatorType,serviceCode_p,result);
				dataPublishLogDao.insert(dataLogVo);
				if(StatusConstants.OPERATOR_TYPE_DELETE.equals(operatorType)){
					classInfoMsgDao.updateDelFlagSingle(dataLogVo);
				}else{
					classInfoMsgDao.updateFlagSingle(dataLogVo);
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
	public List<DataLogVo> getDataList(List<ClassInfoMsgVo> classInfoMsgList,String dataStatus,String operatorType,String serviceCode){
		if(classInfoMsgList==null||classInfoMsgList.size() == 0){
			return null;
		}
		Date nowTime = new Date();
		DataLogVo dataLogVo;
		String data = ConfigDictUtils.getDictValue(DataConstants.data_class,DataConstants.data);
		List<DataLogVo> list = Lists.newArrayList();
		for(ClassInfoMsgVo classInfoMsgVo:classInfoMsgList){
			dataLogVo = new DataLogVo(serviceCode,operatorType,data,nowTime);
			if(null != classInfoMsgVo.getOperatorType()){
				dataLogVo.setOperatorType(classInfoMsgVo.getOperatorType().toString());
			}
			dataLogVo.setDataStatus(dataStatus);
			dataLogVo.setId(IdGen.uuid());
			dataLogVo.setObjId(classInfoMsgVo.getId());
			dataLogVo.setDataJson(JsonUtils.ojbTojson(classInfoMsgVo));
			list.add(dataLogVo);
		}
		return list;
	}
	/**
	 * 组装数据--单条
	 * @param classInfoMsgVo
	 * @param dataStatus 0：成功、1：失败
	 * @param operatorType 
	 * @return
	 */
	public DataLogVo getData(ClassInfoMsgVo classInfoMsgVo,String dataStatus,String operatorType,String serviceCode,String errorMsg){
		Date nowTime = new Date();
		String data = ConfigDictUtils.getDictValue(DataConstants.data_class,DataConstants.data);
		DataLogVo dataLogVo = new DataLogVo(serviceCode,operatorType,data,nowTime);
		if(null != classInfoMsgVo.getOperatorType()){
			dataLogVo.setOperatorType(classInfoMsgVo.getOperatorType().toString());
		}
		dataLogVo.setErrorMsg(LogMsgUtils.SubErrorMsg(errorMsg));
		dataLogVo.setDataStatus(dataStatus);
		dataLogVo.setId(IdGen.uuid());
		dataLogVo.setObjId(classInfoMsgVo.getId());
		dataLogVo.setDataJson(JsonUtils.ojbTojson(classInfoMsgVo));
		return dataLogVo;
	}
	
	/**
	 * 获取机构发布的时间配置
	 * config_data
	 * @return
	 */
	protected ConfigDataVo getPublishConfig() {
		ConfigDataVo queryBean = new ConfigDataVo(DataConstants.class_publish);
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
	public void subscribeClassList(){
		logger.info("start-------------开始消费数据----------------");
		//1.获取数据
		String serviceCode_d = ConfigDictUtils.getDictValue(DataConstants.serviceCode_class_d, DataConstants.serviceCode);
		String result = DataMsgUtils.subscribeData(serviceCode_d);//订阅的服务code
		if(JsonUtils.isJson(result)){
			//2.解析List<String>
			List<String> resultList = (List<String>) JsonUtils.jsonToObj(result, List.class);
			System.out.println(JsonUtils.ojbTojson(resultList));
			List<ClassInfoMsgVo> msgList = Lists.newArrayList();
			List<ClassInfoMsgVo> failList = Lists.newArrayList();
			for(String object : resultList){
				ClassInfoMsgVo classInfoMsgVo = (ClassInfoMsgVo) JsonUtils.jsonToObj(object, ClassInfoMsgVo.class);
				//订阅数据中的班级创建时间是毫秒数，需要转换成日期格式的字符串
				classInfoMsgVo.setFoundTime(millisecondToDateString(classInfoMsgVo.getFoundTime()));
				// 2.1.执行具体的业务操作
				boolean flag = subscribeOperator(classInfoMsgVo);
				if(flag){
					msgList.add(classInfoMsgVo);
				}else{
					failList.add(classInfoMsgVo);
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
	
	public boolean subscribeOperator(ClassInfoMsgVo classInfoMsgVo){
		try{
			if(DataMsgVo.OPER_INSERT == classInfoMsgVo.getOperatorType()){
				classInfoMsgDao.insert(classInfoMsgVo);
			}else if (DataMsgVo.OPER_UPDATE == classInfoMsgVo.getOperatorType()) {
				classInfoMsgDao.update(classInfoMsgVo);
			} else if (DataMsgVo.OPER_DELETE == classInfoMsgVo.getOperatorType()) {
				classInfoMsgDao.delete(classInfoMsgVo);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	//将毫秒数转换成时间格式
	public String millisecondToDateString(String millisecond){
		if(null==millisecond || millisecond==""){
			return millisecond;
		}
		try{
			Date d = new Date(Long.parseLong(millisecond));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(d);
		}catch(Exception e){
			return millisecond;
		}
	}
}
