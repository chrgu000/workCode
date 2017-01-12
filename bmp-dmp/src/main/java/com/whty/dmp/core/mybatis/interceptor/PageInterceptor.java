package com.whty.dmp.core.mybatis.interceptor;

import java.io.Serializable;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.whty.dmp.core.mybatis.Page;
import com.whty.dmp.core.mybatis.SqlHelper;
import com.whty.dmp.core.mybatis.dialect.Dialect;
import com.whty.dmp.core.mybatis.dialect.db.MysqlDialect;
import com.whty.dmp.core.mybatis.dialect.db.OracleDialect;
import com.whty.dmp.core.mybatis.utils.ReflectUtils;

/**
 * 数据库分页查询拦截器--只拦截Executor的query方法
 * 
 * @author cjp 2016年6月24日
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class PageInterceptor implements Interceptor, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PageInterceptor.class);
	
	private static final String PAGE = "page";
	protected Dialect DIALECT;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final Executor executor = (Executor) invocation.getTarget();
		final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Object parameterObject = boundSql.getParameterObject();
		// 获取分页对象
		Page page = null;
		if (parameterObject != null) {
			page = convertParam(parameterObject);
		}
		// 如果设置了分页
		if (page != null && page.getPageSize() != -1) {
			//集中管理数据源类型
			if (boundSql.getSql() == null || boundSql.getSql().isEmpty()) {
				return null;
			}
			String originalSql = boundSql.getSql().trim();
			//查询总记录数
			page.setCount(SqlHelper.getCount(originalSql, mappedStatement, executor.getTransaction(), parameterObject, boundSql));
			String pageSql = DIALECT.getLimitSql(originalSql, page.getFirstResult(), page.getPageSize());
			invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
			BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), pageSql,
					boundSql.getParameterMappings(), boundSql.getParameterObject());
			// 解决MyBatis 分页foreach 参数失效 start
			if (ReflectUtils.getFieldValue(boundSql, "metaParameters") != null) {
				MetaObject mo = (MetaObject) ReflectUtils.getFieldValue(boundSql, "metaParameters");
				ReflectUtils.setFieldValue(newBoundSql, "metaParameters", mo);
			}
			// 解决MyBatis 分页foreach 参数失效 end
			MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
			invocation.getArgs()[0] = newMs;
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 这种实例化Dialect的方式适应于单一数据配置
	 */
	@Override
	public void setProperties(Properties properties) {
		Dialect dialect = null;
		//1.因为使用了动态数据源，所以采用配置动态切换
    	String dbType = properties.getProperty("dbType");
    	if("oracle".equals(dbType)){
    		dialect = new OracleDialect();
    	}else if("mysql".equals(dbType)){
    		dialect = new MysqlDialect();
    	}
    	if (dialect == null) {
    		log.error("初始化分页拦截器时需要配置属性dbType："+dbType);
            throw new RuntimeException("初始化Mybatis拦截器时，解析 dialect error.");
        }
    	DIALECT = dialect;
	}
	
	private  Page convertParam(Object paramObject) {
    	try {
			if(paramObject instanceof Page){
				return (Page)paramObject;
			} else {
				return (Page)ReflectUtils.getFieldValue(paramObject, PAGE);
			}
		} catch (Exception e) {
			return null;
		}
    }

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
				ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null) {
			for (String keyProperty : ms.getKeyProperties()) {
				builder.keyProperty(keyProperty);
			}
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		return builder.build();
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
	
}
