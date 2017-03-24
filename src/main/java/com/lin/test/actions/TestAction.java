package com.lin.test.actions;
import com.alibaba.fastjson.JSONObject;
import com.lin.test.beans.Shop;
import com.lin.test.bo.UserBo;
import com.lin.test.services.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
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

    @RequestMapping("upimg")
    @ResponseBody
    public JSONObject upimg(MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        JSONObject obj = new JSONObject();

        obj= JSONObject.parseObject("{\n" +
                "            \"code\": 0 //0表示成功，其它失败\n" +
                "                ,\"msg\": \"\" //提示信息 //一般上传失败后返回\n" +
                "                ,\"data\": {\n" +
                "            \"src\": \"upimg/qb110.jpg\"\n" +
                "                    ,\"title\": \"图片名称\" //可选\n" +
                "        }\n" +
                "        }");
        System.out.println(obj.toString());
        return obj;

    }
}
