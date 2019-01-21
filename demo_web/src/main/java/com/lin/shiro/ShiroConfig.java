package com.lin.shiro;


import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2019/1/18.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Configuration
public class ShiroConfig {


	@Bean
	public MyselfRealm myselfRealm(){
		return new MyselfRealm();
	}

	@Bean
	public DefaultWebSecurityManager securityManager(@Autowired MyselfRealm myselfRealm,
													 @Autowired EhCacheManager ehCacheManager,
													 @Autowired DefaultWebSessionManager defaultWebSessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myselfRealm);
		securityManager.setCacheManager(ehCacheManager);
		securityManager.setSessionManager(defaultWebSessionManager);
		return securityManager;
	}

	@Bean
	public EhCacheManager ehCacheManager(){
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:shiro/shiro-ehcache.xml");
		return ehCacheManager;
	}



	/**
	 * shiro session的管理
	 */
	@Bean
	public DefaultWebSessionManager defaultWebSessionManager(@Autowired EnterpriseCacheSessionDAO enterpriseCacheSessionDAO) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		//30分钟
		sessionManager.setGlobalSessionTimeout(TimeUnit.MINUTES.toMillis(30));
		sessionManager.setSessionDAO(enterpriseCacheSessionDAO);
		//定时检查失效的session
		sessionManager.setSessionValidationSchedulerEnabled(false);
		return sessionManager;
	}


	@Bean
	public EnterpriseCacheSessionDAO enterpriseCacheSessionDAO(){
		EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		return enterpriseCacheSessionDAO;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Autowired SecurityManager securityManager) throws Exception {
		ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
		filterFactoryBean.setSecurityManager(securityManager);

		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

//        filterChainDefinitionMap.put("/endpoint/error/require_login", "anon");//异常
//		filterChainDefinitionMap.put("/**", "authc");
		//所有都开放?
		filterChainDefinitionMap.put("/**", "anon");
		filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return  filterFactoryBean;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}




	/**
	 * aop权限检查
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		creator.setUsePrefix(true);
		return creator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Autowired SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

}
