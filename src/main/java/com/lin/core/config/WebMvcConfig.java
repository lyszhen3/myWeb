package com.lin.core.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.charset.Charset;
import java.util.*;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.shigu.**.actions")
public class WebMvcConfig extends WebMvcConfigurerAdapter {



//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.jsp("/WEB-INF/jsp/", ".jsp");
//        registry.enableContentNegotiation(new MappingJackson2JsonView());
//    }
    /**
     * freemarker视图配置
     *
     * @return
     */


    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
        freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
        freeMarkerViewResolver.setExposeRequestAttributes(true);
        freeMarkerViewResolver.setExposeSessionAttributes(true);
        freeMarkerViewResolver.setRequestContextAttribute("request");
        return freeMarkerViewResolver;
    }

    /**
     * freeMarkers基本信息配置
     *
     * @return
     */
    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/templates/");
        Properties p = new Properties();
        p.setProperty("template_update_delay", "0");
        p.setProperty("default_encoding", "UTF-8");
        p.setProperty("number_format", "0.#########");
        p.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
        p.setProperty("classic_compatible", "true");
        p.setProperty("template_exception_handler", "html_debug");//<!-- ignore,debug,html_debug,rethrow -->
//        p.setProperty("auto_import", "/common/head.ftl as h");
        freeMarkerConfigurer.setFreemarkerSettings(p);
        Map<String, Object> variables = new HashMap<String, Object>();
        //variables.put("shiro", new ShiroTags());
        freeMarkerConfigurer.setFreemarkerVariables(variables);
        return freeMarkerConfigurer;

    }

    @Bean
    public MessageSource messageSource() {
        String[] baseNames = "messages".split(",");
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames(baseNames);
        return resourceBundleMessageSource;
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/web/**").addResourceLocations("/web/");
//        registry.addResourceHandler("/assets/**").addResourceLocations(
//                "/assets/");
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index.htm");
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * JSON转换
     */
    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> media = new ArrayList<MediaType>();
        media.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(media);
        // 返回JSON格式时候的中文乱码问题
        converter.setCharset(Charset.forName("UTF-8"));

        converters.add(converter);
        super.configureMessageConverters(converters);
    }

    /**
     * 文件上传解析器
     *
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(104857600);
        resolver.setMaxInMemorySize(4096);
        resolver.setDefaultEncoding("UTF-8");
        return resolver;

    }

}

