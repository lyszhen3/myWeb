package com.lin.data.examples;


import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author 自动生成
 *@see Book
 *@docRoot com.lin.data.examples;
 *@remark 自动生成
 *生成日期：2017-10-10
 *完成日期：
 *内容摘要：
 *
 */
public class BookExample extends Example{


    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    protected String orderByClause;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    protected boolean distinct;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public BookExample(){
        oredCriteria = new ArrayList<>();
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
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
     * This field corresponds to the database table Book
     *
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

     /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
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

        public Criteria andDoubanIdIsNull(){
            addCriterion("douban_id is null");
            return (Criteria) this;
        }

        public Criteria andDoubanIdIsNotNull(){
            addCriterion("douban_id is not null");
            return (Criteria) this;
        }

        public Criteria andDoubanIdEqualTo(String value1){
            addCriterion("douban_id =",value1,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdNotEqualTo(String value1){
            addCriterion("douban_id <>",value1,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdGreaterThan(String value1){
            addCriterion("douban_id >",value1,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdGreaterThanOrEqualTo(String value1){
            addCriterion("douban_id >=",value1,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdLessThan(String value1){
            addCriterion("douban_id <",value1,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdLessThanOrEqualTo(String value1){
            addCriterion("douban_id <=",value1,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdIn(String value1){
            addCriterion("douban_id in",value1,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdNotIn(String value1){
            addCriterion("douban_id not in",value1,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdLike(String value1){
            addCriterion("douban_id like",value1,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdBetween(String value1,String value2){
            addCriterion("douban_id between",value1,value2,"doubanId");
            return (Criteria) this;
        }

        public Criteria andDoubanIdNotBetween(String value1,String value2){
            addCriterion("douban_id not between",value1,value2,"doubanId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull(){
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull(){
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value1){
            addCriterion("title =",value1,"title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value1){
            addCriterion("title <>",value1,"title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value1){
            addCriterion("title >",value1,"title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value1){
            addCriterion("title >=",value1,"title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value1){
            addCriterion("title <",value1,"title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value1){
            addCriterion("title <=",value1,"title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(String value1){
            addCriterion("title in",value1,"title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(String value1){
            addCriterion("title not in",value1,"title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value1){
            addCriterion("title like",value1,"title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1,String value2){
            addCriterion("title between",value1,value2,"title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1,String value2){
            addCriterion("title not between",value1,value2,"title");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull(){
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull(){
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value1){
            addCriterion("url =",value1,"url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value1){
            addCriterion("url <>",value1,"url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value1){
            addCriterion("url >",value1,"url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value1){
            addCriterion("url >=",value1,"url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value1){
            addCriterion("url <",value1,"url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value1){
            addCriterion("url <=",value1,"url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(String value1){
            addCriterion("url in",value1,"url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(String value1){
            addCriterion("url not in",value1,"url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value1){
            addCriterion("url like",value1,"url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1,String value2){
            addCriterion("url between",value1,value2,"url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1,String value2){
            addCriterion("url not between",value1,value2,"url");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull(){
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull(){
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value1){
            addCriterion("description =",value1,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value1){
            addCriterion("description <>",value1,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value1){
            addCriterion("description >",value1,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value1){
            addCriterion("description >=",value1,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value1){
            addCriterion("description <",value1,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value1){
            addCriterion("description <=",value1,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(String value1){
            addCriterion("description in",value1,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(String value1){
            addCriterion("description not in",value1,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value1){
            addCriterion("description like",value1,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1,String value2){
            addCriterion("description between",value1,value2,"description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1,String value2){
            addCriterion("description not between",value1,value2,"description");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIsNull(){
            addCriterion("owner_id is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIsNotNull(){
            addCriterion("owner_id is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdEqualTo(Long value1){
            addCriterion("owner_id =",value1,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotEqualTo(Long value1){
            addCriterion("owner_id <>",value1,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThan(Long value1){
            addCriterion("owner_id >",value1,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThanOrEqualTo(Long value1){
            addCriterion("owner_id >=",value1,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThan(Long value1){
            addCriterion("owner_id <",value1,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThanOrEqualTo(Long value1){
            addCriterion("owner_id <=",value1,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIn(Long value1){
            addCriterion("owner_id in",value1,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotIn(Long value1){
            addCriterion("owner_id not in",value1,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLike(String value1){
            addCriterion("owner_id like",value1,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdBetween(Long value1,Long value2){
            addCriterion("owner_id between",value1,value2,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotBetween(Long value1,Long value2){
            addCriterion("owner_id not between",value1,value2,"ownerId");
            return (Criteria) this;
        }

        public Criteria andOnboardDateIsNull(){
            addCriterion("onboard_date is null");
            return (Criteria) this;
        }

        public Criteria andOnboardDateIsNotNull(){
            addCriterion("onboard_date is not null");
            return (Criteria) this;
        }

        public Criteria andOnboardDateEqualTo(java.util.Date value1){
            addCriterion("onboard_date =",value1,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateNotEqualTo(java.util.Date value1){
            addCriterion("onboard_date <>",value1,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateGreaterThan(java.util.Date value1){
            addCriterion("onboard_date >",value1,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateGreaterThanOrEqualTo(java.util.Date value1){
            addCriterion("onboard_date >=",value1,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateLessThan(java.util.Date value1){
            addCriterion("onboard_date <",value1,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateLessThanOrEqualTo(java.util.Date value1){
            addCriterion("onboard_date <=",value1,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateIn(java.util.Date value1){
            addCriterion("onboard_date in",value1,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateNotIn(java.util.Date value1){
            addCriterion("onboard_date not in",value1,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateLike(String value1){
            addCriterion("onboard_date like",value1,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateBetween(java.util.Date value1,java.util.Date value2){
            addCriterion("onboard_date between",value1,value2,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andOnboardDateNotBetween(java.util.Date value1,java.util.Date value2){
            addCriterion("onboard_date not between",value1,value2,"onboardDate");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull(){
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull(){
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value1){
            addCriterion("status =",value1,"status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value1){
            addCriterion("status <>",value1,"status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value1){
            addCriterion("status >",value1,"status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value1){
            addCriterion("status >=",value1,"status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value1){
            addCriterion("status <",value1,"status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value1){
            addCriterion("status <=",value1,"status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(String value1){
            addCriterion("status in",value1,"status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(String value1){
            addCriterion("status not in",value1,"status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value1){
            addCriterion("status like",value1,"status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1,String value2){
            addCriterion("status between",value1,value2,"status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1,String value2){
            addCriterion("status not between",value1,value2,"status");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdIsNull(){
            addCriterion("borrower_id is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdIsNotNull(){
            addCriterion("borrower_id is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdEqualTo(Long value1){
            addCriterion("borrower_id =",value1,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdNotEqualTo(Long value1){
            addCriterion("borrower_id <>",value1,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdGreaterThan(Long value1){
            addCriterion("borrower_id >",value1,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdGreaterThanOrEqualTo(Long value1){
            addCriterion("borrower_id >=",value1,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdLessThan(Long value1){
            addCriterion("borrower_id <",value1,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdLessThanOrEqualTo(Long value1){
            addCriterion("borrower_id <=",value1,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdIn(Long value1){
            addCriterion("borrower_id in",value1,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdNotIn(Long value1){
            addCriterion("borrower_id not in",value1,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdLike(String value1){
            addCriterion("borrower_id like",value1,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdBetween(Long value1,Long value2){
            addCriterion("borrower_id between",value1,value2,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowerIdNotBetween(Long value1,Long value2){
            addCriterion("borrower_id not between",value1,value2,"borrowerId");
            return (Criteria) this;
        }

        public Criteria andBorrowDateIsNull(){
            addCriterion("borrow_date is null");
            return (Criteria) this;
        }

        public Criteria andBorrowDateIsNotNull(){
            addCriterion("borrow_date is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowDateEqualTo(java.util.Date value1){
            addCriterion("borrow_date =",value1,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateNotEqualTo(java.util.Date value1){
            addCriterion("borrow_date <>",value1,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateGreaterThan(java.util.Date value1){
            addCriterion("borrow_date >",value1,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateGreaterThanOrEqualTo(java.util.Date value1){
            addCriterion("borrow_date >=",value1,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateLessThan(java.util.Date value1){
            addCriterion("borrow_date <",value1,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateLessThanOrEqualTo(java.util.Date value1){
            addCriterion("borrow_date <=",value1,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateIn(java.util.Date value1){
            addCriterion("borrow_date in",value1,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateNotIn(java.util.Date value1){
            addCriterion("borrow_date not in",value1,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateLike(String value1){
            addCriterion("borrow_date like",value1,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateBetween(java.util.Date value1,java.util.Date value2){
            addCriterion("borrow_date between",value1,value2,"borrowDate");
            return (Criteria) this;
        }

        public Criteria andBorrowDateNotBetween(java.util.Date value1,java.util.Date value2){
            addCriterion("borrow_date not between",value1,value2,"borrowDate");
            return (Criteria) this;
        }


    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
     *
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This field was generated  Generator.
     * This field corresponds to the database table Book
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
