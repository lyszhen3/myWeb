package thread.concurrentutil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by lys on 2018/8/14.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class CyclicBarrierTest {
	//如果是3 下面只有两个await 所以永远到达不到3个线程到达屏障的要求，会永远阻塞
	//都到达屏障后先执行 A
	static CyclicBarrier c = new CyclicBarrier(2,new A());

	public static void main(String[] args) {
		new Thread(() -> {
			try {
				c.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println(1);
		}).start();


		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println(2);
	}

	static class A implements Runnable{

		@Override
		public void run() {
			System.out.println(3);
		}
	}
}
