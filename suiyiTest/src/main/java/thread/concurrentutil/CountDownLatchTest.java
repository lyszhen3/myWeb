package thread.concurrentutil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
		Thread parsr = new Thread(() -> {
			System.out.println(1);
			c.countDown();
			System.out.println(2);
			c.countDown();
		});
		parsr.start();
		c.await(3, TimeUnit.SECONDS);
		System.out.println(3);


	}

}
