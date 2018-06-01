package com.lin.TestAop.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lin.TestAop.Lys;
import com.lin.TestAop.TestAopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pc on 2017-07-18.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@Controller
public class TestAop {

    @Autowired
    TestAopService service;
    @Lys
    @RequestMapping("testAop")
    @ResponseBody
    public JSONObject testAop(){
        System.out.println("???");
        service.testService("hello aop");
        System.out.println("over");
        JSONObject jsonObject = JSON.parseObject("{'name':'lys'}");
        return jsonObject;

    }

    public static void main(String[] args) {

    }
}
