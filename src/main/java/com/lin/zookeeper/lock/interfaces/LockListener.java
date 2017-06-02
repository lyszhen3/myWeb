package com.lin.zookeeper.lock.interfaces;

public interface LockListener {
    /**
     * call back called when the lock 
     * is acquired
     */
    public void lockAcquired();
    
    /**
     * call back called when the lock is 
     * released.
     */
    public void lockReleased();
}