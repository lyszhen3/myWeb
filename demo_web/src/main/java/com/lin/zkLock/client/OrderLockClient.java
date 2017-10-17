package com.lin.zkLock.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by pc on 2017-10-16.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@Component
public class OrderLockClient {
    private  String host;

    @Value("${zookeeper.host}")
    public  void setHost(String host) {
        this.host = host;
    }

    private  String port;
    @Value("${zookeeper.port}")
    public  void setPort(String port) {
        this.port = port;
    }

    private  RetryPolicy retryPolicy;
    @Autowired
    public  void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public  CuratorFramework getClient(){

        return CuratorFrameworkFactory.newClient(host+":"+port, retryPolicy);
    }

}
