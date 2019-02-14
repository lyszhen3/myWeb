package com.lys;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lys.data.beans.Role;
import com.lys.data.mappers.RoleMapper;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 12/25/2017.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/data_source.xml")
public class test {
    @Autowired
    RoleMapper roleMapper;


    static int THREAD_TIME = 100;
    CountDownLatch c = new CountDownLatch(THREAD_TIME);

    @Test
    public void insert(){
        Role role = new Role();
        role.setDescription("会不会有问题2");
        roleMapper.insert(role);
    }

    @Test
    public void delete(){
        roleMapper.deleteById(1111L);
    }

    @Test
    public void insertMillion(){
        int radio = 6;
        int base = 1000000;
        Integer count = roleMapper.selectCount(new EntityWrapper<>());
        for(int i = count;i<radio*base;i++){
            Role role = new Role();
            role.setDescription("插入数据啊~~插入一些数据"+(i+1));
            role.setSort(i+1);
            role.setIsDelete(1);
            roleMapper.insert(role);
        }

    }


    @Test
    public void testMoreThread() {
        for (int i = 0; i < THREAD_TIME; i++) {
            final int p = i;
            Thread thread = new Thread(()->{
                try {
                    long start = System.currentTimeMillis();
                    roleMapper.selectPage(new RowBounds(p*10,10), new EntityWrapper<>());
                    System.out.println("耗时"+(System.currentTimeMillis()-start));
                } finally {
                    c.countDown();
                }
            });
            thread.start();
        }
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println((System.currentTimeMillis()-start)/1000+"s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
