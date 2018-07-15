package thread;

/**
 * Created by lys on 2018/7/14.
 * ReorderExample(重排序实力)
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SyschronizedExample {

	int a = 0;
	boolean flag = false;

	public synchronized void writer() {
		//获取锁
		a = 1;
		flag = true;
	}//释放锁

	public synchronized void reader() {
		//获取锁
		if (flag) {
			int i = a;

		}
	}//释放锁
}
