package com.lin.data.beans;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *@author 自动生成
 *@see Message
 *@remark 自动生成
 *生成日期：2017-10-12
 *完成日期：
 *内容摘要：
 *
 */
public class Message{

    
    /**主键*/
    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;
    /**接收者id*/
    private Long receiverId;
    /**null*/
    private java.util.Date receiveDate;

    public void setId(Long id){
        this.id =id;
    }
    public Long getId(){
        return this.id;
    }
    public void setReceiverId(Long receiverId){
        this.receiverId =receiverId;
    }
    public Long getReceiverId(){
        return this.receiverId;
    }
    public void setReceiveDate(java.util.Date receiveDate){
        this.receiveDate =receiveDate;
    }
    public java.util.Date getReceiveDate(){
        return this.receiveDate;
    }
}
