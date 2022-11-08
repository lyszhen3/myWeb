package regex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 测试正则性能
 * @author LinYuanSheng
 * @date 2022/11/8
 */
public class TestRex {

	private static List<Future<Long>> list = new ArrayList<>();
	private static long count = 0;
	public static void main(String[] args) {
		final ExecutorService executorService = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 3; i++) {
			//三个线程执行100次
			 Callable<Long> integerFutureTask = () -> {
				 final long start = System.currentTimeMillis();
				 for (int j = 0; j < 100; j++) {
					 Rex rex = new Rex();
					 String ii = "承诺10月9日送达";
					 final String string2 = rex.getString2(ii, "\\d{1,2}月\\d{1,2}日");
					 final String replace = ii.replace(string2, string2 + "12:00前");
				 }

				 final long end = System.currentTimeMillis();
				 System.out.println("nei---" + (end - start));
				 return (end - start);
			 };

			final Future<Long> submit = executorService.submit(integerFutureTask);
			list.add(submit);
		}
		for (Future<Long> longFuture : list) {
			try {
				System.out.println(longFuture.get());
				count += longFuture.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println(count/3);

		executorService.shutdown();
	}
}
