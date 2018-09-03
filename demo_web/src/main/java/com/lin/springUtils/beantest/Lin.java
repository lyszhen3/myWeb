package com.lin.springUtils.beantest;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Created by lys on 2018/8/30.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */

@Service
@Scope(value = "prototype",proxyMode = ScopedProxyMode.INTERFACES)
public class Lin extends AbstractLin{
	public Lin(String id, String name) {
		super(id, name);
	}

	public Lin(){
		super();
	}
}
