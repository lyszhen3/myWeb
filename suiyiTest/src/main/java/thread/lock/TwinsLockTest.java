package thread.lock;

import org.junit.Test;
import thread.SleepUtils;

import java.util.concurrent.locks.Lock;

/**
 * Created by lys on 2018/7/31.
 * 可以观察到线程名称成对输出
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TwinsLockTest {
	@Test
	public void test() {
		final Lock lock = new TwinsLock();

		class Worker extends Thread {
			@Override
			public void run() {
				while (true) {
					lock.lock();
					try{
						SleepUtils.second(1);
						System.out.println(Thread.currentThread().getName());
						SleepUtils.second(1);
					}finally {
						lock.unlock();
					}
				}
			}
		}
		//启动十个线程
		for (int i = 0; i < 10; i++) {
			Worker w = new Worker();
			w.setDaemon(true);
			w.start();
		}
		//每秒换行
		for (int i = 0; i < 10; i++) {
			SleepUtils.second(1);
			System.out.println();
		}

	}

}
