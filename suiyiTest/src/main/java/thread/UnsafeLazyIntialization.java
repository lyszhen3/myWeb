package thread;

/**
 * Created by lys on 2018/7/24.
 * 非线程安全延迟初始化的例子
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class UnsafeLazyIntialization {

	private static Instance instance;

	public static Instance getInstance() {
		if (instance == null) {
			instance = new Instance();
		}
		return instance;
	}


}

/**
 * 线程安全延迟初始化对象
 */
class SafeLazyIntialization {
	private static Instance instance;

	public synchronized static Instance getInstance() {
		if (instance == null) {
			instance = new Instance();
		}
		return instance;
	}
}

/**
 * 由于早起synchronized 开销较大所以采取这个方式
 * 但是由于赋予对象内存地址和初始化 会存在重排的可能，所以此方法在多线程使用可能会产生错误
 * 正确方法
 * @see thread.SafeDoublecheckedLocking,thread.InstanceFactory
 */
class DoubleCheckedLocking {
	private static Instance instance;

	public static Instance getInstance() {
		if (instance == null) {
			synchronized (DoubleCheckedLocking.class) {
				if (instance == null) {
					instance = new Instance();
				}
			}
		}
		return instance;
	}

}

/**
 * 使用volatile 对象内存地址和初始化 不会重排序
 */
class SafeDoublecheckedLocking {
	private static volatile Instance instance;

	public static Instance getInstance() {
		if (instance == null) {
			synchronized (SafeLazyIntialization.class) {
				if (instance == null) {
					instance = new Instance();
				}
			}
		}
		return instance;
	}
}

/**
 * jvm 在类的初始化阶段（即在class被加载后，且被线程使用之前），会执行类的初始化。
 * 在执行类初始化期间，jvm会去获取一个锁。这个锁可以同步多个线程对同一个类的初始化。
 */
class InstanceFactory{
	private static class InstanceHolder{
		public static Instance instance = new Instance();
	}
	public static Instance getInstance(){
		return InstanceHolder.instance;  //这里将导致InstanceHolder 类被初始化
	}
}

class Instance {

}
