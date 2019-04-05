package com.lin.zkLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by sunyujia@aliyun.com on 2016/2/26.
 */
@Component
public class ZkDistributedLockTemplate implements DistributedLockTemplate {
    private static final Logger log = LogManager.getLogger(ZkDistributedLockTemplate.class);

    private CuratorFramework client;

    @Autowired
    public void setClient(CuratorFramework client) {
        this.client = client;
    }

    //    public ZkDistributedLockTemplate(CuratorFramework client) {
//        this.client = client;
//    }



    private boolean tryLock(ZkReentrantLock distributedReentrantLock,Long timeout) throws Exception {
        return distributedReentrantLock.tryLock(timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public Object execute(String lockId, int timeout, Callback callback) {
        ZkReentrantLock distributedReentrantLock = null;
        boolean getLock=false;
        try {
            distributedReentrantLock = new ZkReentrantLock(client,lockId);
            if(tryLock(distributedReentrantLock, (long) timeout)){
                getLock=true;
                return callback.onGetLock();
            }else{
                return callback.onTimeout();
            }
        }catch(InterruptedException ex){
            log.error(ex.getMessage(), ex);
            Thread.currentThread().interrupt();
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally {
            if(getLock){
                distributedReentrantLock.unlock();
            }
        }
        return null;
    }
}
