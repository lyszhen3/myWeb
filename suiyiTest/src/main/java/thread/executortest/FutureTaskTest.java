package thread.executortest;

import java.util.concurrent.*;

/**
 * Created by lys on 2018/8/19.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class FutureTaskTest {

	private final ConcurrentHashMap<Object, Future<String>> taskCache
			= new ConcurrentHashMap<>();
	private String executionTask(final String taskName){
		while(true){
			//1.1,2.1
			Future<String> future = taskCache.get(taskName);

			if(future == null){
				Callable<String> task = () -> taskName;
				//1.2 创建任务
				FutureTask<String> futureTask = new FutureTask<>(task);
				//1.3
				future = taskCache.putIfAbsent(taskName,futureTask);
				if(future == null ){
					future = futureTask;
					//1.4执行任务
					futureTask.run();
				}

			}
			try {
				//1.5,2.2
				return future.get();
			} catch (InterruptedException | ExecutionException ignore) {
			}
		}

	}

	public static void main(String[] args) {
		FutureTaskTest t1 = new FutureTaskTest();
		String first = t1.executionTask("第一");
		String second = t1.executionTask("第二");
		String third = t1.executionTask("第一");
		System.out.println(first);
		System.out.println(second);
		System.out.println(third);
	}
}
