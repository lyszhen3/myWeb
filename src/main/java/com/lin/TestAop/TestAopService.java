package com.lin.TestAop;

import com.lin.TestAop.actions.LysFactory;
import org.springframework.stereotype.Service;

/**
 * Created by pc on 2017-07-18.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */

@Service
public class TestAopService {


    public void testService(){
        System.out.println("我正在走");

        System.out.println(this);

        TestAopService bean = LysFactory.getBean(TestAopService.class);
        System.out.println(bean);

    }
}
