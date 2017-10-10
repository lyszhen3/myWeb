package com.lin.test.services;

import com.lin.data.beans.Account;
import com.lin.data.mappers.TestMapper;
import com.lin.demo.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    TestMapper testMapper;
    @Autowired
    public void setTestMapper(TestMapper testMapper) {
        this.testMapper = testMapper;
    }


    TestService2 testService2;
    @Autowired
    public void setTestService2(TestService2 testService2) {
        this.testService2 = testService2;
    }

    DemoService demoService;
    @Autowired
    public void setDemoService(DemoService demoService) {
        this.demoService = demoService;
    }

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
    @Transactional(rollbackFor = Exception.class)
    public void updateByPK(Long id) {


        Account account  =new Account();
        account.setId(id);
        account.setName("修改");
        testService2.updateOne(account);
//        testMapper.updateByPrimaryKeySelective(account);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.setName("修复");
        testMapper.updateByPrimaryKeySelective(account);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Transactional
    public Account selOne(Long id) {

        Account account = testMapper.selectByPrimaryKey(id);
        System.out.println(account.getName());
        return account;
    }
    //调用dubbo测试
    public void testDubbo(){
        demoService.helloWorld();
    }
}
