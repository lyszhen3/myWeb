package com.lin.test.mappers.base;

import org.apache.ibatis.annotations.SelectProvider;

/**
 * Created by pc on 2017-07-03.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public  interface Mapper<T> {

     /**
     * 查主键
     * @param paramObject
     * @return
     */
    @SelectProvider(type=MapperProvider.class, method="dynamicSQL")
    public  T selectByPrimaryKey(Object paramObject);

}
