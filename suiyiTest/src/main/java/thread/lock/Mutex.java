package thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by lys on 2018/7/27.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Mutex implements Lock {

	private static class Sync extends AbstractQueuedSynchronizer {

		//是否处于占用状态

		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		//当状态为0的时候获取锁
		public boolean tryAcquire(int acquires) {
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		//释放锁，将状态设置为0


		@Override
		protected boolean tryRelease(int releases) {
			if(getState()==0)
				throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}

		Condition newCondition(){
			return new ConditionObject();
		}
	}
	private final Sync sync = new Sync();

	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1,unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}

	public boolean isLocked(){
		return sync.isHeldExclusively();
	}

	public boolean hasQueuedThreads(){
		return sync.hasQueuedThreads();
	}
}
