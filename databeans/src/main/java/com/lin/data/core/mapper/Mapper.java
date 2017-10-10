package com.lin.data.core.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by pc on 2017-07-03.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public interface Mapper<T> {

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    T selectOne(T var1);

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    List<T> select(T var1);

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int selectCount(T var1);

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    T selectByPrimaryKey(Object var1);

    @InsertProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int insert(T var1);

    @InsertProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int insertSelective(T var1);

    @DeleteProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int delete(T var1);

    @DeleteProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int deleteByPrimaryKey(Object var1);

    @UpdateProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int updateByPrimaryKey(T var1);

    @UpdateProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int updateByPrimaryKeySelective(T var1);

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int countByExample(Object var1);

    @DeleteProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int deleteByExample(Object var1);

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    List<T> selectByExample(Object var1);

    @UpdateProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int updateByExampleSelective(@Param("record") T var1, @Param("example") Object var2);

    @UpdateProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int updateByExample(@Param("record") T var1, @Param("example") Object var2);

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    List<T> selectByConditionList(Object var1);

    @InsertProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int insertListHasId(List<T> var1);

    @InsertProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int insertListNoId(List<T> var1);

    @InsertProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int insertListHasIdMore(@Param("list") List<T> var1, @Param("webSite") String var2);

    @InsertProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    int insertListNoIdMore(@Param("list") List<T> var1, @Param("webSite") String var2);

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    T selectFieldsByPrimaryKey(@Param("id") Object var1, @Param("fields") String var2);

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    List<T> selectFieldsByConditionList(@Param("example") Object var1, @Param("fields") String var2);

    @SelectProvider(
        type = MapperProvider.class,
        method = "dynamicSQL"
    )
    List<T> selectFieldsByExample(@Param("example") Object var1, @Param("fields") String var2);
}
