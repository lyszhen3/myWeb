package com.lin.data.core.mapperHelper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.util.*;


public class MapperHelper {
    private Map<Class<?>,MapperTemplate> registerMapper=new HashMap<Class<?>, MapperTemplate>();
    private Map<String,MapperTemplate> msIdCache=new HashMap<String, MapperTemplate>();
    private final Map<String,Boolean> msIdSkip=new HashMap<String, Boolean>();//msid Skip
    private Set<Collection<MappedStatement>> collectionSet=new HashSet<Collection<MappedStatement>>();

    private boolean spring=false;
    private boolean spring4=false;
    private String springVersion;
    private boolean notEmpty=false;

    private List<SqlSession> sqlSessions=new ArrayList<SqlSession>();
    private Config config=new Config();

    public boolean isNotEmpty(){
        return this.notEmpty;
    }
    /**
     *难mapper的方法
     * @param msId
     * @return
     */
    public boolean isMapperMethod(String msId){
        if(msIdSkip.get(msId)!=null){
            return msIdSkip.get(msId);
        }
        for(Map.Entry entry:registerMapper.entrySet()){
            if(((MapperTemplate)entry.getValue()).supportMethod(msId)){
                msIdSkip.put(msId,true);
                return true;
            }
        }
        msIdSkip.put(msId,false);
        return false;
    }

    /**
     * 取得MapperTemplate
     * @param msId
     * @return
     */
    private MapperTemplate getMapperTemplate(String msId){
        MapperTemplate mapperTemplate=null;
        if(msIdCache.get(msId)!=null){
            mapperTemplate=msIdCache.get(msId);
        }else{
            for(Map.Entry entry:registerMapper.entrySet()){
                if(((MapperTemplate)entry.getValue()).supportMethod(msId)){
                    mapperTemplate=(MapperTemplate)entry.getValue();
                    break;
                }
            }
            msIdCache.put(msId,mapperTemplate);
        }

        return mapperTemplate;
    }
    public void setSqlSource(MappedStatement ms){
        MapperTemplate mapperTemplate=getMapperTemplate(ms.getId());

        try {
            if(mapperTemplate!=null){
                mapperTemplate.setSqlSource(ms);
            }
        } catch (Exception e) {
            throw new RuntimeException("调用方法异常:"+e.getMessage());
        }
    }

    public String getSeqFormat()
    {
        if ((this.config.seqFormat != null) && (this.config.seqFormat.length() > 0)) {
            return this.config.seqFormat;
        }
        return "{0}.nextval";
    }
    public String getUUID()
    {
        if ((this.config.UUID != null) && (this.config.UUID.length() > 0)) {
            return this.config.UUID;
        }
        return "@java.util.UUID@randomUUID().toString().replace(\"-\", \"\")";
    }

    public String getIDENTITY()
    {
        if ((this.config.IDENTITY != null) && (this.config.IDENTITY.length() > 0)) {
            return this.config.IDENTITY;
        }

        return IdentityDialect.MYSQL.getIdentityRetrievalStatement();
    }

    public boolean getBEFORE()
    {
        return this.config.BEFORE;
    }

    public void setProperties(Properties properties){
        if(properties == null){
            return;
        }
        String UUID=properties.getProperty("UUID");
        if(UUID!=null&&UUID.length()>0){
            setUUID(UUID);
        }
        String IDENTITY=properties.getProperty("IDENTITY");
        if(IDENTITY!=null&&IDENTITY.length()>0){
            setIDENTITY(IDENTITY);
        }
        String seqFormat=properties.getProperty("seqFormat");
        if(seqFormat!=null&&seqFormat.length()>0){
            setSeqFormat(seqFormat);
        }
        String catalog=properties.getProperty("catalog");
        if(catalog!=null&&catalog.length()>0){
            setCatalog(catalog);
        }
        String schema=properties.getProperty("schema");
        if(schema!=null&&schema.length()>0){
            setSchema(schema);
        }
        String ORDER=properties.getProperty("ORDER");
        if(ORDER!=null&&ORDER.length()>0){
            setOrder(ORDER);
        }
        String notEmpty=properties.getProperty("notEmpty");
        if(notEmpty!=null&&notEmpty.length()>0){
            this.notEmpty=notEmpty.equalsIgnoreCase("TRUE");
        }
        String mapper=properties.getProperty("mappers");
        if(mapper!=null&&mapper.length()>0){
            String[] mappers=mapper.split(",");
            for (String mapperClass:mappers){
                if(mapperClass.length()>0){
                    //注册mapper
                    registerMapper(mapperClass);
                }
            }
        }
    }

    /**
     * 得取库名
     * @return
     */
    public String getPrefix(){
        if(this.config.catalog!=null&&this.config.catalog.length()>0){
            return this.config.catalog;
        }
        if(this.config.schema!=null&&this.config.schema.length()>0){
            return this.config.catalog;
        }
        return "";
    }

    /**
     * 得到表名全称
     * xx库.xx表
     * @param entityClass
     * @return
     */
    public String getTableName(Class<?> entityClass){
        EntityHelper.EntityTable entityTable=EntityHelper.getEntityTable(entityClass);
        String prefix=entityTable.getPrefix();
        if(prefix.equals("")){
            prefix=getPrefix();
        }
        if(!prefix.equals("")){
            return prefix+"."+entityTable.getName();
        }
        return entityTable.getName();
    }
    /**
     * 注册通用Mapper
     * @param mapperClass
     */
    public void registerMapper(Class<?> mapperClass){
        if(registerMapper.get(mapperClass)==null){
            this.registerMapper.put(mapperClass,fromMapperClass(mapperClass));
        }else{
            throw new RuntimeException("已经注册过的通用Mapper["+mapperClass.getCanonicalName()+"]不能多次注册");
        }
    }

    /**
     * 注册通用Mapper
     * @param mapperClass
     */
    public void registerMapper(String mapperClass){
        try {
            Class c=Class.forName(mapperClass);
            registerMapper(c);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException("注册通用Mapper["+mapperClass+"]失败，找不到该通用Mapper!");
        }
    }

    /**
     * 根据mapper类，生成动态代理MapperTemplate对象
     * 初始化MapperTemplate
     * @param mapperClass
     * @return
     */
    private MapperTemplate fromMapperClass(Class<?> mapperClass){
        Method[] methods=mapperClass.getDeclaredMethods();
        Class templateClass=null;
        Class tempClass=null;
        Set<String> methodSet=new HashSet<String>();
        for(Method method:methods){
            if(method.isAnnotationPresent(SelectProvider.class)){
                SelectProvider provider=method.getAnnotation(SelectProvider.class);
                tempClass=provider.type();
                methodSet.add(method.getName());
            }else if(method.isAnnotationPresent(InsertProvider.class)){
                InsertProvider provider=method.getAnnotation(InsertProvider.class);
                tempClass=provider.type();
                methodSet.add(method.getName());
            }else if(method.isAnnotationPresent(DeleteProvider.class)){
                DeleteProvider provider=method.getAnnotation(DeleteProvider.class);
                tempClass=provider.type();
                methodSet.add(method.getName());
            }else if(method.isAnnotationPresent(UpdateProvider.class)){
                UpdateProvider provider=method.getAnnotation(UpdateProvider.class);
                tempClass=provider.type();
                methodSet.add(method.getName());
            }
        }
        //这里有可能不是这样
        templateClass=tempClass;

        if(templateClass==null||!(MapperTemplate.class.isAssignableFrom(templateClass))){
            throw new RuntimeException("接口中不存在包含type为MapperTemplate的Provider注释，这不是一个合法的通用Mapper接口类");
        }
        MapperTemplate mapperTemplate=null;
        try {
            mapperTemplate= (MapperTemplate) templateClass.getConstructor(new Class[]{Class.class,MapperHelper.class}).newInstance(new Object[]{mapperClass,this});
        } catch (Exception e) {
            throw new RuntimeException("实例化MapperTemplate对象失败："+e.getMessage());
        }
        for (String methodName:methodSet){
            try {
                mapperTemplate.addMethodMap(methodName,templateClass.getMethod(methodName,new Class[]{MappedStatement.class}));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(templateClass.getCanonicalName()+"中缺少"+methodName+"方法！");
            }
        }
        return mapperTemplate;
    }
    public void setUUID(String UUID){
        this.config.UUID=UUID;
    }

    public void setIDENTITY(String IDENTITY){
        IdentityDialect identityDialect= IdentityDialect.getDatabaseDialect(IDENTITY);
        if(identityDialect!=null){
            config.IDENTITY=identityDialect.getIdentityRetrievalStatement();
        }else{
            config.IDENTITY=IDENTITY;
        }
    }

    public void setOrder(String order){
        this.config.BEFORE="BEFORE".equalsIgnoreCase(order);
    }

    public void setSeqFormat(String seqFormat){
        this.config.seqFormat=seqFormat;
    }

    public void setCatalog(String catalog){
        this.config.catalog=catalog;
    }

    public void setSchema(String schema){
        this.config.schema=schema;
    }
    /**
     * 是否spring4
     * @return
     */
    public boolean isSpring4(){
        return this.spring4;
    }

    /**
     * 是否spring
     * @return
     */
    public boolean isSpring(){
        return this.spring;
    }

    /**
     * 取得spring版本
     * @return
     */
    public String getSpringVersion(){
        return this.springVersion;
    }
    private class Config{
        private String UUID;
        private String IDENTITY;
        private boolean BEFORE=false;
        private String seqFormat;
        private String catalog;
        private String schema;

        private Config(){}
    }

    public static enum IdentityDialect{
        DB2("VALUES IDENTITY_VAL_LOCAL()"),
        MYSQL("SELECT LAST_INSERT_ID()"),
        SQLSERVER("SELECT SCOPE_IDENTITY()"),
        CLOUDSCAPE("VALUES IDENTITY_VAL_LOCAL()"),
        DERBY("VALUES IDENTITY_VAL_LOCAL()"),
        HSQLDB("CALL IDENTITY()"),
        SYBASE("SELECT @@IDENTITY"),
        DB2_MF("SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1"),
        INFORMIX("select dbinfo('sqlca.sqlerrd1') from systables where tabid=1");

        private String identityRetrievalStatement;

        private IdentityDialect(String identityRetrievalStatement) {
            this.identityRetrievalStatement = identityRetrievalStatement;
        }

        public String getIdentityRetrievalStatement() {
            return this.identityRetrievalStatement;
        }

        public static IdentityDialect getDatabaseDialect(String database) {
            IdentityDialect returnValue = null;
            if ("DB2".equalsIgnoreCase(database))
                returnValue = DB2;
            else if ("MySQL".equalsIgnoreCase(database))
                returnValue = MYSQL;
            else if ("SqlServer".equalsIgnoreCase(database))
                returnValue = SQLSERVER;
            else if ("Cloudscape".equalsIgnoreCase(database))
                returnValue = CLOUDSCAPE;
            else if ("Derby".equalsIgnoreCase(database))
                returnValue = DERBY;
            else if ("HSQLDB".equalsIgnoreCase(database))
                returnValue = HSQLDB;
            else if ("SYBASE".equalsIgnoreCase(database))
                returnValue = SYBASE;
            else if ("DB2_MF".equalsIgnoreCase(database))
                returnValue = DB2_MF;
            else if ("Informix".equalsIgnoreCase(database)) {
                returnValue = INFORMIX;
            }
            return returnValue;
        }
    }
}
