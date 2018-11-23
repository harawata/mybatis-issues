package test;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                //Cannot intercept the query method of 6 parameters now
                //This PR can solve this problem
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
                //The current version can only intercept the following method
                //args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
)
public class QueryInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        //It is often necessary to use BoundSql in the interceptor, which is very inconvenient now.
        BoundSql boundSql = (BoundSql) args[5];
        //This is just a simple example and has no practical meaning.
        String newSql = boundSql.getSql().replace("*", "id, concat('test-', name) as name");
        MetaObject metaObject = SystemMetaObject.forObject(boundSql);
        //replace sql
        metaObject.setValue("sql", newSql);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
