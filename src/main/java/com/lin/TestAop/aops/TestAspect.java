package com.lin.TestAop.aops;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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
public class TestAspect {



    public TestAspect(){
        System.out.println("--------------切面类加载--------------");
    }

    @Pointcut("@annotation(com.lin.TestAop.Lys)")
    public void aopTest(){
        System.out.println("我是切入点");
    }

    @Before("aopTest()")
    public void beforeExcution(JoinPoint joinPoint){
        System.out.println("之前");
        joinPoint.getStaticPart();
    }

    @After("aopTest()")
    public void afterExcution(JoinPoint joinPoint){
        System.out.println("之后");

    }

    @Around("aopTest()")
    public void aroundExcution(ProceedingJoinPoint joinPoint) throws Throwable {
       System.out.println("酱油");
        joinPoint.proceed();

    }



}
