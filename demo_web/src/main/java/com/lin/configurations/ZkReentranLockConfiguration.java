package com.lin.configurations;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pc on 2017-10-20.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@Configuration
public class ZkReentranLockConfiguration {

    private String host;
    @Value("${zookeeper.host}")
    public void setHost(String host) {
        this.host = host;
    }
    @Value("${zookeeper.port}")
    private String port;

    private int sessionTimeout;
    @Value("${zookeeper.sessionTimeout}")
    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    private int connectTimeout;
    @Value("${zookeeper.connectTimeout}")
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Bean(initMethod = "start",destroyMethod = "close")
    public CuratorFramework createCuratorFramework(){
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        RetryPolicy retryPolicy = new RetryNTimes(3,1000);
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .newClient(host+":"+port,sessionTimeout,connectTimeout,retryPolicy);
        return curatorFramework;
    }
}
