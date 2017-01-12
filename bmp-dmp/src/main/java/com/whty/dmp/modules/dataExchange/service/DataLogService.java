package com.whty.dmp.modules.dataExchange.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.dmp.modules.dataExchange.dao.DataPublishLogDao;
import com.whty.dmp.modules.dataExchange.dao.DataSubscribeLogDao;
import com.whty.dmp.modules.dataExchange.entity.DataLogVo;

/**
 * 数据日志服务类
 * 
 * @author cjp
 * @date 2016年9月23日
 */
@Service
public class DataLogService {
	
	@Autowired
	private DataSubscribeLogDao dataSubscribeLogDao; //数据订阅
	@Autowired
	private DataPublishLogDao dataPublishLogDao; //数据发布
	
	
	/**
	 * 查询发布日志
	 * @param queryBean
	 * @return
	 */
	public List<DataLogVo> selectPublishLog(DataLogVo queryBean){
		List<DataLogVo> list = dataPublishLogDao.selectList(queryBean);
		return list;
	}
	
	/**
	 * 查询订阅日志
	 * @param queryBean
	 * @return
	 */
	public List<DataLogVo> selectSubscribeLog(DataLogVo queryBean){
		List<DataLogVo> list = dataSubscribeLogDao.selectList(queryBean);
		return list;
	}
	
	/**
	 * 插入订阅数据的日志
	 * @param objBean
	 * @return
	 */
	public int insertSubscribeLog(DataLogVo objBean){
		int count = dataSubscribeLogDao.insert(objBean);
		return count;
	}
	
	/**
	 * 插入发布数据的日志
	 * @param objBean
	 * @return
	 */
	public int insertPublishLog(DataLogVo objBean){
		int count = dataPublishLogDao.insert(objBean);
		return count;
	}
	
	/**
	 * 查看数据详情
	 * @param queryBean
	 * @param type 0 发布、1:订阅
	 * @return
	 */
	public DataLogVo selectDataLog(DataLogVo queryBean,String type){
		DataLogVo data;
		if("0".equals(type)){
			data = dataPublishLogDao.selectOne(queryBean);
		}else{
			data = dataSubscribeLogDao.selectOne(queryBean);
		}
		return data;
	}
	
	/**
	 * 批量插入数据
	 * @param list
	 * @param type 0 发布、1:订阅
	 */
	public void insertDataLog(List<DataLogVo> list,String type){
		if("0".equals(type)){
			for(DataLogVo objBean:list){
				insertPublishLog(objBean);
			}
		}else{
			for(DataLogVo objBean:list){
				insertSubscribeLog(objBean);
			}
		}
	}
	
	
}
