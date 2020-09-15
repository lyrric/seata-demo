/*
Navicat MySQL Data Transfer

Source Server         : localhost_1
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : seata-at

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2020-09-15 15:54:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `balance` int(11) DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='账户表';

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', 'zhangshan', '10000');

-- ----------------------------
-- Table structure for account_pre_pay
-- ----------------------------
DROP TABLE IF EXISTS `account_pre_pay`;
CREATE TABLE `account_pre_pay` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `xid` varchar(255) DEFAULT NULL COMMENT '事务ID',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `money` int(11) DEFAULT NULL COMMENT '预付款金额',
  `status` int(2) DEFAULT NULL COMMENT '状态（0=预扣除，1=commit，2=已回滚）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `xid` (`xid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户预付款记录';

-- ----------------------------
-- Records of account_pre_pay
-- ----------------------------

-- ----------------------------
-- Table structure for invoke_record
-- ----------------------------
DROP TABLE IF EXISTS `invoke_record`;
CREATE TABLE `invoke_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `xid` varchar(255) DEFAULT NULL COMMENT '事务ID',
  `function` varchar(255) DEFAULT NULL COMMENT '操作（格式：PAY_PRE、PAY_COMMIT、PAY_ROLLBACK）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `xid_function` (`xid`,`function`) USING BTREE COMMENT '联合唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of invoke_record
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `status` int(2) DEFAULT NULL COMMENT '状态（0=order_pre，1=order_commit，2=rollback）',
  `money` int(11) DEFAULT NULL COMMENT '订单金额',
  `xid` varchar(255) DEFAULT NULL COMMENT '事务ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单表';

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
