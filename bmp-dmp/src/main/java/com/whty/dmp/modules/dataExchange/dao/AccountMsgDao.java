package com.whty.dmp.modules.dataExchange.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.modules.dataExchange.entity.AccountMsgVo;

/**
 * 账号信息-dao
 * 发布操作(查询：新增列表、更新列表、删除列表)
 * 订阅操作(新增、更新、删除)本地数据库
 * @author cjp
 * @date 2016年9月12日
 */
@Repository
public interface AccountMsgDao {
	
	/**
	 * 查询单个
	 * @param objBean
	 * @return
	 */
	public AccountMsgVo selectOne(AccountMsgVo objBean);
	
	/**
	 * 查询新增列表
	 * @param objBean
	 * @return
	 */
	public List<AccountMsgVo> selectCreateList(AccountMsgVo objBean);
	
	/**
	 * 查询更新列表
	 * @param objBean
	 * @return
	 */
	public List<AccountMsgVo> selectUpdateList(AccountMsgVo objBean);
	
	/**
	 * 删除单个
	 * @param objBean
	 * @return
	 */
	public int delete(AccountMsgVo objBean);
	
	/**
	 * 插入单个
	 * @param objBean
	 * @return
	 */
	public int insert(AccountMsgVo objBean);
	
	/**
	 * 更新单个
	 * @param objBean
	 * @return
	 */
	public int update(AccountMsgVo objBean);
	
}
