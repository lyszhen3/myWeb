package thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/7/25.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Interrupted {

	public static void main(String[] args) throws InterruptedException {
		//sleepThread 不停的尝试睡眠
		Thread sleepThread = new Thread(new SleepRunner(),"SleepThread");
		sleepThread.setDaemon(true);
		//busyThread 不停的运行
		Thread busyThread = new Thread(new BusyRunner(),"BusyThread");
		busyThread.setDaemon(true);
		sleepThread.start();
		busyThread.start();
		//休眠五秒 让sleepThread 喝 BusyThread 充分运行
		TimeUnit.SECONDS.sleep(5);
		sleepThread.interrupt();
		busyThread.interrupt();
		//线程中断操作会让sleep线程报出中断报错,并清除中断标志
		System.out.println("SleepThread interrupted is "+sleepThread.isInterrupted());
		System.out.println("BusyThread interrupted is "+busyThread.isInterrupted());
		//防止sleepThread 喝 BusyThread 立即退出
//		SleepUtils.second(2);
	}

	static class SleepRunner implements Runnable{

		@Override
		public void run() {
			while(true){
				SleepUtils.second(10);
			}
		}
	}

	static class BusyRunner implements Runnable{

		@Override
		public void run() {
			while(true){
			}
		}
	}
}
