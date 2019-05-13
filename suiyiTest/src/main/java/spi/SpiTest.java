package spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by lys on 4/27/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SpiTest {
	public static void main(String[] args) {
		ServiceLoader<SpiHello> serviceLoader = ServiceLoader.load(SpiHello.class);
		Iterator<SpiHello> iterator = serviceLoader.iterator();
		while (iterator.hasNext()){
			SpiHello next = iterator.next();
			next.sayHello();
		}
	}
}
