package com.lin.test.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lys on 5/13/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ClientTest {
    public static void main(String[] args) {
        //要把文件放在META-INF.dubbo.internal 下..
        ExtensionLoader<WheelMaker> extensionLoader = ExtensionLoader.getExtensionLoader(WheelMaker.class);
        WheelMaker maker = extensionLoader.getAdaptiveExtension();
        //需要设置参数
        Map<String, String> param = new HashMap<>();
//        param.put("Wheel.maker", "wheelMakerName");
        URL url = new URL("p1", "1.2.3.4", 1010, "path1", param);
//        maker.makeWheel(url);
        maker.hello(url);
    }
}
