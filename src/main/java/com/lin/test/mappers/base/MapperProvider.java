package com.lin.test.mappers.base;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaohongbo on 15/9/15.
 */
public class MapperProvider extends MapperTemplate{
    public MapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 按单体元素，补全单个信息
     * @param ms
     * @return
     */
    public SqlNode selectOne(MappedStatement ms){
        Class entityClass=getSelectReturyType(ms);//返回值类型
        setResultType(ms, entityClass);

        List<SqlNode> sqlNodes=new ArrayList<SqlNode>();

        sqlNodes.add(new StaticTextSqlNode("SELECT " + EntityHelper.getSelectColumns(entityClass) + " FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getAllIfColumnNode(entityClass)));
        return new MixedSqlNode(sqlNodes);
    }

    /**
     * 查询列表
     * @param ms
     * @return
     */
    public SqlNode select(MappedStatement ms){
        Class entityClass=getSelectReturyType(ms);//拿返回值类型

        setResultType(ms, entityClass);
        List sqlNodes=new ArrayList();
        sqlNodes.add(new StaticTextSqlNode("SELECT "+EntityHelper.getSelectColumns(entityClass)+" FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getAllIfColumnNode(entityClass)));
        StringBuilder orderByClause=EntityHelper.getOrderByClause(entityClass);
        if(orderByClause.length()>0){
            sqlNodes.add(new StaticTextSqlNode(orderByClause.insert(0,"ORDER BY ").toString()));
        }
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode selectByPrimaryKey(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        System.out.println("我的天哪---"+entityClass);
//        List<ParameterMapping> parameterMappings = getPrimaryKeyParameterMappings(ms);
//
//        if(parameterMappings==null||parameterMappings.size()==0){
//            throw new RuntimeException("没有找到主键");
//        }
        setResultType(ms, entityClass);
        List<SqlNode> sqlNodes=new ArrayList<SqlNode>();
        sqlNodes.add(new StaticTextSqlNode("SELECT " + EntityHelper.getSelectColumns(entityClass) + " FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getPkIfColumnNode(entityClass)));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode selectCount(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("SELECT COUNT(*) FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));

        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getAllIfColumnNode(entityClass)));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode insert(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("INSERT INTO "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));

        Set<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);

        Boolean hasIdentityKey = Boolean.valueOf(false);

        for (EntityHelper.EntityColumn column : columnList)
        {
            if ((column.getSequenceName() == null) || (column.getSequenceName().length() <= 0)) {
                if (column.isIdentity())
                {
                    if (hasIdentityKey.booleanValue()) {
                        throw new RuntimeException(ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
                    }

                    newSelectKeyMappedStatement(ms, column);
                    hasIdentityKey = Boolean.valueOf(true);

                    sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_cache", column.getProperty()));
                } else if (column.isUuid())
                {
                    sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_bind", getUUID()));
                }
            }
        }
        sqlNodes.add(new StaticTextSqlNode("(" + EntityHelper.getAllColumns(entityClass) + ")"));
        List ifNodes = new ArrayList();

        for (EntityHelper.EntityColumn column : columnList)
        {
            if (column.isIdentity()) {
                ifNodes.add(getIfCacheNotNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "_cache },")));
            }
            else {
                ifNodes.add(getIfNotNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "},")));
            }

            if ((column.getSequenceName() != null) && (column.getSequenceName().length() > 0))
                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode(getSeqNextVal(column) + " ,")));
            else if (column.isIdentity())
                ifNodes.add(getIfCacheIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + " },")));
            else if (column.isUuid()) {
                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "_bind },")));
            }
            else {
                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + ",jdbcType=VARCHAR},")));
            }
        }

        sqlNodes.add(new TrimSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes), "VALUES (", null, ")", ","));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode insertSelective(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("INSERT INTO "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));
        Set<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        List ifNodes = new ArrayList();

        Boolean hasIdentityKey = Boolean.valueOf(false);

        for (EntityHelper.EntityColumn column : columnList)
        {
            if ((column.getSequenceName() != null) && (column.getSequenceName().length() > 0))
            {
                ifNodes.add(new StaticTextSqlNode(column.getColumn() + ","));
            } else if (column.isIdentity()) {
                if (hasIdentityKey.booleanValue()) {
                    throw new RuntimeException(ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
                }

                newSelectKeyMappedStatement(ms, column);
                hasIdentityKey = Boolean.valueOf(true);

                ifNodes.add(new StaticTextSqlNode(column.getColumn() + ","));

                sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_cache", column.getProperty()));
            } else if (column.isUuid())
            {
                sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_bind", getUUID()));
                ifNodes.add(new StaticTextSqlNode(column.getColumn() + ","));
            } else {
                ifNodes.add(getIfNotNull(column, new StaticTextSqlNode(column.getColumn() + ",")));
            }
        }

        sqlNodes.add(new TrimSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes), "(", null, ")", ","));

        ifNodes = new ArrayList();

        for (EntityHelper.EntityColumn column : columnList)
        {
            if (column.isIdentity())
                ifNodes.add(new IfSqlNode(new StaticTextSqlNode("#{" + column.getProperty() + "_cache },"), column.getProperty() + "_cache != null "));
            else {
                ifNodes.add(new IfSqlNode(new StaticTextSqlNode("#{" + column.getProperty() + "},"), column.getProperty() + " != null "));
            }
            if ((column.getSequenceName() != null) && (column.getSequenceName().length() > 0))
                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode(getSeqNextVal(column) + " ,")));
            else if (column.isIdentity())
                ifNodes.add(getIfCacheIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + " },")));
            else if (column.isUuid()) {
                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "_bind },")));
            }
        }

        sqlNodes.add(new TrimSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes), "VALUES (", null, ")", ","));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode delete(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("DELETE FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));

        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getAllIfColumnNode(entityClass)));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode deleteByPrimaryKey(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        setResultType(ms, entityClass);
        List<SqlNode> sqlNodes=new ArrayList<SqlNode>();
        sqlNodes.add(new StaticTextSqlNode("DELETE FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getPkIfColumnNode(entityClass)));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode updateByPrimaryKey(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("UPDATE "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));

        Set<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        List ifNodes = new ArrayList();
        for (EntityHelper.EntityColumn column : columnList) {
            if (!column.isId()) {
                ifNodes.add(new StaticTextSqlNode(column.getColumn() + " = #{" + column.getProperty() + "}, "));
            }
        }
        sqlNodes.add(new SetSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes)));

        columnList = EntityHelper.getPKColumns(entityClass);
        List whereNodes = new ArrayList();
        boolean first = true;

        for (EntityHelper.EntityColumn column : columnList) {
            whereNodes.add(getColumnEqualsProperty(column, first));
            first = false;
        }
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), new MixedSqlNode(whereNodes)));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode updateByPrimaryKeySelective(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("UPDATE "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(), entityClass));

        Set<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        List ifNodes = new ArrayList();

        for (EntityHelper.EntityColumn column : columnList) {
            if (!column.isId()) {
                StaticTextSqlNode columnNode = new StaticTextSqlNode(column.getColumn() + " = #{" + column.getProperty() + "}, ");
                ifNodes.add(getIfNotNull(column, columnNode));
            }
        }
        sqlNodes.add(new SetSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes)));

        columnList = EntityHelper.getPKColumns(entityClass);
        List whereNodes = new ArrayList();
        boolean first = true;

        for (EntityHelper.EntityColumn column : columnList) {
            whereNodes.add(getColumnEqualsProperty(column, first));
            first = false;
        }
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), new MixedSqlNode(whereNodes)));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode countByExample(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);

        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("SELECT COUNT(*) FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));
        IfSqlNode ifNullSqlNode = new IfSqlNode(exampleWhereClause(ms.getConfiguration()), "_parameter != null");
        sqlNodes.add(ifNullSqlNode);
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode deleteByExample(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);

        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("DELETE FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));
        IfSqlNode ifNullSqlNode = new IfSqlNode(exampleWhereClause(ms.getConfiguration()), "_parameter != null");
        sqlNodes.add(ifNullSqlNode);
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode selectByExample(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);

        setResultType(ms, entityClass);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("SELECT"));
        IfSqlNode distinctSqlNode = new IfSqlNode(new StaticTextSqlNode("DISTINCT"), "distinct");
        sqlNodes.add(distinctSqlNode);
        sqlNodes.add(new StaticTextSqlNode(EntityHelper.getSelectColumns(entityClass) + " FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));
        IfSqlNode ifNullSqlNode = new IfSqlNode(exampleWhereClause(ms.getConfiguration()), "_parameter != null");
        sqlNodes.add(ifNullSqlNode);
        IfSqlNode orderByClauseSqlNode = new IfSqlNode(new TextSqlNode("order by ${orderByClause}"), "orderByClause != null");
        sqlNodes.add(orderByClauseSqlNode);
        StringBuilder orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            IfSqlNode defaultOrderByClauseSqlNode = new IfSqlNode(new StaticTextSqlNode(orderByClause.insert(0, "ORDER BY ").toString()), "orderByClause == null");
            sqlNodes.add(defaultOrderByClauseSqlNode);
        }
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode updateByExampleSelective(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("UPDATE "));
        sqlNodes.add(tableNameCheckFenForExam(ms.getConfiguration(), entityClass));

        Set<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        List ifNodes = new ArrayList();

        for (EntityHelper.EntityColumn column : columnList) {
            if (!column.isId()) {
                StaticTextSqlNode columnNode = new StaticTextSqlNode(column.getColumn() + " = #{record." + column.getProperty() + "}, ");
                ifNodes.add(new IfSqlNode(columnNode, "record." + column.getProperty() + " != null"));
            }
        }
        sqlNodes.add(new SetSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes)));

        IfSqlNode ifNullSqlNode = new IfSqlNode(updateByExampleWhereClause(ms.getConfiguration()), "_parameter != null");
        sqlNodes.add(ifNullSqlNode);
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode updateByExample(MappedStatement ms)
    {
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("UPDATE "));
        sqlNodes.add(tableNameCheckFenForExam(ms.getConfiguration(),entityClass));

        Set<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        List setSqlNodes = new ArrayList();

        for (EntityHelper.EntityColumn column : columnList) {
            if (!column.isId()) {
                setSqlNodes.add(new StaticTextSqlNode(column.getColumn() + " = #{record." + column.getProperty() + "}, "));
            }
        }
        sqlNodes.add(new SetSqlNode(ms.getConfiguration(), new MixedSqlNode(setSqlNodes)));

        IfSqlNode ifNullSqlNode = new IfSqlNode(updateByExampleWhereClause(ms.getConfiguration()), "_parameter != null");
        sqlNodes.add(ifNullSqlNode);
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode selectByConditionList(MappedStatement ms){
//        Class entityClass=getSelectReturyType(ms);//拿返回值类型
//
//        setResultType(ms, entityClass);
//        List sqlNodes=new ArrayList();
//        sqlNodes.add(new StaticTextSqlNode("SELECT "+EntityHelper.getSelectColumns(entityClass)+" FROM "+tableName(entityClass)));
//        StringBuilder orderByClause=EntityHelper.getOrderByClause(entityClass);
//        if(orderByClause.length()>0){
//            sqlNodes.add(new StaticTextSqlNode(orderByClause.insert(0,"ORDER BY ").toString()));
//        }
//        sqlNodes.add(new StaticTextSqlNode("limit #{startIndex},#{endIndex}"));

        Class entityClass = getSelectReturnType(ms);

        setResultType(ms, entityClass);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("SELECT"));
        IfSqlNode distinctSqlNode = new IfSqlNode(new StaticTextSqlNode("DISTINCT"), "distinct");
        sqlNodes.add(distinctSqlNode);
        sqlNodes.add(new StaticTextSqlNode(EntityHelper.getSelectColumns(entityClass) + " FROM "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));
        IfSqlNode ifNullSqlNode = new IfSqlNode(exampleWhereClause(ms.getConfiguration()), "_parameter != null");
        sqlNodes.add(ifNullSqlNode);
        IfSqlNode orderByClauseSqlNode = new IfSqlNode(new TextSqlNode("order by ${orderByClause}"), "orderByClause != null");
        sqlNodes.add(orderByClauseSqlNode);
        StringBuilder orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            IfSqlNode defaultOrderByClauseSqlNode = new IfSqlNode(new StaticTextSqlNode(orderByClause.insert(0, "ORDER BY ").toString()), "orderByClause == null");
            sqlNodes.add(defaultOrderByClauseSqlNode);
        }
        sqlNodes.add(new StaticTextSqlNode("limit #{startIndex},#{endIndex}"));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode insertListHasId(MappedStatement ms){
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("INSERT INTO "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));

        sqlNodes.add(new StaticTextSqlNode("(" + EntityHelper.getAllColumns(entityClass) + ")"));

        List ifNodes = new ArrayList();
        Set<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        for (EntityHelper.EntityColumn column : columnList)
        {
            ifNodes.add(new StaticTextSqlNode("#{item."+column.getProperty()+"},"));
        }
        //加入新值
        ForEachSqlNode fes=new ForEachSqlNode(ms.getConfiguration(),new TrimSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes), "(", null, ")", ","),"list","i","item","VALUES","",",");

        sqlNodes.add(fes);
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode insertListNoId(MappedStatement ms){
        Class entityClass = getSelectReturnType(ms);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("INSERT INTO "));
        sqlNodes.add(tableNameCheckFen(ms.getConfiguration(),entityClass));

        Boolean hasIdentityKey = Boolean.valueOf(false);
        Set<EntityHelper.EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        List ifNodes = new ArrayList();
        for (EntityHelper.EntityColumn column : columnList)
        {
            if (column.isIdentity()) {
                if (hasIdentityKey.booleanValue()) {
                    throw new RuntimeException(ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
                }
//                hasIdentityKey = Boolean.valueOf(true);
            } else {
//                ifNodes.add(getIfNotNull(column, new StaticTextSqlNode(column.getColumn() + ",")));
                ifNodes.add(new StaticTextSqlNode(column.getColumn() + ","));
            }

        }
        sqlNodes.add(new TrimSqlNode(ms.getConfiguration(),new MixedSqlNode(ifNodes),"(",null,")",","));

        ifNodes = new ArrayList();

        for (EntityHelper.EntityColumn column : columnList)
        {
            if (!column.isIdentity()) {
                ifNodes.add(new StaticTextSqlNode("#{item." + column.getProperty() + " },"));
            }
        }
        //加入新值
        ForEachSqlNode fes=new ForEachSqlNode(ms.getConfiguration(),new TrimSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes), "(", null, ")", ","),"list","index","item","VALUES","",",");

        sqlNodes.add(fes);
        return new MixedSqlNode(sqlNodes);
    }
    public SqlNode insertListHasIdMore(MappedStatement ms){
        return insertListHasId(ms);
    }
    public SqlNode insertListNoIdMore(MappedStatement ms){
        return insertListNoId(ms);
    }

    public SqlNode selectFieldsByPrimaryKey(MappedStatement ms){
        Class entityClass = getSelectReturnType(ms);

//        List<ParameterMapping> parameterMappings = getPrimaryKeyParameterMappings(ms);
//
//        if(parameterMappings==null||parameterMappings.size()==0){
//            throw new RuntimeException("没有找到主键");
//        }
        setResultType(ms, entityClass);
        List<SqlNode> sqlNodes=new ArrayList<SqlNode>();
        sqlNodes.add(new TextSqlNode("SELECT ${fields} FROM "));
//        sqlNodes.add(new StaticTextSqlNode("SELECT " + EntityHelper.getSelectColumns(entityClass) + " FROM "));
        sqlNodes.add(tableNameCheckFenForBean(ms.getConfiguration(), entityClass));
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getPkIfColumnNodeMore(entityClass)));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode selectFieldsByConditionList(MappedStatement ms){
        Class entityClass = getSelectReturnType(ms);

        setResultType(ms, entityClass);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("SELECT"));
        IfSqlNode distinctSqlNode = new IfSqlNode(new StaticTextSqlNode("DISTINCT"), "example.distinct");
        sqlNodes.add(distinctSqlNode);
        //sqlNodes.add(new StaticTextSqlNode(EntityHelper.getSelectColumns(entityClass) + " FROM "));
        sqlNodes.add(new TextSqlNode("${fields} FROM "));
        sqlNodes.add(tableNameCheckFenForExam(ms.getConfiguration(), entityClass));
        IfSqlNode ifNullSqlNode = new IfSqlNode(updateByExampleWhereClause(ms.getConfiguration()), "_parameter != null");
        sqlNodes.add(ifNullSqlNode);
        IfSqlNode orderByClauseSqlNode = new IfSqlNode(new TextSqlNode("order by ${example.orderByClause}"), "example.orderByClause != null");
        sqlNodes.add(orderByClauseSqlNode);
        StringBuilder orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            IfSqlNode defaultOrderByClauseSqlNode = new IfSqlNode(new StaticTextSqlNode(orderByClause.insert(0, "ORDER BY ").toString()), "example.orderByClause == null");
            sqlNodes.add(defaultOrderByClauseSqlNode);
        }
        sqlNodes.add(new StaticTextSqlNode("limit #{example.startIndex},#{example.endIndex}"));
        return new MixedSqlNode(sqlNodes);
    }

    public SqlNode selectFieldsByExample(MappedStatement ms){
        Class entityClass = getSelectReturnType(ms);

        setResultType(ms, entityClass);
        List sqlNodes = new ArrayList();

        sqlNodes.add(new StaticTextSqlNode("SELECT"));
        IfSqlNode distinctSqlNode = new IfSqlNode(new StaticTextSqlNode("DISTINCT"), "example.distinct");
        sqlNodes.add(distinctSqlNode);
//        sqlNodes.add(new StaticTextSqlNode(EntityHelper.getSelectColumns(entityClass) + " FROM "));
        sqlNodes.add(new TextSqlNode("${fields} FROM "));
        sqlNodes.add(tableNameCheckFenForExam(ms.getConfiguration(), entityClass));
        IfSqlNode ifNullSqlNode = new IfSqlNode(updateByExampleWhereClause(ms.getConfiguration()), "_parameter != null");
        sqlNodes.add(ifNullSqlNode);
        IfSqlNode orderByClauseSqlNode = new IfSqlNode(new TextSqlNode("order by ${example.orderByClause}"), "example.orderByClause != null");
        sqlNodes.add(orderByClauseSqlNode);
        StringBuilder orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            IfSqlNode defaultOrderByClauseSqlNode = new IfSqlNode(new StaticTextSqlNode(orderByClause.insert(0, "ORDER BY ").toString()), "example.orderByClause == null");
            sqlNodes.add(defaultOrderByClauseSqlNode);
        }
        return new MixedSqlNode(sqlNodes);
    }
}
