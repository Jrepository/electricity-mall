DROP DATABASE IF EXISTS `electricity_mall_order`;
CREATE DATABASE electricity_mall_order;
USE electricity_mall_order;
CREATE TABLE `order`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11) DEFAULT NULL,
    `order_number`  bigint(11) DEFAULT NULL,
    `pay_amount`  decimal(10, 0) DEFAULT NULL,
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
