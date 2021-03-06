package thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/7/25.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Daemon {

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new DaemonRunner(),"DaemonRunner");
		thread.setDaemon(true);
		thread.start();
	}

	static class DaemonRunner implements Runnable{

		@Override
		public void run() {
			try {
				SleepUtils.second(10);
			} finally {
				System.out.println("Daemon Thread finally run");
			}
		}
	}
}
