package com.lin.test.spitest;

import org.apache.dubbo.common.extension.SPI;

/**
 * Created by lys on 4/27/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@SPI
public interface SpiHello {
	void hello();
}
