package com.lin.test.spitest;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * Created by lys on 4/27/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestClient {
    public static void main(String[] args) {
        ExtensionLoader<SpiHello> extensionLoader =
            ExtensionLoader.getExtensionLoader(SpiHello.class);
        SpiHello optimusPrime = extensionLoader.getExtension("spiImpl1");
        optimusPrime.hello();
        SpiHello bumblebee = extensionLoader.getExtension("spiImpl2");
        bumblebee.hello();
    }
}
