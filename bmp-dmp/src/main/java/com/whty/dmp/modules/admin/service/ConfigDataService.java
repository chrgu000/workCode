package com.whty.dmp.modules.admin.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.dmp.modules.admin.dao.ConfigDataDao;
import com.whty.dmp.modules.admin.entity.ConfigDataVo;
import com.whty.dmp.modules.admin.utils.ConfigDictUtils;
import com.whty.dmp.utils.IdGen;

/**
 * 发布订阅数据项-service
 * @author cjp
 * @date 2016年9月22日
 */
@Service
public class ConfigDataService {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigDictService.class);
	
	@Autowired
	private ConfigDataDao configDataDao;
	
	public List<ConfigDataVo> selectList(ConfigDataVo objBean){
		List<ConfigDataVo> list = configDataDao.selectList(objBean);
		return list;
	}

	public int insert(ConfigDataVo objBean) {
		objBean.setId(IdGen.uuid());
		int size = configDataDao.insert(objBean);
		return size;
	}
	
	public int update(ConfigDataVo objBean) {
		int size = configDataDao.update(objBean);
		return size;
	}
	
	public int delete(ConfigDataVo objBean) {
		int size = configDataDao.delete(objBean);
		return size;
	}
	
	public ConfigDataVo findOne(ConfigDataVo queryBean){
		ConfigDataVo objBean = configDataDao.selectOne(queryBean);
		return objBean;
	}
}
