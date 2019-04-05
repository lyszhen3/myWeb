package com.lin.reentrantLock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by pc on 2017-10-18.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class OrderLock extends ReentrantReadWriteLock  {
    private AtomicInteger num;

    public AtomicInteger getNum() {
        return num;
    }
    OrderLock(){
        num = new AtomicInteger(1);

    }

}
