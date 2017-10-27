package com.lin.data.examples;


import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author 自动生成
 *@see User
 *@docRoot com.lin.data.examples;
 *@remark 自动生成
 *生成日期：2017-10-24
 *完成日期：
 *内容摘要：
 *
 */
public class UserExample extends Example{


    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    protected String orderByClause;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    protected boolean distinct;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public UserExample(){
        oredCriteria = new ArrayList<>();
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
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
     * This field corresponds to the database table User
     *
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

     /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
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

        public Criteria andUserIdIsNull(){
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull(){
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value1){
            addCriterion("user_id =",value1,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value1){
            addCriterion("user_id <>",value1,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value1){
            addCriterion("user_id >",value1,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value1){
            addCriterion("user_id >=",value1,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value1){
            addCriterion("user_id <",value1,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value1){
            addCriterion("user_id <=",value1,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(Integer value1){
            addCriterion("user_id in",value1,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(Integer value1){
            addCriterion("user_id not in",value1,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value1){
            addCriterion("user_id like",value1,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1,Integer value2){
            addCriterion("user_id between",value1,value2,"userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1,Integer value2){
            addCriterion("user_id not between",value1,value2,"userId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull(){
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull(){
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value1){
            addCriterion("user_name =",value1,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value1){
            addCriterion("user_name <>",value1,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value1){
            addCriterion("user_name >",value1,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value1){
            addCriterion("user_name >=",value1,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value1){
            addCriterion("user_name <",value1,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value1){
            addCriterion("user_name <=",value1,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(String value1){
            addCriterion("user_name in",value1,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(String value1){
            addCriterion("user_name not in",value1,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value1){
            addCriterion("user_name like",value1,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1,String value2){
            addCriterion("user_name between",value1,value2,"userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1,String value2){
            addCriterion("user_name not between",value1,value2,"userName");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNull(){
            addCriterion("login_name is null");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNotNull(){
            addCriterion("login_name is not null");
            return (Criteria) this;
        }

        public Criteria andLoginNameEqualTo(String value1){
            addCriterion("login_name =",value1,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotEqualTo(String value1){
            addCriterion("login_name <>",value1,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThan(String value1){
            addCriterion("login_name >",value1,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThanOrEqualTo(String value1){
            addCriterion("login_name >=",value1,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThan(String value1){
            addCriterion("login_name <",value1,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThanOrEqualTo(String value1){
            addCriterion("login_name <=",value1,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameIn(String value1){
            addCriterion("login_name in",value1,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotIn(String value1){
            addCriterion("login_name not in",value1,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLike(String value1){
            addCriterion("login_name like",value1,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameBetween(String value1,String value2){
            addCriterion("login_name between",value1,value2,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotBetween(String value1,String value2){
            addCriterion("login_name not between",value1,value2,"loginName");
            return (Criteria) this;
        }

        public Criteria andLoginPassIsNull(){
            addCriterion("login_pass is null");
            return (Criteria) this;
        }

        public Criteria andLoginPassIsNotNull(){
            addCriterion("login_pass is not null");
            return (Criteria) this;
        }

        public Criteria andLoginPassEqualTo(String value1){
            addCriterion("login_pass =",value1,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassNotEqualTo(String value1){
            addCriterion("login_pass <>",value1,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassGreaterThan(String value1){
            addCriterion("login_pass >",value1,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassGreaterThanOrEqualTo(String value1){
            addCriterion("login_pass >=",value1,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassLessThan(String value1){
            addCriterion("login_pass <",value1,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassLessThanOrEqualTo(String value1){
            addCriterion("login_pass <=",value1,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassIn(String value1){
            addCriterion("login_pass in",value1,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassNotIn(String value1){
            addCriterion("login_pass not in",value1,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassLike(String value1){
            addCriterion("login_pass like",value1,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassBetween(String value1,String value2){
            addCriterion("login_pass between",value1,value2,"loginPass");
            return (Criteria) this;
        }

        public Criteria andLoginPassNotBetween(String value1,String value2){
            addCriterion("login_pass not between",value1,value2,"loginPass");
            return (Criteria) this;
        }


    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
     *
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table User
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
