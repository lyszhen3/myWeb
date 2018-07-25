package com.lin.data.core.mapperHelper;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;


public class EntityHelper {
    private static final Map<Class<?>,EntityTable> entityTableMap=new HashMap<Class<?>, EntityTable>();

    /**
     * 得到实体表
     * @param entityClass
     * @return
     */
    public static EntityTable getEntityTable(Class<?> entityClass){
        EntityTable entityTable=entityTableMap.get(entityClass);
        if(entityTable == null){
            initEntityNameMap(entityClass);
            entityTable=entityTableMap.get(entityClass);
        }
        if(entityTable==null){
            throw new RuntimeException("无法获取实体类"+entityClass.getCanonicalName()+"对应的表名");
        }
        return entityTable;
    }

    /**
     * 查所有的表字段，拼成查询用的字段
     * @param entityClass
     * @return
     */
    public static String getSelectColumns(Class<?> entityClass){
        Set<EntityColumn> columnList=getColumns(entityClass);
        StringBuilder selectBuilder=new StringBuilder();
        boolean skipAlias=Map.class.isAssignableFrom(entityClass);
        for(EntityColumn entityColumn:columnList){
            selectBuilder.append(entityColumn.getColumn());
            if((!skipAlias)&&(!entityColumn.getColumn().equalsIgnoreCase(entityColumn.getProperty()))){
                selectBuilder.append(" ").append(entityColumn.getProperty().toUpperCase()).append(",");
            }else{
                selectBuilder.append(",");
            }
        }
        return selectBuilder.substring(0,selectBuilder.length()-1);
    }

    public static Set<EntityColumn> getColumns(Class<?> entityClass){
        return getEntityTable(entityClass).getEntityClassColumns();
    }
    public static synchronized void initEntityNameMap(Class<?> entityClass){
        if(entityTableMap.get(entityClass) !=null){
            return;
        }

        EntityTable entityTable=null;
        if(entityClass.isAnnotationPresent(Table.class)){
            Table table=entityClass.getAnnotation(Table.class);
            if(!table.name().equals("")){
                entityTable=new EntityTable();
                entityTable.setTable(table);
            }
        }
        if(entityTable==null){
            entityTable=new EntityTable();
            entityTable.name=camelhumpToUnderline(entityClass.getSimpleName());
        }

        List<Field> fieldList=getAllField(entityClass,null);
        Set columnSet=new HashSet();
        Set pkColumnSet=new HashSet();
        for(Field field:fieldList){
            //如果是自己家的属性，直接跳过
            if(!field.isAnnotationPresent(Transient.class)){
                EntityColumn entityColumn=new EntityColumn();
                if(field.isAnnotationPresent(Id.class)){
                    entityColumn.setId(true);
                }
                String columnName=null;
                if(field.isAnnotationPresent(Column.class)){
                    Column column=field.getAnnotation(Column.class);
                    columnName=column.name();
                }
                if((columnName==null)||(columnName.equals(""))){
                    columnName=camelhumpToUnderline(field.getName());
                }
                entityColumn.setProperty(field.getName());
                entityColumn.setColumn(columnName.toUpperCase());
                entityColumn.setJavaType(field.getType());

                if(field.isAnnotationPresent((OrderBy.class))){
                    OrderBy orderBy=field.getAnnotation(OrderBy.class);
                    if(orderBy.value().equals("")){
                        entityColumn.setOrderBy("ASC");
                    }else{
                        entityColumn.setOrderBy(orderBy.value());
                    }
                }

                if(field.isAnnotationPresent(SequenceGenerator.class)){
                    SequenceGenerator sequenceGenerator=field.getAnnotation(SequenceGenerator.class);
                    if(sequenceGenerator.sequenceName().equals("")){
                        throw new RuntimeException(entityClass+"字段"+field.getName()+"的注解@SequenceGenerator未指定sequenceName!");
                    }
                    entityColumn.setSequenceName(sequenceGenerator.sequenceName());
                }else if(field.isAnnotationPresent(GeneratedValue.class)){
                    GeneratedValue generatedValue=field.getAnnotation(GeneratedValue.class);
                    if(generatedValue.generator().equals("UUID")){
                        if(field.getType().equals(String.class)){
                            entityColumn.setUuid(true);
                        }else{
                            throw new RuntimeException(field.getName()+" - 该字段@GeneratedValue配置为UUID,但字段类型不是String");
                        }
                    }else if(generatedValue.generator().equals("JDBC")){
                        if(Number.class.isAssignableFrom(field.getType())){
                            entityColumn.setIdentity(true);
                            entityColumn.setGenerator("JDBC");
                        }else{
                            throw new RuntimeException(field.getName()+" - 该字段@GeneratedValue配置为JDBC,但字段类型不是Number");
                        }
                    }else if(generatedValue.strategy()==GenerationType.IDENTITY){
                        entityColumn.setIdentity(true);
                        if(!generatedValue.generator().equals("")){
                            String generator=null;
                            MapperHelper.IdentityDialect identityDialect=MapperHelper.IdentityDialect.getDatabaseDialect(generatedValue.generator());
                            if(identityDialect!=null){
                                generator=identityDialect.getIdentityRetrievalStatement();
                            }else{
                                generator=generatedValue.generator();
                            }
                            entityColumn.setGenerator(generator);
                        }
                    }else{
                        throw new RuntimeException(field.getName()+" - 该字段@GeneratedValue配置只允许以下几种形式:\n1.全部数据库通用的@GeneratedValue(generator=\"UUID\")"+
                        "\n2.useGeneratedKeys的@GeneratedValue(generator=\"JDBC\")"+
                        "\n3.类似mysql数据库的@GeneratedValue(stratedgy=GenerationType.IDENTITY[,generator=\"Mysql\"])");
                    }
                }
                columnSet.add(entityColumn);
                if(entityColumn.isId()){
                    pkColumnSet.add(entityColumn);
                }
            }
        }
        entityTable.entityClassColumns=columnSet;
        if(pkColumnSet.size()==0){
            entityTable.entityClassPKColumns=columnSet;
        }else{
            entityTable.entityClassPKColumns=pkColumnSet;
        }
        entityTableMap.put(entityClass,entityTable);
    }

    public static String getAllColumns(Class<?> entityClass)
    {
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn()).append(",");
        }
        return selectBuilder.substring(0, selectBuilder.length() - 1);
    }
    /**
     * 得到主键字段
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getPKColumns(Class<?> entityClass)
    {
        return getEntityTable(entityClass).getEntityClassPKColumns();
    }
    /**
     * 得到排序
     * @param entityClass
     * @return
     */
    public static StringBuilder getOrderByClause(Class<?> entityClass){
        EntityTable table=getEntityTable(entityClass);
        if(table.orderByClause!=null){
            return table.orderByClause;
        }
        StringBuilder orderBy=new StringBuilder();
        for(EntityColumn column:table.getEntityClassColumns()){
            if(column.getOrderBy()!=null){
                if(orderBy.length()>0){
                    orderBy.append(",");
                }
                orderBy.append(column.getColumn()).append(" ").append(column.getOrderBy());
            }
        }
        table.orderByClause=orderBy;
        return table.orderByClause;
    }
    /**
     * 返回类及父类中的所有属性
     * @param entityClass
     * @param fieldList
     * @return
     */
    private static List<Field> getAllField(Class<?> entityClass,List<Field> fieldList){
        if(fieldList==null){
            fieldList=new ArrayList<Field>();
        }
        if(entityClass.equals(Object.class)){
            return fieldList;
        }
        Field[] fields=entityClass.getDeclaredFields();
        for(Field field:fields){
            if(!Modifier.isStatic((field.getModifiers()))){
                fieldList.add(field);
            }
        }
        Class supperClass=entityClass.getSuperclass();
        if((supperClass!=null)&&
                (!supperClass.equals(Object.class))&&(
                (supperClass.isAnnotationPresent(Entity.class))||(!Map.class.isAssignableFrom(supperClass))&&
                        (!Collection.class.isAssignableFrom(supperClass)))){
            return getAllField(entityClass.getSuperclass(),fieldList);
        }
        return fieldList;
    }

    public static String camelhumpToUnderline(String str){
        char[] chars;
        int size;
        StringBuilder sb=new StringBuilder((size=(chars=str.toCharArray()).length)*3/2+1);
        for(int i=0;i<size;i++){
            char c=chars[i];
            if(isUppercaseAlpha(c)){
                sb.append('_').append(toLowerAscii(c));
            }else{
                sb.append(c);
            }
        }
        return sb.charAt(0)=='_'?sb.substring(1):sb.toString();
    }

    /**
     * 验证是大写字母
     * @param c
     * @return
     */
    public static boolean isUppercaseAlpha(char c){
        return (c>='A')&&(c<='Z');
    }

    public static char toUpperAscii(char c){
        if(isUppercaseAlpha(c)){
            c=(char)(c-32);
        }
        return c;
    }
    public static char toLowerAscii(char c){
        if(isUppercaseAlpha(c)){
            c=(char)(c+32);
        }
        return c;
    }
    /**
     * 表实体
     */
    public static class EntityTable{
        private String name;
        private String catalog;
        private String schema;
        private StringBuilder orderByClause;
        private Set<EntityColumn> entityClassColumns;
        private Set<EntityColumn> entityClassPKColumns;
        private Map<String,String> aliasMap;

        public void setTable(Table table){
            this.name=table.name();
            this.catalog=table.catalog();
            this.schema=table.schema();
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public StringBuilder getOrderByClause() {
            return orderByClause;
        }

        public void setOrderByClause(StringBuilder orderByClause) {
            this.orderByClause = orderByClause;
        }

        public Set<EntityColumn> getEntityClassColumns() {
            return entityClassColumns;
        }

        public void setEntityClassColumns(Set<EntityColumn> entityClassColumns) {
            this.entityClassColumns = entityClassColumns;
        }

        public Set<EntityColumn> getEntityClassPKColumns() {
            return entityClassPKColumns;
        }

        public void setEntityClassPKColumns(Set<EntityColumn> entityClassPKColumns) {
            this.entityClassPKColumns = entityClassPKColumns;
        }

        public Map<String, String> getAliasMap() {
            return aliasMap;
        }

        public void setAliasMap(Map<String, String> aliasMap) {
            this.aliasMap = aliasMap;
        }

        public String getPrefix(){
            if(this.catalog!=null&&this.catalog.length()>0){
                return this.catalog;
            }
            if(this.schema!=null&&this.schema.length()>0){
                return this.catalog;
            }
            return "";
        }

    }

    public static class EntityColumn{
        private String property;
        private String column;
        private Class<?> javaType;
        private String sequenceName;
        private boolean id=false;
        private boolean uuid=false;
        private boolean identity=false;
        private String generator;
        private String orderBy;

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public Class<?> getJavaType() {
            return javaType;
        }

        public void setJavaType(Class<?> javaType) {
            this.javaType = javaType;
        }

        public String getSequenceName() {
            return sequenceName;
        }

        public void setSequenceName(String sequenceName) {
            this.sequenceName = sequenceName;
        }

        public boolean isId() {
            return id;
        }

        public void setId(boolean id) {
            this.id = id;
        }

        public boolean isUuid() {
            return uuid;
        }

        public void setUuid(boolean uuid) {
            this.uuid = uuid;
        }

        public boolean isIdentity() {
            return identity;
        }

        public void setIdentity(boolean identity) {
            this.identity = identity;
        }

        public String getGenerator() {
            return generator;
        }

        public void setGenerator(String generator) {
            this.generator = generator;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }
        @Override
        public boolean equals(Object o) {
            if(this==o){
                return true;
            }
            if(o==null||getClass()!=o.getClass()){
                return false;
            }
            EntityColumn that= (EntityColumn) o;

            if(this.id!=that.id){
                return false;
            }
            if(this.identity!=that.identity){
                return false;
            }
            if(this.uuid!=that.uuid){
                return false;
            }
            if(this.column!=null?!this.column.equals(that.column):that.column!=null){
                return false;
            }
            if(this.generator!=null?!this.generator.equals(that.generator):that.generator!=null){
                return false;
            }
            if(this.javaType!=null?!this.javaType.equals(that.javaType):that.javaType!=null){
                return false;
            }
            if(this.orderBy!=null?!this.orderBy.equals(that.orderBy):that.orderBy!=null){
                return false;
            }
            if(this.property!=null?!this.property.equals(that.property):that.property!=null){
                return false;
            }
            if(this.sequenceName!=null?!this.sequenceName.equals(that.sequenceName):that.sequenceName!=null){
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result=this.property!=null?this.property.hashCode():0;
            result =31*result+(this.column!=null?this.column.hashCode():0);
            result =31*result+(this.javaType!=null?this.javaType.hashCode():0);
            result =31*result+(this.sequenceName!=null?this.sequenceName.hashCode():0);
            result =31*result+(this.id?1:0);
            result =31*result+(this.uuid?1:0);
            result =31*result+(this.identity?1:0);
            result =31*result+(this.generator!=null?this.generator.hashCode():0);
            result =31*result+(this.orderBy!=null?this.orderBy.hashCode():0);
            return result;
        }
    }
}
