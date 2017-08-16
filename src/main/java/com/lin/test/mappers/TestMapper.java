package com.lin.test.mappers;

import com.lin.test.beans.Account;
import com.lin.test.mappers.base.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pc on 2017-01-13.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Component
@Scope("singleton")
@Lazy(true)
public interface TestMapper extends Mapper<Account> {


    int testCount();

    List<Account> selList();


    int insertOne(@Param("name") String name,@Param("email") String email);
    int deleteOne(@Param("id") Long id);

}
