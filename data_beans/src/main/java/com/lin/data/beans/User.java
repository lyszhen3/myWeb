package com.lin.data.beans;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *@author 自动生成
 *@see User
 *@remark 自动生成
 *生成日期：2017-10-24
 *完成日期：
 *内容摘要：
 *
 */
public class User{

    
    /**null*/
    @Id
    @GeneratedValue(generator="JDBC")
    private Integer userId;
    /**null*/
    private String userName;
    /**null*/
    private String loginName;
    /**null*/
    private String loginPass;

    public void setUserId(Integer userId){
        this.userId =userId;
    }
    public Integer getUserId(){
        return this.userId;
    }
    public void setUserName(String userName){
        this.userName =userName;
    }
    public String getUserName(){
        return this.userName;
    }
    public void setLoginName(String loginName){
        this.loginName =loginName;
    }
    public String getLoginName(){
        return this.loginName;
    }
    public void setLoginPass(String loginPass){
        this.loginPass =loginPass;
    }
    public String getLoginPass(){
        return this.loginPass;
    }
}
