package springannotation.construction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by lys on 2018/11/7.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConfigA.class)
public class ClientTest {

	@Autowired
	Environment environment;

	@Autowired
	Bar1 bar1;

	@Autowired
	Foo1 foo1;

	@Test
	public void test() {
		System.out.println(bar1.name);
		String t = environment.getProperty("t");
		System.out.println(t);
	}

	@Test
	public void test2() {
		System.out.println(foo1.getFoo2());
	}
}
