import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by lys on 2018/11/30.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestThread {
	private static final ThreadPoolExecutor poolexcutor = new ThreadPoolExecutor(10, 20, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<>(5),new ThreadFactoryBuilder().setDaemon(false).setNameFormat("account-pool-%d").build());



	public static void main(String[] args) {


		for (int i = 0; i < 1000; i++) {
			int finali = i;
			poolexcutor.execute(() -> {

				try {
					System.out.println(Thread.currentThread().getName()+"~~开始");
					TimeUnit.SECONDS.sleep(3L);
					System.out.println(Thread.currentThread().getName()+"~~over");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(finali);

			});

		}
		poolexcutor.shutdown();
	}
}
