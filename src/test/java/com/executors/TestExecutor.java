package com.executors;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pc on 2017-07-28.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class TestExecutor {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);//固定大小线程池
        
        for(int i=0;i<20;i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名:"+Thread.currentThread().getName());
                }
            };
            executorService.execute(runnable);
            
        }
        executorService.shutdown();


    }

}
