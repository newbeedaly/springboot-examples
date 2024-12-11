-- ----------------------------
-- Table structure for t_excel_order
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_order`  (
  `order_no` bigint(20) NOT NULL COMMENT '主键',
  `order_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  `total_price` bigint(20) NULL DEFAULT NULL COMMENT '总价(单位为分)',
  `origin` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '渠道',
  `address_detail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `order_time` datetime NULL DEFAULT NULL COMMENT '订单时间',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_excel_order
-- ----------------------------
INSERT IGNORE INTO `t_order` VALUES (1, '已完成', 1000, '手厅', '杭州市滨江区长河街道110号', now());
