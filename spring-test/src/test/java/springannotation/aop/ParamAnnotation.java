package springannotation.aop;

import java.lang.annotation.*;

/**
 * @author LinYuanSheng
 * @date 05/28/2019
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ParamAnnotation {
    String name() default "name";
}
