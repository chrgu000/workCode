package com.whty.dmp.modules.admin.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.dmp.modules.admin.dao.ConfigDictDao;
import com.whty.dmp.modules.admin.entity.ConfigDictVo;
import com.whty.dmp.modules.admin.utils.ConfigDictUtils;
import com.whty.dmp.utils.IdGen;

/**
 * 数据字典-service
 * @author cjp
 * @date 2016年9月21日
 */
@Service
public class ConfigDictService {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigDictService.class);
	
	@Autowired
	private ConfigDictDao configDictDao;
	
	public List<ConfigDictVo> selectList(ConfigDictVo objBean){
		List<ConfigDictVo> list = configDictDao.selectList(objBean);
		return list;
	}

	public int insertDict(ConfigDictVo objBean) {
		Date createTime = new Date();
		objBean.setId(IdGen.uuid());
		objBean.setCreateTime(createTime);
		int size = configDictDao.insert(objBean);
		return size;
	}
	
	public int updateDict(ConfigDictVo objBean) {
		Date updateTime = new Date();
		objBean.setUpdateTime(updateTime);
		int size = configDictDao.update(objBean);
		ConfigDictUtils.remove();
		return size;
	}
	
	public int deleteDict(ConfigDictVo objBean) {
		int size = configDictDao.delete(objBean);
		ConfigDictUtils.remove();
		return size;
	}
	
	public List<String> queryTypeList(){
		List<String> list = configDictDao.queryTypeList(new ConfigDictVo());
		return list;
	}
	
	public ConfigDictVo findOne(ConfigDictVo queryBean){
		ConfigDictVo objBean = configDictDao.selectOne(queryBean);
		return objBean;
	}
}
