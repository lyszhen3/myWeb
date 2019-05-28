package springannotation.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by lys on 2018/11/8.
 * <p>
 * 执行顺序 aop --> around --> before --> around --> after -->afterReturning
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Component
@Aspect
public class AspectDemo {

    /**
     * 第一个*号表示匹配所有类型返回值
     */
    @Pointcut(value = "execution(* springannotation.aop.AopTestService.hello(String||Integer))")
    public void pointMethod() {

    }

    @Pointcut("@annotation(springannotation.aop.AopAnnotation)")
    public void pointMethod2() {

    }

    /**
     * args(id) "id"匹配方法里的参数id 代表代理方法里的首个匹配到的此类型的参数
     * --[@annotation(aopAnnotation) ]代表代理方法上的注解
     *
     * @param joinPoint
     * @param id
     * @param aopAnnotation
     * @param paramAnnotation
     * @return
     */
    @Before(value = "pointMethod()&&args(id)&&@annotation(aopAnnotation)&&@annotation(paramAnnotation)", argNames = "joinPoint,id,aopAnnotation,paramAnnotation")
    public Object before(JoinPoint joinPoint, Object id, AopAnnotation aopAnnotation, ParamAnnotation paramAnnotation) {
        System.out.println(aopAnnotation.des());
        System.out.println("作者：" + paramAnnotation.name());
        System.out.println(id);
        System.out.println("之前" + "参数=" + joinPoint.getArgs()[0]);
        System.out.println("传入的id：" + id);
        return null;
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
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @return object 一个被切入方法的返回值
     */
    @Around("pointMethod()")
    public Object aa(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg.equals(1)) {
                //这里return 会导致before 没进去。。
//                return 3;
            }
        }
        return proceedingJoinPoint.proceed();
    }

}
