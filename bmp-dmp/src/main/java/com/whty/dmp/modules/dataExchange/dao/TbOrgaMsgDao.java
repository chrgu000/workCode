package com.whty.dmp.modules.dataExchange.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.modules.dataExchange.entity.TbOrgaMsgVo;

/**
 * 机构信息-dao
 * @author cjp
 * @date 2016年9月23日
 */
@Repository
public interface TbOrgaMsgDao {
	
	/**
	 * 查询单个
	 * @param objBean
	 * @return
	 */
	public TbOrgaMsgVo selectOne(TbOrgaMsgVo objBean);
	
	/**
	 * 查询列表
	 * @param objBean
	 * @return
	 */
	public List<TbOrgaMsgVo> selectList(TbOrgaMsgVo objBean);
		
	/**
	 * 删除单个
	 * @param objBean
	 * @return
	 */
	public int delete(TbOrgaMsgVo objBean);
	
	/**
	 * 插入单个
	 * @param objBean
	 * @return
	 */
	public int insert(TbOrgaMsgVo objBean);
	
	/**
	 * 更新单个
	 * @param objBean
	 * @return
	 */
	public int update(TbOrgaMsgVo objBean);

	/**
	 * 批量修改数据标识状态
	 * @param list
	 */
	public int updateFlagBatch(List<TbOrgaMsgVo> list);

	/**
	 * 修改数据标识状态
	 * @param dataLogVo
	 */
	public int updateFlagSingle(DataLogVo dataLogVo);

	/**
	 * 查询删除未发布的数据
	 * @param objBean
	 * @return
	 */
	public List<TbOrgaMsgVo> selectDeleteList(TbOrgaMsgVo objBean);

	/**
	 * 批量修改删除备份表中的数据标识状态
	 * @param list
	 * @return
	 */
	public int updateDelFlagBatch(List<TbOrgaMsgVo> list);

	/**
	 * 修改删除备份表中的数据标识状态
	 * @param dataLogVo
	 * @return
	 */
	public int updateDelFlagSingle(DataLogVo dataLogVo);
	
}
