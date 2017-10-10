package com.lin.data.examples;


import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author 自动生成
 *@see Account
 *@docRoot com.lin.data.examples;
 *@remark 自动生成
 *生成日期：2017-10-10
 *完成日期：
 *内容摘要：
 *
 */
public class AccountExample extends Example{


    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    protected String orderByClause;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    protected boolean distinct;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public AccountExample(){
        oredCriteria = new ArrayList<>();
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
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
     * This field corresponds to the database table Account
     *
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

     /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
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

        public Criteria andIdEqualTo(Integer value1){
            addCriterion("id =",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value1){
            addCriterion("id <>",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value1){
            addCriterion("id >",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value1){
            addCriterion("id >=",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value1){
            addCriterion("id <",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value1){
            addCriterion("id <=",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdIn(Integer value1){
            addCriterion("id in",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(Integer value1){
            addCriterion("id not in",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value1){
            addCriterion("id like",value1,"id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1,Integer value2){
            addCriterion("id between",value1,value2,"id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1,Integer value2){
            addCriterion("id not between",value1,value2,"id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull(){
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull(){
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value1){
            addCriterion("name =",value1,"name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value1){
            addCriterion("name <>",value1,"name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value1){
            addCriterion("name >",value1,"name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value1){
            addCriterion("name >=",value1,"name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value1){
            addCriterion("name <",value1,"name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value1){
            addCriterion("name <=",value1,"name");
            return (Criteria) this;
        }

        public Criteria andNameIn(String value1){
            addCriterion("name in",value1,"name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(String value1){
            addCriterion("name not in",value1,"name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value1){
            addCriterion("name like",value1,"name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1,String value2){
            addCriterion("name between",value1,value2,"name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1,String value2){
            addCriterion("name not between",value1,value2,"name");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull(){
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull(){
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value1){
            addCriterion("email =",value1,"email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value1){
            addCriterion("email <>",value1,"email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value1){
            addCriterion("email >",value1,"email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value1){
            addCriterion("email >=",value1,"email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value1){
            addCriterion("email <",value1,"email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value1){
            addCriterion("email <=",value1,"email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(String value1){
            addCriterion("email in",value1,"email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(String value1){
            addCriterion("email not in",value1,"email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value1){
            addCriterion("email like",value1,"email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1,String value2){
            addCriterion("email between",value1,value2,"email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1,String value2){
            addCriterion("email not between",value1,value2,"email");
            return (Criteria) this;
        }

        public Criteria andHashPasswordIsNull(){
            addCriterion("hash_password is null");
            return (Criteria) this;
        }

        public Criteria andHashPasswordIsNotNull(){
            addCriterion("hash_password is not null");
            return (Criteria) this;
        }

        public Criteria andHashPasswordEqualTo(String value1){
            addCriterion("hash_password =",value1,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordNotEqualTo(String value1){
            addCriterion("hash_password <>",value1,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordGreaterThan(String value1){
            addCriterion("hash_password >",value1,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordGreaterThanOrEqualTo(String value1){
            addCriterion("hash_password >=",value1,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordLessThan(String value1){
            addCriterion("hash_password <",value1,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordLessThanOrEqualTo(String value1){
            addCriterion("hash_password <=",value1,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordIn(String value1){
            addCriterion("hash_password in",value1,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordNotIn(String value1){
            addCriterion("hash_password not in",value1,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordLike(String value1){
            addCriterion("hash_password like",value1,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordBetween(String value1,String value2){
            addCriterion("hash_password between",value1,value2,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andHashPasswordNotBetween(String value1,String value2){
            addCriterion("hash_password not between",value1,value2,"hashPassword");
            return (Criteria) this;
        }

        public Criteria andFidIsNull(){
            addCriterion("fid is null");
            return (Criteria) this;
        }

        public Criteria andFidIsNotNull(){
            addCriterion("fid is not null");
            return (Criteria) this;
        }

        public Criteria andFidEqualTo(Integer value1){
            addCriterion("fid =",value1,"fid");
            return (Criteria) this;
        }

        public Criteria andFidNotEqualTo(Integer value1){
            addCriterion("fid <>",value1,"fid");
            return (Criteria) this;
        }

        public Criteria andFidGreaterThan(Integer value1){
            addCriterion("fid >",value1,"fid");
            return (Criteria) this;
        }

        public Criteria andFidGreaterThanOrEqualTo(Integer value1){
            addCriterion("fid >=",value1,"fid");
            return (Criteria) this;
        }

        public Criteria andFidLessThan(Integer value1){
            addCriterion("fid <",value1,"fid");
            return (Criteria) this;
        }

        public Criteria andFidLessThanOrEqualTo(Integer value1){
            addCriterion("fid <=",value1,"fid");
            return (Criteria) this;
        }

        public Criteria andFidIn(Integer value1){
            addCriterion("fid in",value1,"fid");
            return (Criteria) this;
        }

        public Criteria andFidNotIn(Integer value1){
            addCriterion("fid not in",value1,"fid");
            return (Criteria) this;
        }

        public Criteria andFidLike(String value1){
            addCriterion("fid like",value1,"fid");
            return (Criteria) this;
        }

        public Criteria andFidBetween(Integer value1,Integer value2){
            addCriterion("fid between",value1,value2,"fid");
            return (Criteria) this;
        }

        public Criteria andFidNotBetween(Integer value1,Integer value2){
            addCriterion("fid not between",value1,value2,"fid");
            return (Criteria) this;
        }

        public Criteria andF2idIsNull(){
            addCriterion("f_2id is null");
            return (Criteria) this;
        }

        public Criteria andF2idIsNotNull(){
            addCriterion("f_2id is not null");
            return (Criteria) this;
        }

        public Criteria andF2idEqualTo(Integer value1){
            addCriterion("f_2id =",value1,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idNotEqualTo(Integer value1){
            addCriterion("f_2id <>",value1,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idGreaterThan(Integer value1){
            addCriterion("f_2id >",value1,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idGreaterThanOrEqualTo(Integer value1){
            addCriterion("f_2id >=",value1,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idLessThan(Integer value1){
            addCriterion("f_2id <",value1,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idLessThanOrEqualTo(Integer value1){
            addCriterion("f_2id <=",value1,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idIn(Integer value1){
            addCriterion("f_2id in",value1,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idNotIn(Integer value1){
            addCriterion("f_2id not in",value1,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idLike(String value1){
            addCriterion("f_2id like",value1,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idBetween(Integer value1,Integer value2){
            addCriterion("f_2id between",value1,value2,"f2id");
            return (Criteria) this;
        }

        public Criteria andF2idNotBetween(Integer value1,Integer value2){
            addCriterion("f_2id not between",value1,value2,"f2id");
            return (Criteria) this;
        }


    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
     *
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Account
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
