package com.lin.data.examples;


import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author 自动生成
 *@see Message
 *@docRoot com.lin.data.examples;
 *@remark 自动生成
 *生成日期：2017-10-11
 *完成日期：
 *内容摘要：
 *
 */
public class MessageExample extends Example{


    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    protected String orderByClause;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    protected boolean distinct;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public MessageExample(){
        oredCriteria = new ArrayList<>();
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

     /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull(){
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull(){
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value1){
            addCriterion("id =",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value1){
            addCriterion("id <>",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value1){
            addCriterion("id >",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value1){
            addCriterion("id >=",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value1){
            addCriterion("id <",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value1){
            addCriterion("id <=",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdIn(Long value1){
            addCriterion("id in",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(Long value1){
            addCriterion("id not in",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value1){
            addCriterion("id like",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1,Long value2){
            addCriterion("id between",value1,value2,"id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1,Long value2){
            addCriterion("id not between",value1,value2,"id");
            return (Criteria) this;
        }

        public Criteria andReceiverIdIsNull(){
            addCriterion("receiver_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiverIdIsNotNull(){
            addCriterion("receiver_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverIdEqualTo(Long value1){
            addCriterion("receiver_id =",value1,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdNotEqualTo(Long value1){
            addCriterion("receiver_id <>",value1,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdGreaterThan(Long value1){
            addCriterion("receiver_id >",value1,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdGreaterThanOrEqualTo(Long value1){
            addCriterion("receiver_id >=",value1,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdLessThan(Long value1){
            addCriterion("receiver_id <",value1,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdLessThanOrEqualTo(Long value1){
            addCriterion("receiver_id <=",value1,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdIn(Long value1){
            addCriterion("receiver_id in",value1,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdNotIn(Long value1){
            addCriterion("receiver_id not in",value1,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdLike(String value1){
            addCriterion("receiver_id like",value1,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdBetween(Long value1,Long value2){
            addCriterion("receiver_id between",value1,value2,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiverIdNotBetween(Long value1,Long value2){
            addCriterion("receiver_id not between",value1,value2,"receiverId");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIsNull(){
            addCriterion("receive_date is null");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIsNotNull(){
            addCriterion("receive_date is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveDateEqualTo(java.util.Date value1){
            addCriterion("receive_date =",value1,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotEqualTo(java.util.Date value1){
            addCriterion("receive_date <>",value1,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateGreaterThan(java.util.Date value1){
            addCriterion("receive_date >",value1,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateGreaterThanOrEqualTo(java.util.Date value1){
            addCriterion("receive_date >=",value1,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateLessThan(java.util.Date value1){
            addCriterion("receive_date <",value1,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateLessThanOrEqualTo(java.util.Date value1){
            addCriterion("receive_date <=",value1,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateIn(java.util.Date value1){
            addCriterion("receive_date in",value1,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotIn(java.util.Date value1){
            addCriterion("receive_date not in",value1,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateLike(String value1){
            addCriterion("receive_date like",value1,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateBetween(java.util.Date value1,java.util.Date value2){
            addCriterion("receive_date between",value1,value2,"receiveDate");
            return (Criteria) this;
        }

        public Criteria andReceiveDateNotBetween(java.util.Date value1,java.util.Date value2){
            addCriterion("receive_date not between",value1,value2,"receiveDate");
            return (Criteria) this;
        }


    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Message
     *
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
