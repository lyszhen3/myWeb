package thread.concurrentutil;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lys on 2018/8/16.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ExchangerTest {
	private static final Exchanger<String> exgr = new Exchanger<>();
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {
		threadPool.execute(()->{
			try {
				//A录入银行的数据
				String A = "银行流水A";
				exgr.exchange(A);
			} catch (InterruptedException ignore) {
			}
		});
		threadPool.execute(()->{
			try {
				//B录入银行的数据
				String B = "银行流水B";
				String A = exgr.exchange(B);
				System.out.println("A和B数据是否一致:"+A.equals(B)+",A录入的是:"+A+",B录入的是:"+B);
			} catch (InterruptedException ignore) {
			}
		});

		threadPool.shutdown();
	}
}
