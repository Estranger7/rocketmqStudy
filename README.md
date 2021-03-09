# sql
```
-- ----------------------------
-- 订单表
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `amount` int(11) NULL DEFAULT NULL COMMENT '件数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- 积分表
-- ----------------------------
DROP TABLE IF EXISTS `t_points`;
CREATE TABLE `t_points`  (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(16) NOT NULL COMMENT '用户id',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单编号',
  `points` int(4) NOT NULL COMMENT '积分',
  `remarks` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- 事务日志表
-- ----------------------------
DROP TABLE IF EXISTS `transaction_log`;
CREATE TABLE `transaction_log`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '事务ID',
  `business` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务标识',
  `order_id` int(11) NOT NULL COMMENT '对应业务表中的主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```
# 执行流程
- 调用`OrderController`的`createOrder`方法，会发送一条创建订单的事务消息；
- 此时会触发`executeLocalTransaction()`方法，进行本地事务
的提交，根据本地事务的执行成功或失败返回相应的二次确认的消息；
- 如果是确认成功的消息，则这条消息会被消费，执行加积分的操作。

严格意义上来说，rocketmq并没有真正实现分布式事务，它只能确保上游服务一定成功，做不到下游服务出现问题时，回滚上游服务。

