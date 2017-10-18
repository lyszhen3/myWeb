package com.lin.reentrantLock;

import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.jboss.netty.util.internal.ConcurrentHashMap;

/**
 * Created by pc on 2017-10-18.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class OrderLockUtil {
    private static ConcurrentHashMap<String, OrderLock> orderLockMap = new ConcurrentHashMap<>();
    private final static int MAX_NUM = (1 << 16) - 1;

    public synchronized static OrderLock getLock(String key) throws MaximumLockCountExceeded {
        OrderLock orderLock;
        if ((orderLock = orderLockMap.get(key)) != null) {
            if(orderLock.getNum().get() >= MAX_NUM){
                throw new MaximumLockCountExceeded("锁数量超过最大值");
            }
            orderLock.getNum().getAndIncrement();
        } else {
            orderLock = new OrderLock();
            orderLockMap.put(key, orderLock);
        }
        return orderLock;
    }

    public  synchronized static void returnLock(String key) {
        OrderLock orderLock;
        if ((orderLock = orderLockMap.get(key)) != null) {
            orderLock.getNum().getAndDecrement();
            if (orderLock.getNum().get() == 0) {
                orderLockMap.remove(key);
            }
        }
    }
    public static class MaximumLockCountExceeded extends Exception{
        public MaximumLockCountExceeded(String msg){
            super(msg);
        }
    }
}
