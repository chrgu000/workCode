package com.whty.dmp.modules.dataExchange.common;

/**
 * 静态常量
 * 数据来源、数据状态
 * @author zhangmingxing
 *
 */
public class StatusConstants {
	/**
	 * 数据来源-本地
	 */
	public static final String DATA_EX_FROM_LOCAL="0";
	/**
	 * 数据来源-外部
	 */
	public static final String DATA_EX_FROM_EXTERNAL="1";
	
	
	/**
	 * 新增未发布
	 */
	public static final String DATA_EX_FLAG_ADD="0"; 
	/**
	 * 修改未发布
	 */
	public static final String DATA_EX_FLAG_EDIT="1";  
	/**
	 * 删除未发布
	 */
	public static final String DATA_EX_FLAG_DELETE="2";
	/**
	 * 已发布,正常发布
	 */
	public static final String DATA_EX_FLAG_PUBLISHED="9";
	
	/**
	 * 操作类型-新增
	 */
	public static final String OPERATOR_TYPE_ADD="1";
	/**
	 * 操作类型-修改
	 */
	public static final String OPERATOR_TYPE_EDIT="2";
	/**
	 * 操作类型-删除
	 */
	public static final String OPERATOR_TYPE_DELETE="3";
}
