package thread.concurrentutil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/8/15.
 * 信号量
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SemaphoreTest {

	private static final int THREAD_COUNT = 30;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
	/**
	 * 表示5个允许通过证明,拿到的可以执行线程,否则阻塞等待获取信号
	 */
	private static Semaphore s = new Semaphore(5);

	public static void main(String[] args) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(()->{
				try {
					//获取信号
					s.acquire();
					System.out.println("save data");
					TimeUnit.SECONDS.sleep(2);
					//释放信号,供下一个使用
					s.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		threadPool.shutdown();

	}
}
