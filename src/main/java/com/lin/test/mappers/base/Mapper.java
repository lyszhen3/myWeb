package com.lin.test.mappers.base;

import com.lin.test.beans.Account;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * Created by pc on 2017-07-03.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public interface Mapper<T> {

    /**
     * 查主键
     *
     * @param paramObject
     * @return
     */
    @SelectProvider(type = MapperProvider.class, method = "dynamicSQL")
    T selectByPrimaryKey(Object paramObject);

    @UpdateProvider(type = MapperProvider.class, method = "dynamicSQL")
    int updateByPrimaryKey(Account account);
    @UpdateProvider(type = MapperProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeySelective(Account account);
}
