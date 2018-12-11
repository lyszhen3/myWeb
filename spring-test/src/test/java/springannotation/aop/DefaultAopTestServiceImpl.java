package springannotation.aop;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lys on 2018/11/8.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Service
//@Primary
public class DefaultAopTestServiceImpl implements AopTestService{
	@Override
	public void hello(String name) {
		System.out.println("hello  "+name);
	}

	@Override
	public int hello(Integer id) {
		System.out.println("hello "+id);
		return id;
	}
}
