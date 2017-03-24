package com.lin.test.actions;

import com.lin.test.services.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by pc on 2017-03-21.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Controller
public class TestAction {

    @Resource(name="lin_testService")
    TestService testService;

    @RequestMapping(value="test")
    public String test(Model model){
       int i= testService.testCount();

       model.addAttribute("count",i);

       return "sysman/mainIndex";
    }

    @RequestMapping(value = "rightPanel" , method = RequestMethod.GET)
    public String rightPanel(){

        return "sysman/common/rightPanel";
    }
}
