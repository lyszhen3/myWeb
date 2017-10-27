package com.lin.shiro;

import java.io.Serializable;

/**
 * Created by pc on 2017-10-24.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class ShiroUser implements Serializable{

    private static final long serialVersionUID = 6381413667278224731L;
    private Integer userId;
    private String userName;
    private Integer[] roleids;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer[] getRoleids() {
        return roleids;
    }

    public void setRoleids(Integer[] roleids) {
        this.roleids = roleids;
    }
}
