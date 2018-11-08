package com.lin.test.services;

import com.lin.data.beans.Account;
import com.lin.data.mappers.AccountTestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by lys on 2017-07-30.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@Service
public class TestService2 {

    AccountTestMapper testMapper;
    @Autowired
    public void setTestMapper(AccountTestMapper testMapper) {
        this.testMapper = testMapper;
    }

    //    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void delete() throws Exception {

        try {
            testMapper.deleteOne(4L);
//        throw new Exception("我的天");
            String xx = null;
            xx.split("|");
        } catch (Exception e) {
            //throw 异常会使事务回滚,否则不会回滚
//        	throw new RuntimeException("xx");
        }
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOne(Account account) {
        testMapper.updateByPrimaryKeySelective(account);

    }
}
