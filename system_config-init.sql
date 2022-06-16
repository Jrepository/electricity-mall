create database electricity_mall;
use electricity_mall;
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
    `parent_id`   int(11) DEFAULT '0' COMMENT '父ID',
    `name_en`     varchar(255) NOT NULL COMMENT '英文名称',
    `name_ch`     varchar(255) NOT NULL COMMENT '中午名称',
    `value`       text         NOT NULL COMMENT ' 配置项目',
    `sort`        int(11) NOT NULL COMMENT '排序，升序排列  值越小，越靠前',
    `state`       varchar(64)  DEFAULT '0' COMMENT '0:禁用,1:启用',
    `creator`     varchar(255) DEFAULT NULL COMMENT '创建人',
    `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
    `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
    `remark`      varchar(255) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

INSERT INTO `system_config`
VALUES (1, 0, 'int-val', 'Integer类型', '1', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (2, 0, 'long-val', 'Long类型', '2', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (3, 0, 'double-val', 'Double类型', '2', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (4, 0, 'boolean-val', 'Boolean类型', 'true', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (5, 0, 'string-val', 'String类型', '字符串', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (6, 0, 'list-val', 'List<Integer>类型', '[1,2]', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (7, 0, 'set-val', 'Set<String>类型', '[v1,v2]', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (8, 0, 'map-val', 'Map类型', '{key,val}', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (9, 8, 'map-val1', 'Map<String,String>类型', 'val1', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (10, 8, 'map-val2', 'Map<String,String>类型', 'val2', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (11, 8, 'map-val3', 'Map<String,String>类型', 'val3', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (12, 0, 'leve-1', 'leve-1', 'leve-1', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (13, 12, 'leve-2', 'leve-2', 'leve-2', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (14, 13, 'leve-3-1', 'leve-3-1', 'leve-3-1', 1, '1', NULL, NULL, NULL, NULL);
INSERT INTO `system_config`
VALUES (15, 13, 'leve-3-2', 'leve-3-2', 'leve-3-2', 1, '1', NULL, NULL, NULL, NULL);
