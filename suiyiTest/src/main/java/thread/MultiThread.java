package thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/7/24.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class MultiThread {
	public static void main(String[] args) {
		//获取java线程管理MxBean
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

		//不需要获取同步的monitor和synchronizer 信息，仅获取线程和线程堆栈信息
		ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
		//遍历线程信息，仅打印线程id和线程名称信息
		for (ThreadInfo threadInfo : threadInfos) {
			System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
		}
	}

}

/**
 * 优先级 有些操作系统没用比如ubuntu..
 * win10 竟然有效。。
 */
class Priority {
	private static volatile boolean noStart = true;
	private static volatile boolean noEnd = true;

	public static void main(String[] args) throws InterruptedException {
		List<Job> jobs = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
			Job job = new Job(priority);
			jobs.add(job);
			Thread thread = new Thread(job, "Thread:" + i);
			thread.setPriority(priority);
			thread.start();
		}
		noStart = false;
		TimeUnit.SECONDS.sleep(10);
		noEnd = false;
		for (Job job : jobs) {
			System.out.println("job Priority:" + job.priority + ",count:" + job.jobCount);
		}

	}


	static class Job implements Runnable {

		private int priority;
		private long jobCount;

		public Job(int priority) {
			this.priority = priority;
		}

		@Override
		public void run() {
			while (noStart) {
				Thread.yield();
			}
			while (noEnd) {
				Thread.yield();
				jobCount++;
			}
		}
	}

}
