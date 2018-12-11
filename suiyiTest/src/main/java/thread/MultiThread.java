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

				//Thread.yield()方法作用是：暂停当前正在执行的线程对象，并执行其他线程。
				// yield()应该做的是让当前运行线程回到可运行状态，以允许具有相同优先级的其他线程获得运行机会。
				// 因此，使用yield()的目的是让相 同优先级的线程之间能适当的轮转执行。但是，实际中无法保证yield()达到让步目的，
				// 因为让步的线程还有可能被线程调度程序再次选中。
				//结论：yield()从未导致线程转到等待/睡眠/阻塞状态。在大多数情况下，yield()将导致线程从运行状态转到可运行状态，但有可能没有效果。

				Thread.yield();
			}
			while (noEnd) {
				Thread.yield();
				jobCount++;
			}
		}
	}

}
