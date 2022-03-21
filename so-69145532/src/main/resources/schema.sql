
-- ----------------------------
-- Table structure for from_info
-- ----------------------------
DROP TABLE IF EXISTS `from_info`;
CREATE TABLE `from_info`  (
                              `id` bigint(20) NOT NULL COMMENT '主键',
                              `order_id` bigint(20) NOT NULL COMMENT '订单id',
                              `from_id` bigint(20) NOT NULL COMMENT '表单信息id',
                              `from_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '信息值(出地址控件外地填值)',
                              PRIMARY KEY (`id`) USING BTREE,
                              INDEX `ofrom_index`(`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预约订单表单信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of from_info
-- ----------------------------
INSERT INTO `from_info` VALUES (1, 1, 901, 'good');
INSERT INTO `from_info` VALUES (2, 1, 902, 'big');
INSERT INTO `from_info` VALUES (3, 1, 903, '10');
INSERT INTO `from_info` VALUES (4, 1, 905, '2022-03-21');
INSERT INTO `from_info` VALUES (5, 2, 901, 'well');
INSERT INTO `from_info` VALUES (6, 2, 902, 'middle');
INSERT INTO `from_info` VALUES (7, 2, 903, '11');
INSERT INTO `from_info` VALUES (8, 2, 905, '2022-03-21');
INSERT INTO `from_info` VALUES (9, 3, 901, 'bad');
INSERT INTO `from_info` VALUES (10, 3, 902, 'small');
INSERT INTO `from_info` VALUES (11, 3, 903, '12');
INSERT INTO `from_info` VALUES (12, 3, 905, '2022-03-21');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
                               `id` bigint(20) NOT NULL COMMENT '主键',
                               `main_id` bigint(20) NOT NULL COMMENT '主表id',
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `order_main_id`(`main_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预约订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (1, 100);
INSERT INTO `order_info` VALUES (2, 100);
INSERT INTO `order_info` VALUES (3, 200);