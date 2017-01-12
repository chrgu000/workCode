package com.whty.dmp.modules.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.modules.admin.entity.ConfigDataVo;


/**
 * 发布订阅数据配置-dao
 * @author cjp
 * @date 2016年9月22日
 */
@Repository
public interface ConfigDataDao {

	public ConfigDataVo selectOne(ConfigDataVo queryBean);
	
	public List<ConfigDataVo> selectList(ConfigDataVo queryBean);
	
	public int delete(ConfigDataVo queryBean);
	
	public int insert(ConfigDataVo queryBean);
	
	public int update(ConfigDataVo queryBean);
	
}
