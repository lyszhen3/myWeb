package com.lin.data.base;



import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

import java.util.Properties;

/**
 * Mybatis拦截器
 * Created by zhaohongbo on 15/9/15.
 */
@Intercepts({@org.apache.ibatis.plugin.Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class,org.apache.ibatis.session.RowBounds.class,org.apache.ibatis.session.ResultHandler.class}),@org.apache.ibatis.plugin.Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class})})
public class MapperInterceptor implements Interceptor {


    private final MapperHelper mapperHelper=new MapperHelper();
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] objects=invocation.getArgs();
        MappedStatement ms=(MappedStatement)objects[0];
        String msId=ms.getId();
        if(mapperHelper.isMapperMethod(msId)){
            if(ms.getSqlSource() instanceof ProviderSqlSource){
                mapperHelper.setSqlSource(ms);
            }
        }
        return invocation.proceed();
    }


    @Override
    public Object plugin(Object target) {
        if(target instanceof Executor){
            return Plugin.wrap(target,this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        mapperHelper.setProperties(properties);
    }
}
