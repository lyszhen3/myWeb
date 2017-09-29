package mvctest;

import com.lin.data.beans.Account;
import com.lin.data.mappers.TestMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pc on 2017/9/29.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/lys_spring_test.xml")
public class TestMapperDemo {
    @Autowired
    TestMapper mapper;
    @Test
    public void test(){
        Account account = mapper.selectByPrimaryKey(1);
        System.out.println(account.getName());
    }
}
