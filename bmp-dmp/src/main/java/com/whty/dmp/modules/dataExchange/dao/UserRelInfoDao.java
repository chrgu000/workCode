package com.whty.dmp.modules.dataExchange.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.modules.dataExchange.entity.UserRelInfoVo;

/**
 * 账号信息-dao
 * 发布操作(查询：新增列表、更新列表、删除列表)
 * 订阅操作(新增、更新、删除)本地数据库
 * @author cjp
 * @date 2016年9月12日
 */
@Repository
public interface UserRelInfoDao {
	
	/**
	 * 查询单个
	 * @param objBean
	 * @return
	 */
	public UserRelInfoVo selectOne(UserRelInfoVo objBean);
	
	/**
	 * 查询新增列表
	 * @param objBean
	 * @return
	 */
	public List<UserRelInfoVo> selectCreateList(UserRelInfoVo objBean);
	
	/**
	 * 查询更新列表
	 * @param objBean
	 * @return
	 */
	public List<UserRelInfoVo> selectUpdateList(UserRelInfoVo objBean);
	
	/**
	 * 删除单个
	 * @param objBean
	 * @return
	 */
	public int delete(UserRelInfoVo objBean);
	
	/**
	 * 插入单个
	 * @param objBean
	 * @return
	 */
	public int insert(UserRelInfoVo objBean);
	
	/**
	 * 更新单个
	 * @param objBean
	 * @return
	 */
	public int update(UserRelInfoVo objBean);
	
}
