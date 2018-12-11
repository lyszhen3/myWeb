package springannotation.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by lys on 2018/11/8.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Component
@Aspect
public class AspectDemo {


	@Pointcut(value = "execution(int springannotation.aop.AopTestService.hello(String||Integer))")
	public void pointMethod() {

	}

	@Before("pointMethod()")
	public void before() {
		System.out.println("之前");
	}


}
