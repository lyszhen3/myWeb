package com.lin.test.services;

import com.lin.test.beans.Account;
import com.lin.test.mappers.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pc on 2017-03-21.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Service("lin_testService")
public class TestService {
    @Autowired
    TestMapper testMapper;

    @Autowired
    TestService2 testService2;
    public int testCount(){
       int i= testMapper.testCount();
       return i;
    }

    public List<Account> selList() {
        Account account = testMapper.selectByPrimaryKey(1);
        System.out.println(account.getEmail());


        return testMapper.selList();
    }

    /**
     * 测试双service事务
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void testTransation() throws Exception {

        testMapper.insertOne("大麦","@qq.com");
        testService2.delete();


    }
}
