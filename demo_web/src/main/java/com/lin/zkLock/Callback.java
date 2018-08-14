package com.lin.zkLock;

/**
 * Created by sunyujia@aliyun.com on 2016/2/23.
 */
public interface Callback {

     Object onGetLock() throws InterruptedException;

     Object onTimeout() throws InterruptedException;
}
