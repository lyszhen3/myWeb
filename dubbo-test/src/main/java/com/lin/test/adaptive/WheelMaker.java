package com.lin.test.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * Created by lys on 5/13/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@SPI("adaptiveWheelMaker")
public interface WheelMaker {
    @Adaptive
    Wheel makeWheel(URL url);

    @Adaptive
    void hello(URL url);
}
