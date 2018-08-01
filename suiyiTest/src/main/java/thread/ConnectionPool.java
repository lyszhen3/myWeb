package thread;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lys on 2018/7/25.
 * 模拟数据库连接池
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ConnectionPool {
	private LinkedList<Connection> pool = new LinkedList<>();

	public ConnectionPool(int initalSize) {
		if (initalSize > 0) {
			for (int i = 0; i < initalSize; i++) {
				pool.addLast(ConnectionDriver.createConnection());
			}
		}
	}

	public void releaseConnection(Connection connection) {
		if (connection != null) {
			synchronized (pool) {

				//连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个链接
				pool.addLast(connection);
				pool.notifyAll();
			}
		}
	}

	public Connection fetchConnection(long mills) throws InterruptedException {
		synchronized (pool) {
			//完全超时
			if (mills <= 0) {
				while (pool.isEmpty()) {
					pool.wait();
				}
				return pool.removeFirst();
			} else {
				long future = System.currentTimeMillis() + mills;
				long remaining = mills;
				while (pool.isEmpty() && remaining > 0) {
					pool.wait(remaining);
					remaining = future - System.currentTimeMillis();
				}
				Connection result = null;
				if (!pool.isEmpty()) {
					result = pool.removeFirst();
				}
				return result;
			}
		}
	}
}


class ConnectionDriver {

	static class ConnectionHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("commit")) {
				TimeUnit.MILLISECONDS.sleep(100);
			}
			return null;
		}
	}

	public static final Connection createConnection() {

		return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(), new Class[]{Connection.class}, new ConnectionHandler());
	}


}

/**
 * 测试
 */
class ConnectionPoolTest {
	static ConnectionPool pool = new ConnectionPool(10);
	//保证所有ConnectionRunner 能够同事开始
	static CountDownLatch start = new CountDownLatch(1);
	//main 线程将会等待所有ConnectionRunner  结束后才会继续执行
	static CountDownLatch end;

	public static void main(String[] args) throws InterruptedException {
		//线程数量，可以修改线程数量进行观察
		int threadCount = 100;
		end = new CountDownLatch(threadCount);
		int count = 20;
		AtomicInteger got = new AtomicInteger();
		AtomicInteger notGot = new AtomicInteger();
		for (int i = 0; i < threadCount; i++) {
			Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
			thread.start();
		}
		start.countDown();
		end.await();
		System.out.println("total invoke: " + (threadCount * count));
		System.out.println("got connection: "+got);
		System.out.println("not got connection: "+notGot);
	}

	static class ConnectionRunner implements Runnable {
		int count;
		AtomicInteger got;
		AtomicInteger notGot;

		public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
			this.count = count;
			this.got = got;
			this.notGot = notGot;
		}

		@Override
		public void run() {
			try {
				start.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (count > 0) {
				try {
					//从线程池中获取，如果1000毫秒内无法获取到，将会返回null
					//分别统计连接获取数和未获取数noGot
					Connection connection = pool.fetchConnection(1000);
					if (connection != null) {
						try {
							connection.createStatement();
							connection.commit();
						} catch (SQLException e) {
							e.printStackTrace();
						} finally {
							pool.releaseConnection(connection);
							got.incrementAndGet();
						}
					} else {
						notGot.incrementAndGet();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					count--;
				}
			}
			end.countDown();
		}
	}
}
