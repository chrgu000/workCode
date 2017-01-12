package com.whty.dmp.modules.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.modules.admin.entity.ConfigDictVo;


/**
 * 数据配置字典-dao
 * @author cjp
 * @date 2016年9月21日
 */
@Repository
public interface ConfigDictDao {

	public ConfigDictVo selectOne(ConfigDictVo queryBean);
	
	public List<ConfigDictVo> selectList(ConfigDictVo queryBean);
	
	public int delete(ConfigDictVo queryBean);
	
	public int insert(ConfigDictVo queryBean);
	
	public int update(ConfigDictVo queryBean);
	
	public List<String> queryTypeList(ConfigDictVo queryBean);
}
