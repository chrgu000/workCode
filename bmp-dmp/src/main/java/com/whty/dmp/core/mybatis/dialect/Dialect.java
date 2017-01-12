package com.whty.dmp.core.mybatis.dialect;

/**
 * 数据库日志接口
 * @author cjp 2016年6月24日
 */
public interface Dialect {
	
	/**
	 * 数据库是否支持分页查询
	 * @date 2016年6月24日
	 */
	public boolean supportLimit();
	
	/**
	 *  获取分页sql语句
	 * @date 2016年6月24日
	 * @param sql SQL语句
	 * @param start 开始数目
	 * @param pageSize 每页显示数据
	 * @return 分页查询的sql
	 */
	public String getLimitSql(String sql,int start,int pageSize);
}	
