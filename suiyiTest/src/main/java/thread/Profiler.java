package thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/7/25.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Profiler {
	//第一次get()方法调用时会进行初始化(如果set()方法没有调用),每个线程都会调用一次
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
		@Override
		protected Long initialValue() {
			return System.currentTimeMillis();
		}
	};

	public static final void begin() {
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}

	public static final long end() {
		return System.currentTimeMillis() - TIME_THREADLOCAL.get();
	}

	public static void main(String[] args) throws InterruptedException {
		Profiler.begin();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("cost:" + Profiler.end() + " mills");
	}

}
