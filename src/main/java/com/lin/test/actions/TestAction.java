package com.lin.test.actions;

import com.lin.test.beans.Shop;
import com.lin.test.bo.UserBo;
import com.lin.test.services.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value={"cllist"})
    public String cllist(UserBo bo,Model model){

        List<Shop> shops=new ArrayList<Shop>();
        for(int i=0;i<10;i++){
            Shop shop=new Shop();
            shop.setShopName("啊");
            shop.setUserName("嗯");
            shops.add(shop);
        }


        if(bo.getPage()==null){
            bo.setPage(1);

        }
        if(bo.getRows()==null){
            bo.setRows(10);
        }
        bo.setTotalPage(10);

        model.addAttribute("list",shops);
        model.addAttribute("bo",bo);



        return "sysman/shop/shoplist";
    }
}
