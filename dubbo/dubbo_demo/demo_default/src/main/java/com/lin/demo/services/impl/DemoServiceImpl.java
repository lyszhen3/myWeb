package com.lin.demo.services.impl;

import com.lin.demo.services.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by pc on 2017-10-10.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {
    private final static Logger log =  LoggerFactory.getLogger(DemoServiceImpl.class);
    @Override
    public void helloWorld() {
        System.out.println("hello dubbo");
        log.info("hello");
    }
}
