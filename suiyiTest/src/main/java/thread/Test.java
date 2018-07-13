package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lys on 7/10/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Test {
	/**
	 * 即使是volatile 也不能保证在多线程中+算的正确性
	 */
//	public static volatile int  i = 0;
	public static AtomicInteger a = new AtomicInteger(0);


	public static void main(String[] args) {

		for (int j = 0; j < 100; j++) {
			int finalJ = j;
			Thread t = new Thread("线程名"+ finalJ){
				@Override
				public void run() {
					for (int k = 0; k < 1000; k++) {
						a.getAndAdd(1);
						System.out.println(a.get());
					}
				}
			};
			t.start();
		}

	}
}
