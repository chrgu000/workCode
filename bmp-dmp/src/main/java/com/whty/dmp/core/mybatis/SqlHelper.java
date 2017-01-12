package com.whty.dmp.core.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlHelper {

	private static final String ORDER_REGEX = "order\\s*by[\\w|\\W|\\s|\\S]*";

	private static Logger logger = LoggerFactory.getLogger(SqlHelper.class);

	/**
	 * 查询总记录数
	 * @param originalSql 原始sql
	 * @param mappedStatement mapped
	 * @param transaction 事务
	 * @param parameterObject 参数
	 * @param boundSql
	 * @return
	 * @throws SQLException
	 */
	public static int getCount(String originalSql, final MappedStatement mappedStatement, final Transaction transaction,
			final Object parameterObject, final BoundSql boundSql) throws SQLException {
		final String count_sql = "select count(1) from (" + removeOrders(originalSql) + ") temp_count";
		logger.debug("Total count SQL [{}] ", count_sql);
		logger.debug("Total count Parameters: {} ", parameterObject);

		Connection connection = transaction.getConnection();
		PreparedStatement countStmt = connection.prepareStatement(count_sql);
		DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
		handler.setParameters(countStmt);
		ResultSet rs = countStmt.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}
		logger.debug("Total count: {}", count);
		return count;
	}

	private static String removeOrders(String sqlString) {
		Pattern p = Pattern.compile(ORDER_REGEX, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sqlString);
		StringBuffer strbuf = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(strbuf, "");
		}
		m.appendTail(strbuf);
		return strbuf.toString();
	}
}
