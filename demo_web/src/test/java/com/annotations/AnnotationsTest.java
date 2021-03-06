package com.annotations;

import java.lang.annotation.Annotation;

/**
 * Created by lys on 2017-08-15.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@Lys(status = Lys.Status.STARTED,author = "lyh")
public class AnnotationsTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        AnnotationsTest annotationsTest = new AnnotationsTest();
        Class<? extends AnnotationsTest> aClass = annotationsTest.getClass();

        Annotation[] annotations = annotationsTest.getClass().getAnnotations();
        if(aClass.isAnnotationPresent(Lys.class)){//是否包含Lys注解
            Lys annotation = annotationsTest.getClass().getAnnotation(Lys.class);
            System.out.println(annotation.author());
        }
        for (Annotation annotation1 : annotations) {
            System.out.println(annotation1);
            System.out.println(annotation1.annotationType());

        }
    }
}
