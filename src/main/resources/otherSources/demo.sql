/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50096
Source Host           : 127.0.0.1:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2017-06-30 14:18:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(64) NOT NULL,
  `email` varchar(128) default NULL,
  `hash_password` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', 'User1', 'user1@gmail.com', '+2MunThvGcEfdYIFlT4NQQHt6z4=');
INSERT INTO `account` VALUES ('2', 'User2', 'user2@gmail.com', '+2MunThvGcEfdYIFlT4NQQHt6z4=');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` bigint(20) NOT NULL auto_increment,
  `douban_id` varchar(64) NOT NULL,
  `title` varchar(128) NOT NULL,
  `url` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  `owner_id` bigint(20) NOT NULL,
  `onboard_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` varchar(32) NOT NULL,
  `borrower_id` bigint(20) default NULL,
  `borrow_date` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '25984046', 'Big Data日知录', 'http://book.douban.com/subject/25984046/', '', '1', '2016-01-04 18:52:43', 'idle', null, '2016-01-04 18:52:41');
INSERT INTO `book` VALUES ('2', '25900156', 'Redis设计与实现', 'http://book.douban.com/subject/25900156/', '', '1', '2016-01-04 18:52:46', 'idle', null, '2016-01-04 18:52:44');
INSERT INTO `book` VALUES ('3', '25741352', 'DSL实战', 'http://book.douban.com/subject/25741352/', '', '2', '2016-01-04 18:52:49', 'idle', null, '2016-01-04 18:52:47');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL auto_increment,
  `receiver_id` bigint(20) default NULL,
  `message` varchar(256) default NULL,
  `receive_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
