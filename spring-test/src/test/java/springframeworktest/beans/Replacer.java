package springframeworktest.beans;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * @author LinYuanSheng
 * @date 06/12/2019
 */
public class Replacer implements MethodReplacer {
    @Override
    public Object reimplement(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("replace方法开始");
        System.out.println(o);
        method.invoke(o);
        System.out.println("replace方法结束");
        return null;
    }
}
