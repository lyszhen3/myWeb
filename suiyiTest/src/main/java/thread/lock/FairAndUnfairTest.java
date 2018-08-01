package thread.lock;

import org.junit.Test;
import thread.SleepUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Created by lys on 2018/7/31.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class FairAndUnfairTest {

	private static Lock fairLock = new ReentrantLock2(true);
	private static Lock unfairLock = new ReentrantLock2(false);

	/**
	 * 同一线程不是连续获取重入锁
	 */
	@Test
	public void fair(){
		testLock(fairLock);
	}

	/**
	 * 同一线程大概率连续获取重入锁
	 */
	@Test
	public void unFair(){
		testLock(unfairLock);
	}


	private void testLock(Lock lock){
		List<Job> jobs = new ArrayList<>(10);
		for (int i = 0; i < 5; i++) {
			Job job = new Job(lock);
			job.setName(String.format("%d",i));
			jobs.add(job);
		}
		jobs.parallelStream().forEach(Job::start);

		SleepUtils.second(2);
	}

	private static class Job extends Thread{
		private Lock lock;
		public Job(Lock lock){
			this.lock = lock;
		}
		@SuppressWarnings("Duplicates")
		@Override
		public void run() {
			lock.lock();
			try {
				Collection<Thread> queuedThreads = ((ReentrantLock2) lock).getQueuedThreads();
				List<String> collect = queuedThreads.stream().map(Thread::getName).collect(Collectors.toList());
				String join = String.join(",", collect);
				System.out.println(String.format("Lock by [%s], Waiting by [%s]",Thread.currentThread().getName(),join));
			} finally {
				lock.unlock();
			}
			lock.lock();
			try {
				Collection<Thread> queuedThreads = ((ReentrantLock2) lock).getQueuedThreads();
				List<String> collect = queuedThreads.stream().map(Thread::getName).collect(Collectors.toList());
				String join = String.join(",", collect);
				System.out.println(String.format("Lock by [%s], Waiting by [%s]",Thread.currentThread().getName(),join));
			} finally {
				lock.unlock();
			}
		}


	}

	private static class ReentrantLock2 extends ReentrantLock{
		public ReentrantLock2(boolean fair){
			super(fair);
		}

		public Collection<Thread> getQueuedThreads(){
			List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
			Collections.reverse(arrayList);
			return arrayList;
		}
	}
}
