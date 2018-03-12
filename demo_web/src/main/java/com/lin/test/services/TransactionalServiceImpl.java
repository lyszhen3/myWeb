package com.lin.test.services;

import com.lin.data.beans.Account;
import com.lin.data.mappers.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lys on 1/8/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Service
public class TransactionalServiceImpl implements TransactionalService {

    private AccountMapper accountMapper;
    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }
    @Override
    @Transactional
    public void a() {

        this.b();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void b() {
        Account account = new Account();
        account.setId(1L);
        account.setName("天才爱因斯坦");
        accountMapper.updateByPrimaryKeySelective(account);
        throw new RuntimeException("回滚么");
    }
}
