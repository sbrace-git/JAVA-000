/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-12-02 03:11:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_good
-- ----------------------------
DROP TABLE IF EXISTS `t_good`;
CREATE TABLE `t_good` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '商品名',
  `detail` varchar(1023) NOT NULL COMMENT '描述',
  `pice` decimal(10,0) unsigned NOT NULL COMMENT '价格',
  `stock` bigint(20) unsigned NOT NULL COMMENT '库存',
  `update_time` bigint(20) unsigned NOT NULL COMMENT '更新时间',
  `create_time` bigint(20) unsigned NOT NULL COMMENT '创建时间',
  `status` varchar(255) NOT NULL COMMENT '状态',
  `version` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品';

-- ----------------------------
-- Table structure for t_good_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `t_good_snapshot`;
CREATE TABLE `t_good_snapshot` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `good_id` bigint(20) unsigned NOT NULL COMMENT '商品id',
  `name` varchar(255) NOT NULL COMMENT '商品名',
  `describe` varchar(1023) NOT NULL COMMENT '描述',
  `pice` decimal(10,0) unsigned NOT NULL COMMENT '价格',
  `update_time` bigint(20) unsigned NOT NULL COMMENT '更新时间',
  `create_time` bigint(20) unsigned NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品快照';

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `total` decimal(10,0) unsigned NOT NULL COMMENT '金额',
  `status` varchar(255) NOT NULL COMMENT '状态',
  `update_time` bigint(20) unsigned NOT NULL COMMENT '更新时间',
  `create_time` bigint(20) unsigned NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` bigint(20) unsigned NOT NULL COMMENT '订单id',
  `good_snapshot_id` bigint(20) unsigned NOT NULL COMMENT '商品快照id',
  `update_time` bigint(20) unsigned NOT NULL COMMENT '更新时间',
  `create_time` bigint(20) unsigned NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nick_name` varchar(255) NOT NULL COMMENT '昵称',
  `update_time` bigint(20) unsigned NOT NULL COMMENT '更新时间',
  `create_time` bigint(20) unsigned NOT NULL COMMENT '创建时间',
  `status` varchar(255) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';
