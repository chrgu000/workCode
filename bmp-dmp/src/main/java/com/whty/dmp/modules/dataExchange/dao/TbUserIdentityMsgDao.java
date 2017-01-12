package com.whty.dmp.modules.dataExchange.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.modules.dataExchange.entity.DataLogVo;
import com.whty.dmp.modules.dataExchange.entity.TbUserIdentityMsgVo;

/**
 * 账号信息-dao
 * 发布操作(查询：新增列表、更新列表、删除列表)
 * 订阅操作(新增、更新、删除)本地数据库
 * @author cjp
 * @date 2016年9月12日
 */
@Repository
public interface TbUserIdentityMsgDao {
	
	/**
	 * 查询单个
	 * @param objBean
	 * @return
	 */
	public TbUserIdentityMsgVo selectOne(TbUserIdentityMsgVo objBean);
	
	/**
	 * 查询列表
	 * @param objBean
	 * @return
	 */
	public List<TbUserIdentityMsgVo> selectList(TbUserIdentityMsgVo objBean);
		
	/**
	 * 删除单个
	 * @param objBean
	 * @return
	 */
	public int delete(TbUserIdentityMsgVo objBean);
	
	/**
	 * 插入单个
	 * @param objBean
	 * @return
	 */
	public int insert(TbUserIdentityMsgVo objBean);
	
	/**
	 * 更新单个
	 * @param objBean
	 * @return
	 */
	public int update(TbUserIdentityMsgVo objBean);
	
	/**
	 * 批量修改数据标识状态
	 * @param list
	 */
	public int updateFlagBatch(List<TbUserIdentityMsgVo> list);

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
	public List<TbUserIdentityMsgVo> selectDeleteList(TbUserIdentityMsgVo queryBean);

	/**
	 * 批量修改删除备份表中的数据标识状态
	 * @param list
	 * @return
	 */
	public void updateDelFlagBatch(List<TbUserIdentityMsgVo> list);

	/**
	 * 修改删除备份表中的数据标识状态
	 * @param dataLogVo
	 * @return
	 */
	public void updateDelFlagSingle(DataLogVo dataLogVo);
	
}
