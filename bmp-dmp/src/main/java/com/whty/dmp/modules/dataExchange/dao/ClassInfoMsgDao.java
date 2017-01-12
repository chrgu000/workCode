package com.whty.dmp.modules.dataExchange.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.modules.dataExchange.entity.ClassInfoMsgVo;
import com.whty.dmp.modules.dataExchange.entity.DataLogVo;

/**
 * 班级信息-dao
 * 发布操作(查询：新增列表、更新列表、删除列表)
 * 订阅操作(新增、更新、删除)本地数据库
 * @author zhangmingxing
 * @date 2016年9月13日
 */
@Repository
public interface ClassInfoMsgDao {
	
	/**
	 * 查询单个
	 * @param objBean
	 * @return
	 */
	public ClassInfoMsgVo selectOne(ClassInfoMsgVo objBean);
	
	/**
	 * 查询新增列表
	 * @param objBean
	 * @return
	 */
	public List<ClassInfoMsgVo> selectList(ClassInfoMsgVo objBean);
	
	/**
	 * 查询更新列表
	 * @param objBean
	 * @return
	 */
	public List<ClassInfoMsgVo> selectUpdateList(ClassInfoMsgVo objBean);
	
	/**
	 * 删除单个
	 * @param objBean
	 * @return
	 */
	public int delete(ClassInfoMsgVo objBean);
	
	/**
	 * 插入单个
	 * @param objBean
	 * @return
	 */
	public int insert(ClassInfoMsgVo objBean);
	
	/**
	 * 更新单个
	 * @param objBean
	 * @return
	 */
	public int update(ClassInfoMsgVo objBean);
	
	/**
	 * 批量更新数据标识状态
	 * @param list
	 * @return
	 */
	public int updateFlagBatch(List<ClassInfoMsgVo> list);
	
	/**
	 * 更新数据标识状态
	 * @return
	 */
	public int updateFlagSingle(DataLogVo dataLogVo);

	/**
	 * 查询删除未发布的数据
	 * @param objBean
	 * @return
	 */
	public List<ClassInfoMsgVo> selectDeleteList(ClassInfoMsgVo queryBean);

	/**
	 * 批量修改删除备份表中的数据标识状态
	 * @param list
	 * @return
	 */
	public int updateDelFlagBatch(List<ClassInfoMsgVo> list);

	/**
	 * 修改删除备份表中的数据标识状态
	 * @param dataLogVo
	 * @return
	 */
	public int updateDelFlagSingle(DataLogVo dataLogVo);
	
}
