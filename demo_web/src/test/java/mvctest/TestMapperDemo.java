package mvctest;

import com.lin.data.beans.Book;
import com.lin.data.mappers.BookMapper;
import com.lin.data.mappers.RoleMapper;
import com.lin.data.mappers.TestMapper;
import com.lin.test.services.abstracts.AbstractSmsTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.logging.Logger;

/**
 * Created by pc on 2017/9/29.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 * <p>
 * SpringJUnit4ClassRunner 支持多线程
 * Junit4可不支持
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/lys_spring_test.xml")
public class TestMapperDemo {
    private final static Logger log = Logger.getLogger(TestMapperDemo.class.getName());
    @Autowired
    TestMapper mapper;
    @Autowired
    BookMapper bookMapper;

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    AbstractSmsTest abstractSmsTest;
    @Test
    public void test() {
        Book book = new Book();
        book.setStatus("不行");
        book.setTitle("我觉得ok");
        book.setDoubanId("11");
        book.setOwnerId(111L);
        bookMapper.insertSelective(book);
        log.info(String.valueOf(book.getId()));

    }
    @Test
    public void testMoreThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i);
                }
            }
        });
        thread.start();
    }
    @Test
    public void testBaomidou(){
    	abstractSmsTest.setNamef("nihao");
        System.out.println(abstractSmsTest.getName());
        abstractSmsTest.ssout();

    }

}
