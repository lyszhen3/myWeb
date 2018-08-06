package thread.volatileintroduce;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/7/15.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class VolatileTest {

	int i;
	int j;
	boolean t = false;

	@Test
	public void volatileReadFirst() {
		j = 2;
		i = 1;
	}

	public void commanReadFirst() {
		setI(2);
		getJ();
		getT();
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public boolean getT() {
		return t;
	}

	public static void main(String[] args) throws InterruptedException {
		Bar bar = new Bar();
		class Runner implements Runnable{
			private long i;
			public Runner(long id){
				this.i = id;
			}

			public void run() {
//				instance.setFoo();
				bar.setS(i);
				System.out.println("seti="+i+" geti="+bar.getS());
			}
		}
		List<Thread> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			Thread thread = new Thread(new Runner((long)i),String.format("Thread[%d]",i));
			list.add(thread);
		}

		list.parallelStream().forEach(Thread::start);

		TimeUnit.SECONDS.sleep(3);

	}
}
