CREATE DATABASE IF NOT EXISTS ticket_system DEFAULT CHARSET utf8mb4;
USE ticket_system;

-- 1. 演出活动表
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '演出名称',
  `venue` varchar(128) NOT NULL COMMENT '演出场馆',
  `show_time` datetime NOT NULL COMMENT '演出时间',
  `status` int DEFAULT 1 COMMENT '状态：0-未开售，1-售票中',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='演出活动表';

INSERT INTO `event` (`name`, `venue`, `show_time`, `status`)
VALUES ('张学友 60+ 巡回演唱会', '北京凯迪拉克中心', '2026-12-31 19:30:00', 1);

-- 2. 票档库存表
DROP TABLE IF EXISTS `ticket_tier`;
CREATE TABLE `ticket_tier` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_id` bigint NOT NULL COMMENT '关联演出ID',
  `tier_name` varchar(64) NOT NULL COMMENT '票档名称',
  `price` decimal(10,2) NOT NULL COMMENT '票价',
  `total_stock` int NOT NULL COMMENT '总库存',
  `available_stock` int NOT NULL COMMENT '可用库存',
  `version` int DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  KEY `idx_event_id` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='票档库存表';

INSERT INTO `ticket_tier` (`event_id`, `tier_name`, `price`, `total_stock`, `available_stock`)
VALUES (1, '内场 VIP 区', 2580.00, 500, 500),
       (1, '看台 A 区', 1280.00, 2000, 2000);

-- 3. 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码，当前阶段先明文存储，后续会升级为加密存储',
  `nickname` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO `user` (`username`, `password`, `nickname`, `phone`)
VALUES ('demo', '123456', '演示用户', '13800000000');

-- 4. 管理员表
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员主键ID',
  `username` varchar(64) NOT NULL COMMENT '管理员用户名',
  `password` varchar(128) NOT NULL COMMENT '密码，当前阶段先明文存储，后续会升级为加密存储',
  `name` varchar(64) DEFAULT NULL COMMENT '管理员姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

INSERT INTO `admin` (`username`, `password`, `name`)
VALUES ('admin', '123456', '系统管理员');

-- 5. 票务订单表
DROP TABLE IF EXISTS `ticket_order`;
CREATE TABLE `ticket_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单主键ID',
  `order_no` varchar(64) NOT NULL COMMENT '全局唯一订单号',
  `user_id` bigint NOT NULL COMMENT '购票用户ID',
  `event_id` bigint NOT NULL COMMENT '演出ID',
  `ticket_tier_id` bigint NOT NULL COMMENT '票档ID',
  `ticket_count` int NOT NULL COMMENT '购票数量',
  `amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `status` varchar(32) NOT NULL COMMENT '订单状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_event_id` (`event_id`),
  KEY `idx_ticket_tier_id` (`ticket_tier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='票务订单表';

-- 6. 支付记录表
DROP TABLE IF EXISTS `payment_record`;
CREATE TABLE `payment_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '支付记录ID',
  `payment_no` varchar(64) NOT NULL COMMENT '支付流水号',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '支付用户ID',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `status` varchar(32) NOT NULL COMMENT '支付状态',
  `pay_time` datetime DEFAULT NULL COMMENT '支付成功时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
      ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payment_no` (`payment_no`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付记录表';
