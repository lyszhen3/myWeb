package com.lin.data.core.mapperHelper;

import com.lin.data.base.MoreSiteBean;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;


public class MapperTemplate {

    private Map<String,Method> methodMap=new HashMap<String, Method>();
    private Class<?> mapperClass;
    private MapperHelper mapperHelper;

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY=new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY=new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY=new DefaultReflectorFactory();

    public MapperTemplate(Class<?> mapperClass,MapperHelper mapperHelper){
        this.mapperClass=mapperClass;
        this.mapperHelper=mapperHelper;
    }

    public String dynamicSQL(Object record){
        return "dynamicSQL";
    }

    public String getIDENTITY() {
        return this.mapperHelper.getIDENTITY();
    }

    public boolean getBEFORE() {
        return this.mapperHelper.getBEFORE();
    }
    public String getUUID() {
        return this.mapperHelper.getUUID();
    }

    protected SqlNode getIfCacheNotNull(EntityHelper.EntityColumn column, SqlNode columnNode)
    {
        return new IfSqlNode(columnNode, column.getProperty() + "_cache != null ");
    }

    protected SqlNode getIfCacheIsNull(EntityHelper.EntityColumn column, SqlNode columnNode)
    {
        return new IfSqlNode(columnNode, column.getProperty() + "_cache == null ");
    }

    protected String getSeqNextVal(EntityHelper.EntityColumn column)
    {
        return MessageFormat.format(this.mapperHelper.getSeqFormat(), new Object[]{column.getSequenceName(), column.getColumn(), column.getProperty()});
    }


    protected SqlNode getIfIsNull(EntityHelper.EntityColumn column, SqlNode columnNode)
    {
        return new IfSqlNode(columnNode, column.getProperty() + " == null ");
    }
    /**
     * 得到查询的返回结果
     * @param ms
     * @return
     */
    public Class<?> getSelectReturnType(MappedStatement ms){
        String msId=ms.getId();
        Class mapperClass=getMapperClass(msId);
        Type[] types=mapperClass.getGenericInterfaces();
        for(Type type:types){
            if(type instanceof ParameterizedType){
                ParameterizedType t= (ParameterizedType) type;
                if(t.getRawType()==this.mapperClass){
                    Class returnType= (Class) t.getActualTypeArguments()[0];
                    return returnType;
                }
            }
        }
        throw new RuntimeException("无法获取Mapper<T>泛型："+msId);
    }

    protected List<ParameterMapping> getPrimaryKeyParameterMappings(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        Set<EntityHelper.EntityColumn> entityColumns = EntityHelper.getPKColumns(entityClass);
        List parameterMappings = new ArrayList();
        for (EntityHelper.EntityColumn column : entityColumns) {
            ParameterMapping.Builder builder = new ParameterMapping.Builder(ms.getConfiguration(), column.getProperty(), column.getJavaType());
            builder.mode(ParameterMode.IN);
            parameterMappings.add(builder.build());
        }
        return parameterMappings;
    }

    protected void newSelectKeyMappedStatement(MappedStatement ms, EntityHelper.EntityColumn column)
    {
        String keyId = ms.getId() + "!selectKey";
        if (ms.getConfiguration().hasKeyGenerator(keyId)) {
            return;
        }
        Class entityClass = getSelectReturnType(ms);

        Configuration configuration = ms.getConfiguration();
        KeyGenerator keyGenerator = null;
        Boolean executeBefore = Boolean.valueOf(getBEFORE());
        String IDENTITY = (column.getGenerator() == null) || (column.getGenerator().equals("")) ? getIDENTITY() : column.getGenerator();
        if (IDENTITY.equalsIgnoreCase("JDBC")) {
            keyGenerator = new Jdbc3KeyGenerator();
        } else {
            SqlSource sqlSource = new RawSqlSource(configuration, IDENTITY, entityClass);

            MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, keyId, sqlSource, SqlCommandType.SELECT);
            statementBuilder.resource(ms.getResource());
            statementBuilder.fetchSize(null);
            statementBuilder.statementType(StatementType.STATEMENT);
            statementBuilder.keyGenerator(new NoKeyGenerator());
            statementBuilder.keyProperty(column.getProperty());
            statementBuilder.keyColumn(null);
            statementBuilder.databaseId(null);
            statementBuilder.lang(configuration.getDefaultScriptingLanuageInstance());
            statementBuilder.resultOrdered(false);
            statementBuilder.resulSets(null);
            statementBuilder.timeout(configuration.getDefaultStatementTimeout());

            List parameterMappings = new ArrayList();
            ParameterMap.Builder inlineParameterMapBuilder = new ParameterMap.Builder(
                    configuration,
                    statementBuilder.id() + "-Inline",
                    entityClass,
                    parameterMappings);
            statementBuilder.parameterMap(inlineParameterMapBuilder.build());

            List resultMaps = new ArrayList();
            ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(
                    configuration,
                    statementBuilder.id() + "-Inline",
                    column.getJavaType(),
                    new ArrayList(),
                    null);
            resultMaps.add(inlineResultMapBuilder.build());
            statementBuilder.resultMaps(resultMaps);
            statementBuilder.resultSetType(null);

            statementBuilder.flushCacheRequired(false);
            statementBuilder.useCache(false);
            statementBuilder.cache(null);

            MappedStatement statement = statementBuilder.build();
            configuration.addMappedStatement(statement);

            MappedStatement keyStatement = configuration.getMappedStatement(keyId, false);
            keyGenerator = new SelectKeyGenerator(keyStatement, executeBefore.booleanValue());
            configuration.addKeyGenerator(keyId, keyGenerator);
        }
        try
        {
            MetaObject msObject = forObject(ms);
            msObject.setValue("keyGenerator", keyGenerator);
            msObject.setValue("keyProperties", new String[] { column.getProperty() });
        }
        catch (Exception localException) {
        }
    }
    /**
     * 为template添加方法
     * @param methodName
     * @param method
     */
    public void addMethodMap(String methodName,Method method){
        this.methodMap.put(methodName,method);
    }
    /**
     * 查方法是否被支持
     * @param msId
     * @return
     */
    public boolean supportMethod(String msId){
        Class mapperClass=getMapperClass(msId);
        if(mapperClass!=null&&this.mapperClass.isAssignableFrom(mapperClass)){//执行的类存在
            String methodName=getMethodName(msId);
            return methodMap.get(methodName)!=null;
        }
        return false;
    }

    public static MetaObject forObject(Object object){
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
    }

    /**
     * 取元素不为空
     * @param column
     * @param columnNode
     * @return
     */
    protected SqlNode getIfNotNull(EntityHelper.EntityColumn column,SqlNode columnNode){
        return getIfNotNull(column, columnNode, false);
    }

    /**
     * 如果empty为true，要验证空字符串
     * @param column
     * @param columnNode
     * @param empty
     * @return
     */
    protected SqlNode getIfNotNull(EntityHelper.EntityColumn column,SqlNode columnNode,boolean empty){
        return new IfSqlNode(columnNode,column.getProperty()+" != null "+(empty ? " and "+column.getProperty()+" !=''":""));
    }

    /**
     * 得到xx库.xx表
     * @param entityClass
     * @return
     */
    protected String tableName(Class<?> entityClass){
        return this.mapperHelper.getTableName(entityClass);
    }

    /**
     * 得到xx库.xx表,检测分站情况 返回 xxx_${webSite}
     * @param entityClass
     * @return
     */
    protected SqlNode tableNameCheckFen(Configuration con,Class<?> entityClass){
        if(MoreSiteBean.class.isAssignableFrom(entityClass)){
            return new TextSqlNode(this.mapperHelper.getTableName(entityClass)+"_${webSite}");
        }else{
            return new StaticTextSqlNode(this.mapperHelper.getTableName(entityClass));
        }
    }

    /**
     * 得到xx库.xx表,检测分站情况,给update用的，返回 xxx_${example.webSite}
     * @param entityClass
     * @return
     */
    protected SqlNode tableNameCheckFenForExam(Configuration con,Class<?> entityClass){
        if(MoreSiteBean.class.isAssignableFrom(entityClass)){
            return new TextSqlNode(this.mapperHelper.getTableName(entityClass)+"_${example.webSite}");
        }else{
            return new StaticTextSqlNode(this.mapperHelper.getTableName(entityClass));
        }
    }

    /**
     * 得到xx库.xx表,检测分站情况,给update用的，返回 xxx_${id.webSite}
     * @param entityClass
     * @return
     */
    protected SqlNode tableNameCheckFenForBean(Configuration con,Class<?> entityClass){
        if(MoreSiteBean.class.isAssignableFrom(entityClass)){
            return new TextSqlNode(this.mapperHelper.getTableName(entityClass)+"_${id.webSite}");
        }else{
            return new StaticTextSqlNode(this.mapperHelper.getTableName(entityClass));
        }
    }
    /**
     * 拼接元素相等
     * @param column
     * @param first
     * @return
     */
    protected SqlNode getColumnEqualsProperty(EntityHelper.EntityColumn column,boolean first){
        return new StaticTextSqlNode((first?"":" AND ")+column.getColumn()+" = #{"+column.getProperty()+"}");
    }

    /**
     * 拼接元素相等,多站点用的
     * @param column
     * @param first
     * @return
     */
    protected SqlNode getColumnEqualsPropertyMore(EntityHelper.EntityColumn column,boolean first,boolean ismore){
        if(ismore){
            return new StaticTextSqlNode((first?"":" AND ")+column.getColumn()+" = #{id."+column.getProperty()+"}");
        }else{
            return new StaticTextSqlNode((first?"":" AND ")+column.getColumn()+" = #{id}");
        }
    }

    /**
     * 查全部条件列
     * @param entityClass
     * @return
     */
    protected SqlNode getAllIfColumnNode(Class<?> entityClass){
        Set<EntityHelper.EntityColumn> columnList=EntityHelper.getColumns(entityClass);
        List ifNodes=new ArrayList();
        boolean first=true;

        for(EntityHelper.EntityColumn colunm:columnList){
            ifNodes.add(getIfNotNull(colunm,getColumnEqualsProperty(colunm,first),this.mapperHelper.isNotEmpty()));
            first=false;
        }
        return new MixedSqlNode(ifNodes);
    }

    /**
     * 主键按条件查,如果是null就null
     * @param entityClass
     * @return
     */
    protected SqlNode getPkIfColumnNode(Class<?> entityClass){
        Set<EntityHelper.EntityColumn> columnList=EntityHelper.getPKColumns(entityClass);
        List ifNodes=new ArrayList();
        boolean first=true;

        for(EntityHelper.EntityColumn colunm:columnList){
            ifNodes.add(getColumnEqualsProperty(colunm, first));
            first=false;
        }
        return new MixedSqlNode(ifNodes);
    }

    /**
     * 主键按条件查,如果是null就null,多条件的
     * @param entityClass
     * @return
     */
    protected SqlNode getPkIfColumnNodeMore(Class<?> entityClass){
        Set<EntityHelper.EntityColumn> columnList=EntityHelper.getPKColumns(entityClass);
        List ifNodes=new ArrayList();
        boolean first=true;

        boolean ismore=MoreSiteBean.class.isAssignableFrom(entityClass);
        for(EntityHelper.EntityColumn colunm:columnList){
            ifNodes.add(getColumnEqualsPropertyMore(colunm, first, ismore));
            first=false;
        }
        return new MixedSqlNode(ifNodes);
    }
    /**
     * 设置返回值类型
     * @param ms
     * @param entityClass
     */
    protected void setResultType(MappedStatement ms,Class<?> entityClass){
        ResultMap resultMap=(ResultMap)ms.getResultMaps().get(0);
        MetaObject metaObject=forObject(resultMap);
        metaObject.setValue("type",entityClass);
    }
    protected void setSqlSource(MappedStatement ms,SqlSource sqlSource){
        MetaObject msObject=forObject(ms);
        msObject.setValue("sqlSource",sqlSource);
    }

    public void setSqlSource(MappedStatement ms) throws Exception{
        if(mapperClass==getMapperClass(ms.getId())){
            if(mapperHelper.isSpring4()){
                return;
            }
            if(this.mapperHelper.isSpring()){
                throw new RuntimeException("Spring 4.x.x 及以上版本支持泛型注入，您当前的Spring版本为"+mapperHelper.getSpringVersion()+",不能使用泛型注入，因此在配置MapperScannerConfigurer时,不要扫描通用Mapper接口类,也不要在您Mybatis的xml配置文件中的<mappers>中指定通用Mapper接口类");
            }
            throw new RuntimeException("请不要在您Mybatis的xml配置文件中的<mappers>中指定通用Mapper接口类");
        }
        Method method=methodMap.get(getMethodName(ms));

        try {
            if(method.getReturnType()==Void.TYPE){
                method.invoke(this,new Object[]{ms});
            }else if(SqlNode.class.isAssignableFrom(method.getReturnType())){
                SqlNode sqlNode=(SqlNode)method.invoke(this,new Object[]{ms});
                DynamicSqlSource dynamicSqlSource=new DynamicSqlSource(ms.getConfiguration(),sqlNode);
                setSqlSource(ms,dynamicSqlSource);
            }else{
                new RuntimeException("自定义Mapper方法返回类型错误，可选的返回类型为void和SqlNode");
            }
        } catch (IllegalAccessException e) {
           throw new RuntimeException(e);
        }catch (InvocationTargetException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取mapper类的实际类型
     * @param ms
     * @return
     */
    public Class<?> getSelectReturyType(MappedStatement ms){
        String msId=ms.getId();
        Class mapperClass=getMapperClass(msId);
        Type[] types=mapperClass.getGenericInterfaces();
        for(Type type:types){
            if(type instanceof ParameterizedType){
               ParameterizedType t=(ParameterizedType)type;
                if(t.getRawType()==this.mapperClass){
                    Class returnType=(Class)t.getActualTypeArguments()[0];
                    return returnType;
                }
            }
        }
        throw new RuntimeException("无法获取Mapper<T>泛型类型："+msId);
    }
    /**
     * 返回Mapper的类路径
     * @param msId
     * @return
     */
    public static Class<?> getMapperClass(String msId){
        if(msId.indexOf(".")==-1){
            throw new RuntimeException("当前MappedStatement的id="+msId+",不符合MappedStatement的规则！");
        }
        String mapperClassStr=msId.substring(0, msId.lastIndexOf("."));
        try{
            return Class.forName(mapperClassStr);
        }catch (ClassNotFoundException e){}
        return null;
    }

    /**
     * 得到statement对应的方法名
     * @param ms
     * @return
     */
    public static String getMethodName(MappedStatement ms){
        return getMethodName(ms.getId());
    }

    /**
     * 得到statement对应的方法名
     * @param msId
     * @return
     */
    public static String getMethodName(String msId){
        return msId.substring(msId.lastIndexOf(".") + 1);
    }

    /**
     * 取sql条件
     * @param configuration
     * @return
     */
    public IfSqlNode ExampleValidSqlNode(Configuration configuration) {
        List whenSqlNodes = new ArrayList();
        IfSqlNode noValueSqlNode = new IfSqlNode(new TextSqlNode(" and ${criterion.condition}"), "criterion.noValue");
        whenSqlNodes.add(noValueSqlNode);
        IfSqlNode singleValueSqlNode = new IfSqlNode(new TextSqlNode(" and ${criterion.condition} #{criterion.value}"), "criterion.singleValue");
        whenSqlNodes.add(singleValueSqlNode);
        IfSqlNode betweenValueSqlNode = new IfSqlNode(new TextSqlNode(" and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}"), "criterion.betweenValue");
        whenSqlNodes.add(betweenValueSqlNode);

        List listValueContentSqlNodes = new ArrayList();
        listValueContentSqlNodes.add(new TextSqlNode(" and ${criterion.condition}"));
        ForEachSqlNode listValueForEachSqlNode = new ForEachSqlNode(configuration, new StaticTextSqlNode("#{listItem}"), "criterion.value", null, "listItem", "(", ")", ",");
        listValueContentSqlNodes.add(listValueForEachSqlNode);
        IfSqlNode listValueSqlNode = new IfSqlNode(new MixedSqlNode(listValueContentSqlNodes), "criterion.listValue");
        whenSqlNodes.add(listValueSqlNode);

        ChooseSqlNode chooseSqlNode = new ChooseSqlNode(whenSqlNodes, null);

        ForEachSqlNode criteriaSqlNode = new ForEachSqlNode(configuration, chooseSqlNode, "criteria.criteria", null, "criterion", null, null, null);

        TrimSqlNode trimSqlNode = new TrimSqlNode(configuration, criteriaSqlNode, "(", "and", ")", null);
        IfSqlNode validSqlNode = new IfSqlNode(trimSqlNode, "criteria.valid");
        return validSqlNode;
    }

    public WhereSqlNode exampleWhereClause(Configuration configuration)
    {
        ForEachSqlNode forEachSqlNode = new ForEachSqlNode(configuration, ExampleValidSqlNode(configuration), "oredCriteria", null, "criteria", null, null, " or ");
        WhereSqlNode whereSqlNode = new WhereSqlNode(configuration, forEachSqlNode);
        return whereSqlNode;
    }

    public WhereSqlNode updateByExampleWhereClause(Configuration configuration)
    {
        ForEachSqlNode forEachSqlNode = new ForEachSqlNode(configuration, ExampleValidSqlNode(configuration), "example.oredCriteria", null, "criteria", null, null, " or ");
        WhereSqlNode whereSqlNode = new WhereSqlNode(configuration, forEachSqlNode);
        return whereSqlNode;
    }
}
