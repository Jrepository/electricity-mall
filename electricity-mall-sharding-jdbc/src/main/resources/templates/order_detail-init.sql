USE electricity_mall_1;
CREATE TABLE `order_detail_1`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11) DEFAULT NULL,
    `order_number`    bigint(11) NOT NULL,
    `product_id`  bigint(11) DEFAULT NULL,
    `product_name` varchar(255) DEFAULT NULL,
    `price` decimal(10,2) DEFAULT NULL,
    `count` int(11) DEFAULT NULL,
    `pay_amount`  decimal(10, 0) DEFAULT NULL,
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `order_detail_2`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11) DEFAULT NULL,
    `order_number`    bigint(11) NOT NULL,
    `product_id`  bigint(11) DEFAULT NULL,
    `product_name` varchar(255) DEFAULT NULL,
    `price` decimal(10,2) DEFAULT NULL,
    `count` int(11) DEFAULT NULL,
    `pay_amount`  decimal(10, 0) DEFAULT NULL,
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
USE electricity_mall_2;
CREATE TABLE `order_detail_1`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11) DEFAULT NULL,
    `order_number`    bigint(11) NOT NULL,
    `product_id`  bigint(11) DEFAULT NULL,
    `product_name` varchar(255) DEFAULT NULL,
    `price` decimal(10,2) DEFAULT NULL,
    `count` int(11) DEFAULT NULL,
    `pay_amount`  decimal(10, 0) DEFAULT NULL,
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `order_detail_2`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(11) DEFAULT NULL,
    `order_number`    bigint(11) NOT NULL,
    `product_id`  bigint(11) DEFAULT NULL,
    `product_name` varchar(255) DEFAULT NULL,
    `price` decimal(10,2) DEFAULT NULL,
    `count` int(11) DEFAULT NULL,
    `pay_amount`  decimal(10, 0) DEFAULT NULL,
    `create_time` datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
