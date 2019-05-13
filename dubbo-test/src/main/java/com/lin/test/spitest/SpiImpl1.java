package com.lin.test.spitest;

/**
 * Created by lys on 4/27/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SpiImpl1 implements SpiHello {
	@Override
	public void hello() {
		System.out.println("impl1");
	}
}
