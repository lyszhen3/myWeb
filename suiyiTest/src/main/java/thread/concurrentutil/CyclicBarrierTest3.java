package thread.concurrentutil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by lys on 2018/8/15.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class CyclicBarrierTest3 {
	static CyclicBarrier c = new CyclicBarrier(2);

	public static void main(String[] args) {
		Thread thread = new Thread(() -> {
			try {
				c.await();
			} catch (InterruptedException | BrokenBarrierException e) {
			}
		});
		thread.start();
		thread.interrupt();
		try {
			c.await();
		} catch (BrokenBarrierException | InterruptedException e) {
			System.out.println(c.isBroken());
		}

	}
}
