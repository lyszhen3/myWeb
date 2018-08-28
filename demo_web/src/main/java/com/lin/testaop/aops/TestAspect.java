package com.lin.testaop.aops;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Created by pc on 2017-07-18.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */

@Component
@Aspect
public  class TestAspect {


	public TestAspect() {
		System.out.println("--------------切面类加载--------------");
	}

	@Pointcut("@annotation(com.lin.testaop.Lys)")
	public  void aopTest(){}

	@Before("aopTest()")
	public void beforeExcution(JoinPoint joinPoint) {

		System.out.println("aop--之前");
		joinPoint.getStaticPart();
	}

	@AfterReturning("aopTest()")
	public void afterExcution(JoinPoint joinPoint) {
		System.out.println("aop--之后");
		joinPoint.getStaticPart();
	}

	/**
	 * 1.@Around 增强处理是功能比较强大的增强处理，它近似等于Before 和 AfterReturning的总和。
	 * <p> @Around既可在执行目标方法之前织入增强动作，也可在执行目标方法之后织入增强动作。</p>
	 * <p> @Around甚至可以决定目标方法在什么时候执行，如何执行，更甚者可以完全阻止目标方法的执行。</p>
	 * 2.@Around可以改变执行目标方法的参数值，也可以改变执行目标方法之后的返回值。
	 * 3.@Around功能虽然强大，但通常需要在线程安全的环境下使用。
	 * 因此，如果使用普通的Before、AfterReturning就能解决的问题，就没有必要使用Around了。
	 * 如果需要目标方法执行之前和之后共享某种状态数据，则应该考虑使用Around。
	 * 尤其是需要使用增强处理阻止目标的执行，或需要改变目标方法的返回值时，则只能使用Around增强处理了。
	 *
	 * @param joinPoint joinPoint
	 * @return object 一个被切入方法的返回值
	 * @throws Throwable 异常
	 */
	@Around("aopTest()")
	public Object aroundExcution(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("aop--酱油");
		return joinPoint.proceed();
	}


}
