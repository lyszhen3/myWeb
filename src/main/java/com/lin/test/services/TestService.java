package com.lin.test.services;

import com.lin.test.mappers.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by pc on 2017-03-21.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Service("lin_testService")
public class TestService {

    @Resource
    TestMapper testMapper;


    public int testCount(){
       int i= testMapper.testCount();
       return i;
    }
}