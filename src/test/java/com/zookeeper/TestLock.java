package com.zookeeper;

import com.lin.zookeeper.lock.WriteLock;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by pc on 2017-06-14.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class TestLock {
    private final static String connectString="127.0.0.1:2181";
    private final static int sessionTimeout=1000;

    @Test
    public void testLock(){

        try {


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
