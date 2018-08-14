package com.executors;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * Created by lys on 6/14/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ScheduleThreadTest {

	static ScheduledExecutorService ee = Executors.newScheduledThreadPool(1);
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ScheduleThreadTest test = new ScheduleThreadTest();
		test.test();
	}
	public void test() throws ExecutionException, InterruptedException {
		Task task = new Task(String.valueOf(1));
//		ScheduledFuture<JSONObject> schedule = ee.schedule(task, 5, TimeUnit.SECONDS);

//		if (schedule.get().getString("msg").equals("ok")) {
//			excutors.shutdown();
//		}
		ScheduledFuture<?> scheduledFuture = ee.scheduleWithFixedDelay(new TaskRunnable(ee), 0, 1, TimeUnit.SECONDS);


	}

	class TaskRunnable implements Runnable {
		ExecutorService executorService;
		TaskRunnable(ExecutorService executorService){
			this.executorService = executorService;
		}
		@Override
		public void run() {
			System.out.println(111);
			LocalDateTime now = LocalDateTime.now();
			if(now.getSecond()>50){
				executorService.shutdown();
			}
		}
	}

	class Task implements Callable<JSONObject> {
		private String id;

		public Task(String id) {
			this.id = id;
		}

		@Override
		public JSONObject call() throws Exception {
			System.out.println("执行" + this.id);
			if ("100".equals(id)) {
				return JSONObject.parseObject("{\"msg\":\"ok\"}");
			} else {
				return JSONObject.parseObject("{\"msg\":\"error\"}");
			}
		}
	}

}
