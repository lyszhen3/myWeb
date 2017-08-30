package com.lin.test.services;

import com.lin.test.mappers.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by lys on 2017-07-30.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@Service
public class TestService2 {

    TestMapper testMapper;
    @Autowired
    public void setTestMapper(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    //    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void delete() throws Exception {

        testMapper.deleteOne(4L);
       // throw new Exception("我的天");
        String xx = null;
        xx.split("|");
    }
}
