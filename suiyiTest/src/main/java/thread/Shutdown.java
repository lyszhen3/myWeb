package thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/7/25.
 * 线程终止的两种方法。。
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Shutdown {
	public static void main(String[] args) throws InterruptedException {
		Runner one = new Runner();
		Thread countThread = new Thread(one,"CountThread");
		countThread.start();
		//睡眠1秒，main线程对CountThread进行中断，使CountThread 能够感知中断而结束
		TimeUnit.SECONDS.sleep(1);
		countThread.interrupt();
		Runner two = new Runner();
		countThread = new Thread(two,"CountThread");
		countThread.start();
		//睡眠1秒 main线程对Runner two 进行取消，使CountThread 能够感知on 为false 而结束
		TimeUnit.SECONDS.sleep(1);
		two.cancel();
	}

	private static class Runner implements Runnable {
		private long i;
		private volatile boolean on = true;

		@Override
		public void run() {
			while (on && !Thread.currentThread().isInterrupted()) {
				i++;
			}
			System.out.println("Count i= " + i);
		}
		public void cancel(){
			on = false;
		}
	}


}
