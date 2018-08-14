package com.lys;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lys.data.beans.Role;
import com.lys.data.mappers.RoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lys on 12/25/2017.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/data_source.xml")
public class test {
    @Autowired
    RoleMapper roleMapper;

    @Test
    public void insert(){
        Role role = new Role();
        role.setDescription("会不会有问题2");
        roleMapper.insert(role);
    }

    @Test
    public void delete(){
        roleMapper.deleteById(1111L);
    }

    @Test
    public void insertMillion(){
        int radio = 6;
        int base = 1000000;
        Integer count = roleMapper.selectCount(new EntityWrapper<>());
        for(int i = count;i<radio*base;i++){
            Role role = new Role();
            role.setDescription("插入数据啊~~插入一些数据"+(i+1));
            role.setSort(i+1);
            role.setIsDelete(1);
            roleMapper.insert(role);
        }

    }

}
