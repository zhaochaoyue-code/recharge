/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.23-log : Database - recharge
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`recharge` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `recharge`;

/*Table structure for table `auth` */

DROP TABLE IF EXISTS `auth`;

CREATE TABLE `auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号|type=text|search&calculat',
  `name` varchar(32) NOT NULL COMMENT '中文名称|type=text|search',
  `key_code` varchar(32) NOT NULL COMMENT '英文代码|type=text&is_unque=true',
  `urls` varchar(512) DEFAULT NULL COMMENT '请求url&多个用逗号分隔|type=text&abbreviate_lenth=20|search',
  `grade` int(11) NOT NULL COMMENT '级别|type=radio&default_key=1&default_value=1&2=2&3=3&4=4|calculat&hidden',
  `parent_id` bigint(20) NOT NULL COMMENT '父级|type=select_tree_foreign_table&default_key=&default_value=请选择&table=auth&option_key=id&option_value=name|hidden',
  `tree_path` varchar(64) DEFAULT NULL COMMENT '路径|type=text|calculat&hidden&sort',
  `create_time` datetime NOT NULL COMMENT '创建时间|type=datetime|calculat',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限|cache';

/*Table structure for table `mobile` */

DROP TABLE IF EXISTS `mobile`;

CREATE TABLE `mobile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(32) NOT NULL COMMENT '提交充值时使用此id。使用REPLACE(UUID(), ''-'', '''')方式获取。',
  `activity_id` varchar(64) NOT NULL COMMENT '活动id，同个活动内手机号不能重复。',
  `mobile` varchar(16) NOT NULL COMMENT '手机号码',
  `money` decimal(10,3) NOT NULL COMMENT '充值金额。单位为分。',
  `insert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `kc_recharge_id` varchar(64) DEFAULT NULL COMMENT '调用充值接口后返回的id，用于后来的状态查询。',
  `kc_recharge_status` int(11) DEFAULT NULL COMMENT '调用充值接口后返回状态。0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时',
  `kc_query_id` varchar(64) DEFAULT NULL COMMENT '调用查询充值状态接口后返回的id。',
  `kc_query_status` int(11) DEFAULT NULL COMMENT '调用查询充值状态接口后返回状态。0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时',
  `is_finished` int(11) NOT NULL DEFAULT '0' COMMENT '是否完成。发送成功或者重试超过配置的次数即认为完成。1是0否。',
  `process_status` int(11) NOT NULL DEFAULT '0' COMMENT '处理状态。0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功，11发送短信异常，12充值失败(调接口返回)，13充值超时(调接口返回)',
  `recharge_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '已经重试过充值的次数。',
  `query_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '已经重试过查询充值状态的次数。',
  `sms_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '已经重试过短信发送的次数。',
  `last_recharg_time` datetime DEFAULT NULL COMMENT '调用充值接口的时间',
  `last_query_time` datetime DEFAULT NULL COMMENT '调用查询状态接口的时间',
  `last_sms_time` datetime DEFAULT NULL COMMENT '调用短信接口的时间',
  `recharge_exception_msg` varchar(1024) DEFAULT NULL COMMENT '调用充值接口的异常信息',
  `query_exception_msg` varchar(1024) DEFAULT NULL COMMENT '调用查询状态接口的异常信息',
  `sms_exception_msg` varchar(1024) DEFAULT NULL COMMENT '调用短信发送接口的异常信息。',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_uid` (`uid`),
  KEY `index_insert_time` (`insert_time`),
  KEY `index_process_status` (`process_status`)
) ENGINE=InnoDB AUTO_INCREMENT=476129 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户手机号';

/*Table structure for table `mobile_history_151568` */

DROP TABLE IF EXISTS `mobile_history_151568`;

CREATE TABLE `mobile_history_151568` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(32) NOT NULL COMMENT '提交充值时使用此id。使用REPLACE(UUID(), ''-'', '''')方式获取。',
  `activity_id` varchar(64) NOT NULL COMMENT '活动id，同个活动内手机号不能重复。',
  `mobile` varchar(16) NOT NULL COMMENT '手机号码',
  `money` decimal(10,3) NOT NULL COMMENT '充值金额。单位为分。',
  `insert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `kc_recharge_id` varchar(64) DEFAULT NULL COMMENT '调用充值接口后返回的id，用于后来的状态查询。',
  `kc_recharge_status` int(11) DEFAULT NULL COMMENT '调用充值接口后返回状态。0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时',
  `kc_query_id` varchar(64) DEFAULT NULL COMMENT '调用查询充值状态接口后返回的id。',
  `kc_query_status` int(11) DEFAULT NULL COMMENT '调用查询充值状态接口后返回状态。0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时',
  `is_finished` int(11) NOT NULL DEFAULT '0' COMMENT '是否完成。发送成功或者重试超过配置的次数即认为完成。1是0否。',
  `process_status` int(11) NOT NULL DEFAULT '0' COMMENT '处理状态。0待充值，1发送充值中，2已发送充值待查询状态，3发送状态查询中，4已状态查询充值成功待发送短信。5充值getFuture异常，6查询状态getFuture异常，7提交充值异常，8提交查询状态异常，9短信发送处理中，10短信发送成功，11发送短信异常',
  `recharge_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '已经重试过充值的次数。',
  `query_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '已经重试过查询充值状态的次数。',
  `sms_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '已经重试过短信发送的次数。',
  `last_recharg_time` datetime DEFAULT NULL COMMENT '调用充值接口的时间',
  `last_query_time` datetime DEFAULT NULL COMMENT '调用查询状态接口的时间',
  `last_sms_time` datetime DEFAULT NULL COMMENT '调用短信接口的时间',
  `recharge_exception_msg` varchar(1024) DEFAULT NULL COMMENT '调用充值接口的异常信息',
  `query_exception_msg` varchar(1024) DEFAULT NULL COMMENT '调用查询状态接口的异常信息',
  `sms_exception_msg` varchar(1024) DEFAULT NULL COMMENT '调用短信发送接口的异常信息。',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_mobile_activity_id` (`mobile`,`activity_id`),
  UNIQUE KEY `index_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=471718 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户手机号';

/*Table structure for table `mobile_original_import_excel` */

DROP TABLE IF EXISTS `mobile_original_import_excel`;

CREATE TABLE `mobile_original_import_excel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `order_time` varchar(32) DEFAULT NULL COMMENT '订购时间',
  `order_channel` varchar(64) DEFAULT NULL COMMENT '订购渠道',
  `cancel_time` varchar(32) DEFAULT NULL COMMENT '取消时间',
  `province` varchar(32) DEFAULT NULL COMMENT '省',
  `area` varchar(32) DEFAULT NULL COMMENT '市',
  `brand` varchar(32) DEFAULT NULL COMMENT '品牌',
  `package_name` varchar(128) DEFAULT NULL COMMENT '套餐名称',
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='手机号原始表';

/*Table structure for table `mobile_sms` */

DROP TABLE IF EXISTS `mobile_sms`;

CREATE TABLE `mobile_sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_id` varchar(64) NOT NULL DEFAULT 'tencent_activity' COMMENT '活动id',
  `mobile` varchar(16) NOT NULL COMMENT '手机号',
  `send_status` int(11) DEFAULT '1' COMMENT '发送状态，1待发送，2发送成功，3发送失败，4发送异常',
  `exception_msg` varchar(1024) DEFAULT NULL COMMENT '异常信息。',
  `insert_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='mobile_615';

/*Table structure for table `person` */

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号|type=text|calculat&sort',
  `login_name` varchar(32) NOT NULL COMMENT '登录名称|type=text&is_unque=true|search',
  `password` varchar(64) NOT NULL COMMENT '登录密码|type=text&default=$2a$12$6AcLEHFFHC3n.t2Cu68ysOF/.g7HLWyrgKToVxTRd6ovbK6QZdvT6|hidden',
  `name` varchar(32) NOT NULL COMMENT '真实姓名|type=text|search',
  `company_name` varchar(64) DEFAULT NULL COMMENT '公司名称|type=text|search&hidden',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号码|type=text',
  `email` varchar(64) DEFAULT NULL COMMENT '邮件地址|type=text',
  `role_id` bigint(20) NOT NULL COMMENT '角色&用户所属角色。|type=select_foreign_table&default_key=&default_value=请选择&table=role&option_key=id&option_value=name|search',
  `is_enable` tinyint(1) NOT NULL COMMENT '是否启用&禁用后帐号无法登录。|type=radio&default=true',
  `portrait` varchar(64) DEFAULT NULL COMMENT '头像&用户头像。|type=image_file',
  `seller_logo` varchar(64) DEFAULT NULL COMMENT '商户logo&商户logo。|type=image_file',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间&是后一次登录的时间。|type=datetime&date_format=yyyy-MM-dd HH:mm:ss|calculat&sort',
  `create_time` datetime NOT NULL COMMENT '创建时间|type=datetime|calculat',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='后台帐号|cache';

/*Table structure for table `query_record` */

DROP TABLE IF EXISTS `query_record`;

CREATE TABLE `query_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_id` varchar(64) NOT NULL DEFAULT 'tencent_activity' COMMENT '活动id',
  `mobile_id` bigint(20) NOT NULL COMMENT '关联mobile表的id字段。',
  `mobile` varchar(16) NOT NULL COMMENT '手机号。',
  `insert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '查询时间。',
  `kc_id` varchar(32) DEFAULT NULL COMMENT '调用科创充值状态查询接口返回的id。',
  `kc_status` int(11) DEFAULT NULL COMMENT '调用科创充值状态查询接口返回的status。0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时',
  `exception_msg` varchar(2048) DEFAULT NULL COMMENT '其它异常信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=196068 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='调用充值状态查询接口表';

/*Table structure for table `recharge_record` */

DROP TABLE IF EXISTS `recharge_record`;

CREATE TABLE `recharge_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_id` varchar(64) NOT NULL DEFAULT 'tencent_activity' COMMENT '活动id',
  `mobile_id` bigint(20) NOT NULL COMMENT '关联mobile表的id字段。',
  `mobile` varchar(16) NOT NULL COMMENT '手机号。',
  `insert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间。',
  `kc_id` varchar(32) DEFAULT NULL COMMENT '调用科创充值接口返回的id。',
  `kc_status` int(11) DEFAULT NULL COMMENT '调用科创充值接口返回的status。0：初始状态，1：充值中，2：充值成功，3：充值失败，4：充值超时',
  `exception_msg` varchar(2048) DEFAULT NULL COMMENT '其它异常信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217582 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='调用充值接口记录表';

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号|type=text|search&calculat',
  `name` varchar(32) NOT NULL COMMENT '名称|type=text&is_unque=true|search',
  `description` varchar(64) DEFAULT NULL COMMENT '描述&说明此角色有什么作用|type=multiple_lines_text&abbreviate_lenth=50|search',
  `auths` varchar(2048) DEFAULT NULL COMMENT '拥有的权限|type=checkbox_tree_foreign_table&table=auth&option_key=id&option_value=name|hidden',
  `create_time` datetime NOT NULL COMMENT '创建时间|type=datetime|calculat&sort',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色|cache';

/*Table structure for table `sms_record` */

DROP TABLE IF EXISTS `sms_record`;

CREATE TABLE `sms_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_id` varchar(64) NOT NULL DEFAULT 'tencent_activity' COMMENT '活动id',
  `mobile_id` bigint(20) NOT NULL COMMENT '关联mobile表的id字段。',
  `mobile` varchar(16) NOT NULL COMMENT '手机号。',
  `content` varchar(1024) DEFAULT NULL COMMENT '发送内容。',
  `insert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间。',
  `code` varchar(32) DEFAULT NULL COMMENT '调用短信发送接口返回的code。如果不为空且不为0，一般说明有异常。',
  `msg` varchar(256) DEFAULT NULL COMMENT '调用短信发送接口返回的msg。',
  `result` int(11) DEFAULT NULL COMMENT '发送状态，1成功，2失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=149663 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='调用短信发送接口记录表';

/*Table structure for table `thread_mobile` */

DROP TABLE IF EXISTS `thread_mobile`;

CREATE TABLE `thread_mobile` (
  `id` bigint(20) NOT NULL COMMENT '主键id，即mobile表的id字段',
  `thread_name` varchar(64) NOT NULL COMMENT '线程名称',
  `uid` varchar(32) NOT NULL COMMENT 'uuid',
  `mobile` varchar(16) NOT NULL COMMENT '手机号',
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='线程正在处理的手机号记录';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
