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
        Account account = mapper.selectByPrimaryKey(1);
        System.out.println(account.getName());
        Book book = bookMapper.selectByPrimaryKey(1);
        BookExample example = new BookExample();
        example.createCriteria().andTitleEqualTo("DSL实战");
        List<Book> books = bookMapper.selectByExample(example);
        System.out.println(books.get(0).getId());
    }
}
