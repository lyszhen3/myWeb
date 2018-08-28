package thread.executortest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
		//second param : 最大线程数,代表能同时执行任务的最大线程数,如果等待队列满了,并且线程数少于最大线程数,则创建任务执行
		//third param  : 空闲线程等待时间,如果线程没有任务执行,设置为0表示线程无任务执行将立即关闭
		//fourth param : 时间类型
		//fifth param : 等待队列
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
				0l, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
	}
}
