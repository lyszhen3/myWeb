package thread;

import jdk.nashorn.internal.ir.Block;

import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/7/24.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ThreadState {
	public static void main(String[] args) {
		new Thread(new TimeWaiting(),"TimeWaitingThread").start();
		new Thread(new Waiting(),"WaitingThread").start();

		//使用两个block 线程 一个获取锁成功 一个被阻塞
		new Thread(new Blocked(),"BlockedThread-1").start();
		new Thread(new Blocked(),"BlockedThread-2").start();
	}

	static class TimeWaiting implements Runnable{
		@Override
		public void run() {
			while(true){
				SleepUtils.second(100);
			}
		}
	}

	static class Waiting implements Runnable{

		@Override
		public void run() {
			while(true){
				synchronized(Waiting.class){
					try {
						Waiting.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	static class Blocked implements Runnable{

		@Override
		public void run() {
			synchronized (Blocked.class){
				while(true){
					SleepUtils.second(100);
				}
			}
		}
	}


}
