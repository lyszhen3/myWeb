package com.lin.test.services;

import org.springframework.stereotype.Service;

/**
 * Created by lys on 3/12/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Service
public class MoreSecondImpl extends AbstractTestMore{
    {
        System.out.println("-----------"+this+"-----------");
    }
    @Override
    public void say() {
        System.out.println("second");
    }
}
