package com.lin.testaop.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lin.testaop.Lys;
import com.lin.testaop.TestAopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pc on 2017-07-18.
 * aop 测试
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Controller
public class TestAop {

    private TestAopService service;

    @Autowired
    public void setService(TestAopService service) {
        this.service = service;
    }

    @Lys
    @RequestMapping("testAop")
    @ResponseBody
    public JSONObject testAop(String id){
        service.testService("hello aop");
        System.out.println("over");
        return JSON.parseObject("{'name':'lys'}");

    }

    public static void main(String[] args) {

    }
}
