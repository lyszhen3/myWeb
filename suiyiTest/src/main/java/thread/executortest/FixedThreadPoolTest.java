package thread.executortest;

import java.util.concurrent.*;

/**
 * Created by lys on 2018/8/17.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class FixedThreadPoolTest {
	public static void main(String[] args) {

		//first param: 核心线程数,代表同时执行任务的线程数,超过核心线程数,新创建的线程将进入等待队列
		//second param : 最大线程数,代表能同时执行任务的最大线程数,如果等待队列满了,并且线程数少于最大线程数,则创建任务执行(如果等待队列是无界队列,则不会创建任务)
		//third param  : 空闲线程等待时间,如果线程没有任务执行,设置为0表示线程无任务执行将立即关闭
		//fourth param : 时间类型
		//fifth param : 等待队列 LinkedBlockingQueue 为无界队列 所以最大线程数无作用 若指定长度则为有界队列
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 200,
				10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));


		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<String> wo = executorService.submit(() -> {
			System.out.println("wo");
			return "hello";
		});
		try {
			String s = wo.get();
			System.out.println(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}finally {
			executorService.shutdown();
		}
	}
}
