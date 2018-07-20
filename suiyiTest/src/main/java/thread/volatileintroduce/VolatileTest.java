package thread.volatileintroduce;

import org.junit.Test;

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


}
