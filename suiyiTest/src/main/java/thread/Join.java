package thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/7/25.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Join {
	public static void main(String[] args) throws InterruptedException {
		Thread previous = Thread.currentThread();
		for (int i = 0; i < 10; i++) {
			//每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
			Thread thread = new Thread(new Domiao(previous), String.valueOf(i));
			thread.start();
			previous = thread;
		}
		TimeUnit.SECONDS.sleep(5);
		System.out.println(Thread.currentThread().getName() + " terminate");

	}

	static class Domiao implements Runnable {
		private Thread thread;

		public Domiao(Thread thread) {
			this.thread = thread;
		}

		@Override
		public void run() {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " terminate");
		}
	}
}
