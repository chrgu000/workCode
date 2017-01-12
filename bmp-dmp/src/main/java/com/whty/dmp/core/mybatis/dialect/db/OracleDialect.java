package com.whty.dmp.core.mybatis.dialect.db;

import com.whty.dmp.core.mybatis.dialect.Dialect;

/**
 * Oracle方言
 * @author cjp 2016年6月24日
 */
public class OracleDialect implements Dialect {

	@Override
	public boolean supportLimit() {
		// TODO 支持分页
		return true;
	}

	@Override
	public String getLimitSql(String sql, int start, int pageSize) {
		// TODO 组装分页数据
		sql = sql.trim();
		StringBuilder strbuf = new StringBuilder(sql.length()+100);
		if(start > 0 ){
			strbuf.append("select * from (select row_.*,rownum rownum_ from ( ");
		}else {
			strbuf.append("select * from ( ");
		}
		strbuf.append(sql);
		if(start > 0 ){
			int rownum = start + pageSize;
			strbuf.append(" ) row_ where rownum <= "+rownum+" ) where rownum_ > ").append(start);
		}else{
			strbuf.append(" ) where rownum <= "+pageSize);
		}
		return strbuf.toString();
	}

}
