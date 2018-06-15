package com.lin.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Created by lys on 6/15/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Service
//因为如果注入到单列bean中默认也会为单例，要加个proxyMode 接口就ScopedProxyMode.INTERFACES 类就ScopedProxyMode.TARGET_CLASS
@Scope(value = "prototype",proxyMode=ScopedProxyMode.INTERFACES)
public class PrototypeServiceImpl implements PrototypeService{


	private TestService testService;
	@Autowired
	public void setTestService(TestService testService) {
		this.testService = testService;
	}
	@Override
	public void innerBeanScope(){
		System.out.println(this.testService);
	}
}
