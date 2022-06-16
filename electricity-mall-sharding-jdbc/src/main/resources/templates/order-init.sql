DROP DATABASE IF EXISTS `electricity_mall_1`;
DROP DATABASE IF EXISTS `electricity_mall_2`;
CREATE DATABASE electricity_mall_1;
USE electricity_mall_1;
CREATE TABLE `order_1`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11) DEFAULT NULL,
    `order_number`  bigint(11) DEFAULT NULL,
    `pay_amount`  decimal(10, 0) DEFAULT NULL,
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_2`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11) DEFAULT NULL,
    `order_number`  bigint(11) DEFAULT NULL,
    `pay_amount`  decimal(10, 0) DEFAULT NULL,
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE DATABASE electricity_mall_2;
USE electricity_mall_2;
CREATE TABLE `order_1`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11) DEFAULT NULL,
    `order_number`  bigint(11) DEFAULT NULL,
    `pay_amount`  decimal(10, 0) DEFAULT NULL,
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `order_2`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11) DEFAULT NULL,
    `order_number`  bigint(11) DEFAULT NULL,
    `pay_amount`  decimal(10, 0) DEFAULT NULL,
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
