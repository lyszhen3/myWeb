package springannotation.aop;

import java.lang.annotation.*;

/**
 * @author LinYuanSheng
 * @date 05/28/2019
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AopAnnotation {
    String des() default "天才";
}
