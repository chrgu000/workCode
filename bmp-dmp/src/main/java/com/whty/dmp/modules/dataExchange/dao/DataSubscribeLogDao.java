package com.whty.dmp.modules.dataExchange.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.modules.dataExchange.entity.DataLogVo;

/**
 * 数据订阅日志-dao
 * @author cjp
 * @date 2016年9月23日
 */
@Repository
public interface DataSubscribeLogDao {
	
	/**
	 * 查询单个
	 * @param objBean
	 * @return
	 */
	public DataLogVo selectOne(DataLogVo objBean);
	
	/**
	 * 查询列表
	 * @param objBean
	 * @return
	 */
	public List<DataLogVo> selectList(DataLogVo objBean);
	
	/**
	 * 删除单个
	 * @param objBean
	 * @return
	 */
	public int delete(DataLogVo objBean);
	
	/**
	 * 插入单个
	 * @param objBean
	 * @return
	 */
	public int insert(DataLogVo objBean);
	
	/**
	 * 更新单个
	 * @param objBean
	 * @return
	 */
	public int update(DataLogVo objBean);
	
	/**
	 * 删除多个
	 * @param objBean
	 * @return
	 */
	public int deleteList(List<String> list);
	
	/**
	 * 插入多个
	 * @param objBean
	 * @return
	 */
	public int insertList(List<DataLogVo> list);
	
}
