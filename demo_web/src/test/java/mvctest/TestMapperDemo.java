package mvctest;

import com.lin.data.beans.Account;
import com.lin.data.beans.Book;
import com.lin.data.examples.BookExample;
import com.lin.data.mappers.BookMapper;
import com.lin.data.mappers.TestMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
    @Autowired
    BookMapper bookMapper;
    @Test
    public void test(){
        Book book = new Book();
        book.setStatus("不行");
        book.setTitle("我觉得ok");
        book.setDoubanId("11");
        book.setOwnerId(111L);
        bookMapper.insertSelective(book);
        System.out.println(book.getId());
    }
}
