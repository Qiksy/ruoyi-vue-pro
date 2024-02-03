CREATE TABLE `ruoyi-vue-pro`.`system_printer_info`  (
                                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                        `code` varchar(255) NULL COMMENT '编号',
                                                        `name` varchar(255) NULL COMMENT '名称',
                                                        `url` varchar(500) NULL COMMENT '打印机URL',
                                                        `is_default` bit(1) NULL COMMENT '是否默认',
                                                        `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                                                        `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                                        `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                                                        `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                                        `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                                                        `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                                        PRIMARY KEY (`id`)
) COMMENT = '打印机信息表';


CREATE TABLE `ruoyi-vue-pro`.`system_print_template`  (
                                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                          `name` varchar(255) NULL COMMENT '名称',
                                                          `template_content` longtext NULL COMMENT '模板内容',
                                                          `is_system_default` bit(1) NULL COMMENT '系统默认',
                                                          `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                                                          `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                                          `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                                                          `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                                          `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                                          PRIMARY KEY (`id`)
) COMMENT = '打印模板表';

CREATE TABLE `ruoyi-vue-pro`.`system_printer_setting`  (
                                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                           `user_id` bigint NOT NULL COMMENT '用户id',
                                                           `printer_id` bigint NOT NULL COMMENT '打印机主键',
                                                           `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
                                                           `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                                           `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                                                           `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                                           `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                                                           `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                                           PRIMARY KEY (`id`)
) COMMENT = '用户默认打印机设置';


ALTER TABLE `ruoyi-vue-pro`.`system_print_template`
    ADD COLUMN `code` varchar(255) NULL COMMENT '编号' AFTER `id`;