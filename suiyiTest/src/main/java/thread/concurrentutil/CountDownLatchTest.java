package thread.concurrentutil;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lys on 2018/8/14.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class CountDownLatchTest {
	static CountDownLatch c = new CountDownLatch(2);
	public static void main(String[] args) throws InterruptedException {
		Thread parsr1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(1);
				c.countDown();
				System.out.println(2);
				c.countDown();
			}
		});
		parsr1.start();
		c.await();
		System.out.println(3);


	}

}
