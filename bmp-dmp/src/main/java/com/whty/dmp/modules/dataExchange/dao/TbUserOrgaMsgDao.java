package com.whty.dmp.modules.dataExchange.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.modules.dataExchange.entity.TbUserOrgaMsgVo;

/**
 * 账号信息-dao
 * 发布操作(查询：新增列表、更新列表、删除列表)
 * 订阅操作(新增、更新、删除)本地数据库
 * @author cjp
 * @date 2016年9月12日
 */
@Repository
public interface TbUserOrgaMsgDao {
	
	/**
	 * 查询单个
	 * @param objBean
	 * @return
	 */
	public TbUserOrgaMsgVo selectOne(TbUserOrgaMsgVo objBean);
	
	/**
	 * 查询列表
	 * @param objBean
	 * @return
	 */
	public List<TbUserOrgaMsgVo> selectList(TbUserOrgaMsgVo objBean);
		
	/**
	 * 删除单个
	 * @param objBean
	 * @return
	 */
	public int delete(TbUserOrgaMsgVo objBean);
	
	/**
	 * 插入单个
	 * @param objBean
	 * @return
	 */
	public int insert(TbUserOrgaMsgVo objBean);
	
	/**
	 * 更新单个
	 * @param objBean
	 * @return
	 */
	public int update(TbUserOrgaMsgVo objBean);
	
	/**
	 * 批量修改数据标识状态
	 * @param list
	 */
	public int updateFlagBatch(List<TbUserOrgaMsgVo> list);

	/**
	 * 修改数据标识状态
	 * @param dataLogVo
	 */
	public void updateFlagSingle(DataLogVo dataLogVo);

	/**
	 * 查询删除未发布的数据
	 * @param objBean
	 * @return
	 */
	public List<TbUserOrgaMsgVo> selectDeleteList(TbUserOrgaMsgVo queryBean);

	/**
	 * 批量修改删除备份表中的数据标识状态
	 * @param list
	 * @return
	 */
	public void updateDelFlagBatch(List<TbUserOrgaMsgVo> list);

	/**
	 * 修改删除备份表中的数据标识状态
	 * @param dataLogVo
	 * @return
	 */
	public void updateDelFlagSingle(DataLogVo dataLogVo);
	
}
