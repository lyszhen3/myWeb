package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pc on 2017-08-16.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( value="/lys_spring.xml" )
public class ExcutorTest {
    @Autowired
    ThreadPoolTaskExecutor threadPool;

    @Test
    public void test(){
        for(int i = 0;i<30;i++) {
            threadPool.execute(new Task(i));
        }
    }

    class Task implements Runnable {
        final int i ;
        public Task(int i){
            this.i = i;
        }
        @Override
        public void run() {
            System.out.println("我的天"+i);
            while (true){

            }
        }
    }


}
