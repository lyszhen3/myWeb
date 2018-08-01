package thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/7/31.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SleepUtils{
	public static final void second(long seconds){

		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
