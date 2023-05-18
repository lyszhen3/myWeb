package com.lin.springUtils.beantest;

import com.lin.data.beans.Account;
import com.lin.data.mappers.AccountMapper;
import com.lin.Test.services.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * Created by lys on 2018/8/30.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public abstract class AbstractLin implements Task {

	private String id;
	private String name;

	private Account account;

	private AccountMapper accountMapper;

	@Autowired
	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}

	private TransactionalService transactionalService;

	@Autowired
	public void setTransactionalService(TransactionalService transactionalService) {
		this.transactionalService = transactionalService;
	}

	public AbstractLin(){

	}

	public AbstractLin(String id,String name){
		this.id = id;
		this.name =name;
	}

	@Transactional
	public void hello(){
		System.out.println(accountMapper);
		System.out.println("hello");
	}

	@PostConstruct
	public void init(){
		Account account = accountMapper.selectByPrimaryKey(1L);
		this.account = account;
		System.out.println(account.getName());
	}
}
