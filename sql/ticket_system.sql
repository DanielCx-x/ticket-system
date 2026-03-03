-- ticket-system/sql/ticket_system.sql
CREATE DATABASE IF NOT EXISTS ticket_system DEFAULT CHARSET utf8mb4;
USE ticket_system;

-- 文件路径: ticket-system/sql/ticket_system.sql
...

-- ----------------------------
-- 1. 演出活动表
-- ----------------------------
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

-- ----------------------------
-- 2. 票档库存表
-- ----------------------------
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

-- ----------------------------
-- 3. 票务订单表
-- ----------------------------
DROP TABLE IF EXISTS `ticket_order`;
CREATE TABLE `ticket_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(64) NOT NULL COMMENT '全局唯一订单号',
  `user_id` bigint NOT NULL COMMENT '购票用户ID',
  `event_id` bigint NOT NULL COMMENT '演出ID',
  `ticket_tier_id` bigint NOT NULL COMMENT '票档ID',
  `ticket_count` int NOT NULL COMMENT '购票数量',
  `amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `status` varchar(32) NOT NULL COMMENT '订单状态：INIT, QUEUED, CONFIRMED, PAID, FAILED, CANCELLED',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='票务订单表';
