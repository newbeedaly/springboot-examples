CREATE TABLE IF NOT EXISTS `mp_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `user_name` varchar(50) DEFAULT NULL,
    `age` int(11) DEFAULT NULL,
    `mobile_number` varchar(50) DEFAULT NULL,
    `create_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
