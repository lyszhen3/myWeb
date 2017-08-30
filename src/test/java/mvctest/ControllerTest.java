package mvctest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by pc on 2017-08-30.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/lys_spring.xml")
public class ControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    MockMvc mockMvc;
    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

     @Test
    public void test() throws Exception {
        String actual = "{\"shopId\": \"4955\",\"webSite\": \"hz\"}";
        String expect = "{\"shopId\": \"4955\",\"webSite\": \"hz\"}";

       this.mockMvc
            .perform(post("/testDemo2.json")
                     .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
               .param("actual", actual)
               .param("expect", expect))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json;charset=UTF-8"))
               .andExpect(jsonPath("$.result").value("error"));
//               .andExpect(jsonPath("$[0].actual").value("4955"))
//               .andExpect(jsonPath("$[0].expected").value("4956"));
    }

}
