package com.lin.springUtils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pc on 2017-10-17.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
@Configuration
public class WebSpringFactory implements ApplicationContextAware{

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebSpringFactory.applicationContext=applicationContext;
    }

    public static <T> T getBean(Class<T> clazz,Object... objs){
        return applicationContext.getBean(clazz,objs);
    }
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
