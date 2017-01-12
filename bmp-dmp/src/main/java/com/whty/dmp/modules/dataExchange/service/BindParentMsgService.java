package com.whty.dmp.modules.dataExchange.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.dmp.modules.dataExchange.common.DataMsgVo;
import com.whty.dmp.modules.dataExchange.dao.BindParentMsgDao;
import com.whty.dmp.modules.dataExchange.entity.BindParentMsgVo;

/**
 * 家长学生关系信息处理类
 * @author zhangmingxing
 * @date 2016年9月12日
 */
@Service
public class BindParentMsgService {
	
	@Autowired
	private BindParentMsgDao bindParentMsgDao;
	
	/**
	 * 订阅数据服务
	 * @param dataMsgVo
	 * @return
	 */
	public Integer subscribeSingle(DataMsgVo dataMsgVo){
		BindParentMsgVo objBean = (BindParentMsgVo) dataMsgVo.getObj();
		Date time = new Date();
		int num = 0;
		if(DataMsgVo.OPER_INSERT == dataMsgVo.getOperatorType()){
			objBean.setCreateTime(time);
			num = bindParentMsgDao.insert(objBean);
		}else if(DataMsgVo.OPER_UPDATE == dataMsgVo.getOperatorType()){
			objBean.setUpdateTime(time);
			num = bindParentMsgDao.update(objBean);
		}else if(DataMsgVo.OPER_DELETE == dataMsgVo.getOperatorType()){
			num = bindParentMsgDao.delete(objBean);
		}
		return num;
	}
}
