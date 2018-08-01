package thread.pool;

/**
 * Created by lys on 2018/7/26.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public interface ThreadPool<Job extends Runnable> {
	//执行一个job
	void excute(Job job);

	//关闭线程池
	void shutdown();

	//增加工作者线程
	void addWorkers(int num);

	//减少工作者线程

	void removeWorker(int num);

	//得到正在等待执行的任务数量
	int getJobSize();
}
