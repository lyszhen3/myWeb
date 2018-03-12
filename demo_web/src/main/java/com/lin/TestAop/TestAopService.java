package com.lin.TestAop;

import com.lin.springUtils.WebSpringFactory;
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

    @Lys
    public void testService(String hello_aop){
        System.out.println("我正在走");

        System.out.println(this);

        TestAopService bean = WebSpringFactory.getBean(TestAopService.class);
        System.out.println(bean);

    }
}
