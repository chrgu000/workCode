package com.whty.dmp.core.mybatis.dialect.db;

import com.whty.dmp.core.mybatis.dialect.Dialect;

public class MysqlDialect implements Dialect{

	@Override
	public boolean supportLimit() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getLimitSql(String sql, int start, int pageSize) {
		 StringBuilder stringBuilder = new StringBuilder(sql);
	        stringBuilder.append(" limit ");
	        if (start > 0) {
	            stringBuilder.append(start).append(",").append(pageSize);
	        } else {
	            stringBuilder.append(pageSize);
	        }
	        return stringBuilder.toString();
	}

}
