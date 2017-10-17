package com.lin.test.services;

import com.alibaba.fastjson.JSONObject;
import com.lin.data.beans.Account;
import com.lin.data.mappers.TestMapper;
import com.lin.demo.services.DemoService;
import com.lin.springUtils.WebSpringFactory;
import com.lin.test.services.abstracts.MoneyVipPolicy;
import com.lin.zkLock.Callback;
import com.lin.zkLock.ZkDistributedLockTemplate;
import org.apache.curator.framework.CuratorFramework;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    private final static Logger log = LogManager.getLogger(TestService.class);
//    OrderLockClient orderLockClient;
//    @Autowired
//    public void setOrderLockClient(OrderLockClient orderLockClient) {
//        this.orderLockClient = orderLockClient;
//    }
//    private CuratorFramework curatorFramework;
//    @Autowired
//    public void setCuratorFramework(CuratorFramework curatorFramework) {
//        this.curatorFramework = curatorFramework;
//    }

//    private ZkDistributedLockTemplate template;
//    @Autowired
//    public void setTemplate(ZkDistributedLockTemplate template) {
//        this.template = template;
//    }

    private TestMapper testMapper;

    @Autowired
    public void setTestMapper(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    private TestService2 testService2;

    @Autowired
    public void setTestService2(TestService2 testService2) {
        this.testService2 = testService2;
    }

    private DemoService demoService;

    @Resource
    public void setDemoService(DemoService demoService) {
        this.demoService = demoService;
    }

    public int testCount() {
        return testMapper.testCount();
    }

    public List<Account> selList() {
        Account account = testMapper.selectByPrimaryKey(1);
        System.out.println(account.getEmail());


        return testMapper.selList();
    }

    /**
     * 测试双service事务
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void testTransation() throws Exception {

        testMapper.insertOne("大麦", "@qq.com");
        testService2.delete();


    }

    @Transactional(rollbackFor = Exception.class)
    public void updateByPK(Long id) {


        Account account = new Account();
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
    public void testDubbo() {
        demoService.helloWorld();
    }

    /**
     * 匿名内部类呢可以引用到springbean
     *
     * @return long
     */
    public Long payMoney() {
        Long money = 0L;
        return new MoneyVipPolicy() {
            @Override
            public Long getMoney() {
                //like this
                testMapper.deleteOne(1L);
                return money + 20L;
            }
        }.getMoney();
    }

    public JSONObject testZkLockWrite() {
//        final ZkDistributedLockTemplate template = new ZkDistributedLockTemplate(curatorFramework);
        ZkDistributedLockTemplate template = WebSpringFactory.getBean(ZkDistributedLockTemplate.class);
        Object execute = template.execute("12306", 5000, new Callback() {
            @Override
            public Object onGetLock() throws InterruptedException {
                log.info("订单修改操作开始时间" + LocalDateTime.now());
                System.out.println("订单修改操作");
                Thread.sleep(3000);
                return new JSONObject().fluentPut("订单12306", "修改成功");
            }

            @Override
            public Object onTimeout() throws InterruptedException {
                return new JSONObject().fluentPut("订单12306修改", "系统繁忙");
            }
        });
        return (JSONObject) execute;
    }

    public JSONObject testZkLockRead() {

//        final ZkDistributedLockTemplate template = new ZkDistributedLockTemplate(curatorFramework);
        ZkDistributedLockTemplate template = WebSpringFactory.getBean(ZkDistributedLockTemplate.class);
        Object execute = template.execute("12306", 5000, new Callback() {
            @Override
            public Object onGetLock() throws InterruptedException {
                log.info("订单查看操作开始时间" + LocalDateTime.now());
                System.out.println("订单查看操作");
                Thread.sleep(9000);

                return new JSONObject().fluentPut("订单12306", "水杯一个，价格66");
            }

            @Override
            public Object onTimeout() throws InterruptedException {
                System.out.println("获取锁超时，系统繁忙骚后再试咯");
                return new JSONObject().fluentPut("订单12306查看", "系统繁忙");
            }
        });

        return (JSONObject) execute;

    }
}
