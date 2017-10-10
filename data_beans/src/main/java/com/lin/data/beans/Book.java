package com.lin.data.beans;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *@author 自动生成
 *@see Book
 *@remark 自动生成
 *生成日期：2017-10-10
 *完成日期：
 *内容摘要：
 *
 */
public class Book{

    
    /**null*/
    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;
    /**null*/
    private String doubanId;
    /**null*/
    private String title;
    /**null*/
    private String url;
    /**null*/
    private String description;
    /**null*/
    private Long ownerId;
    /**null*/
    private java.util.Date onboardDate;
    /**null*/
    private String status;
    /**null*/
    private Long borrowerId;
    /**null*/
    private java.util.Date borrowDate;

    public void setId(Long id){
        this.id =id;
    }
    public Long getId(){
        return this.id;
    }
    public void setDoubanId(String doubanId){
        this.doubanId =doubanId;
    }
    public String getDoubanId(){
        return this.doubanId;
    }
    public void setTitle(String title){
        this.title =title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setUrl(String url){
        this.url =url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setDescription(String description){
        this.description =description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setOwnerId(Long ownerId){
        this.ownerId =ownerId;
    }
    public Long getOwnerId(){
        return this.ownerId;
    }
    public void setOnboardDate(java.util.Date onboardDate){
        this.onboardDate =onboardDate;
    }
    public java.util.Date getOnboardDate(){
        return this.onboardDate;
    }
    public void setStatus(String status){
        this.status =status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setBorrowerId(Long borrowerId){
        this.borrowerId =borrowerId;
    }
    public Long getBorrowerId(){
        return this.borrowerId;
    }
    public void setBorrowDate(java.util.Date borrowDate){
        this.borrowDate =borrowDate;
    }
    public java.util.Date getBorrowDate(){
        return this.borrowDate;
    }
}
