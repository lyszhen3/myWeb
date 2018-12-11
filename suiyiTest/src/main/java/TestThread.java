import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/11/30.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestThread {
	private static final ThreadPoolExecutor poolexcutor = new ThreadPoolExecutor(10, 20, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>());


	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			int finali = i;
			poolexcutor.execute(() -> {

				try {
					TimeUnit.SECONDS.sleep(3L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(finali);

			});
		}

	}
}
