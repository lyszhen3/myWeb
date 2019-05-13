package spi;

/**
 * Created by lys on 4/27/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SpiImpl2 implements SpiHello {
	@Override
	public void sayHello() {
		System.out.println("impl2");
	}
}
