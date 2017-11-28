package com.lin.core.config;


import org.springframework.web.util.Log4jConfigListener;
import org.springframework.web.util.Log4jWebConfigurer;

import javax.servlet.ServletContextEvent;

/**
 * log4J监听器
 * Created by lin on 16/7/1.
 */
public class Log4jListener extends Log4jConfigListener {
    public static final String log4jdirkey = "LOG_DIR";
    public void contextDestroyed(ServletContextEvent servletcontextevent) {
        System.getProperties().remove(log4jdirkey);
        super.contextDestroyed(servletcontextevent);
    }

    public void contextInitialized(ServletContextEvent servletcontextevent) {
        System.out.println("我启动了？");
        String log4jdir = servletcontextevent.getServletContext().getRealPath("/");
        log4jdir=log4jdir+"/WEB-INF/logs";
        System.out.println("log4jdir:"+log4jdir);
        System.setProperty(log4jdirkey, log4jdir);
        super.contextInitialized(servletcontextevent);
    }
}
