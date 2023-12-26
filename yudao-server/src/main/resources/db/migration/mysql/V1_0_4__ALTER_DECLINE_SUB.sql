ALTER TABLE `ruoyi-vue-pro`.`sale_decline_warning_sub`
    ADD COLUMN `employee_code` varchar(255) NULL COMMENT '科普员编码' AFTER `customer_code`;

ALTER TABLE `ruoyi-vue-pro`.`sale_decline_warning_sub`
    ADD COLUMN `reason_analysis` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原因分析' AFTER `decline_num`,
    ADD COLUMN `improvement_measure` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '改进措施' AFTER `reason_analysis`;