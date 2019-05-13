package com.lin.test.adaptive;

import org.apache.dubbo.common.URL;

/**
 * Created by lys on 5/13/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public interface CarMaker {

    Car makeCar(URL url);
}
